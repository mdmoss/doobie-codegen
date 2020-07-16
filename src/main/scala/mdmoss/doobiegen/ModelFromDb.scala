package mdmoss.doobiegen

import doobie.imports._
import doobie.postgres.imports._
import mdmoss.doobiegen.Runner.Database
import scalaz._
import scalaz.concurrent.Task

object ModelFromDb {
  def apply(target: Runner.Target, database: Database): DbModel = {
    new ModelFromDb(target).run(database)
  }
}

class ModelFromDb(target: Runner.Target) {
  def run(database: Database): DbModel = {
    val xa = DriverManagerTransactor[Task](database.driver, database.url, database.username, database.password)
    xa.trans(implicitly [Monad[Task]])(getModel).unsafePerformSync
  }

  def getModel: ConnectionIO[DbModel] = for {
    tables <- db.information_schema.gen.Tables.allUnbounded()
    columns <- db.information_schema.gen.Columns.allUnbounded()
    tableConstraints <- db.information_schema.gen.TableConstraints.allUnbounded()
    keyColumnUsage <- db.information_schema.gen.KeyColumnUsage.allUnbounded()
    referentialConstraints <- db.information_schema.gen.ReferentialConstraints.allUnbounded()

  } yield {
    val columnsMap = columns.groupBy(_.tableSchema).mapValues(_.groupBy(_.tableName))
    val tableConstraintsMap = tableConstraints.groupBy(_.tableSchema).mapValues(_.groupBy(_.tableName))
    val keyColumnUsageMap = keyColumnUsage.groupBy(_.tableSchema).mapValues(_.groupBy(_.tableName))

    val tableConstraintsByName = tableConstraints.groupBy(_.constraintSchema).mapValues(_.groupBy(_.constraintName).mapValues { c => assert(c.length == 1); c.head })
    val referentialConstraintsByName = referentialConstraints.groupBy(_.constraintSchema).mapValues(_.groupBy(_.constraintName).mapValues { c => assert(c.length == 1); c.head })
    val keyColumnUsageByConstraint = keyColumnUsage.groupBy(_.constraintSchema).mapValues(_.groupBy(_.constraintName))

    val modelTables = tables.map { table =>
      val ref = sql.TableRef(table.tableSchema.filter(_ != "public"), table.tableName.getOrElse("?"))

      val tableColumns = columnsMap.get(table.tableSchema).flatMap(_.get(table.tableName)).getOrElse(List.empty)

      val orderedTableColumns = tableColumns.sortBy(_.ordinalPosition.getOrElse(0))

      val tableConstraints = tableConstraintsMap.get(table.tableSchema).flatMap(_.get(table.tableName)).getOrElse(List.empty)
      val tableKeyColumnUsage = keyColumnUsageMap.get(table.tableSchema).flatMap(_.get(table.tableName)).getOrElse(List.empty)

      val columnsWithTypes = orderedTableColumns.map { c => (c, getDataType(c)) }.collect { case (c, Some(dt)) => (c, dt) }

      val primaryKeyConstraints = tableConstraints.filter(_.constraintType.contains("PRIMARY KEY")).flatMap(_.constraintName)
      val uniqueConstraints = tableConstraints.filter(_.constraintType.contains("UNIQUE")).flatMap(_.constraintName)
      val checkConstraints = tableConstraints.filter(_.constraintType.contains("CHECK")).flatMap(_.constraintName)
      val foreignKeyConstraints = tableConstraints.filter(_.constraintType.contains("FOREIGN KEY"))

      /** Returns true if `column` is part of a singular or composite primary key. */
      def isPrimaryKey(column: db.information_schema.gen.Columns.Row): Boolean = {
        tableKeyColumnUsage.exists { k =>
          k.columnName == column.columnName &&
            k.constraintName.exists(primaryKeyConstraints.contains)
        }
      }

      val primaryKeyColumns = columnsWithTypes.filter { case (column, _) =>
        isPrimaryKey(column)
      }

      val maybeCompositeForeignKey = primaryKeyColumns match {
        case cs if cs.length > 1 => Some(sql.CompositePrimaryKey(cs.map(_._1.columnName.get)))
        case _ => None
      }

      val columns = columnsWithTypes.map { case (column, dataType) =>

        val nullible = if (column.isNullable.contains("NO")) Some(sql.NotNull) else Some(sql.Null)
        val maybeSingularPrimaryKey = if (maybeCompositeForeignKey.isEmpty && isPrimaryKey(column)) Some(sql.SingularPrimaryKey) else None
        val default = if (column.columnDefault.isDefined) Some(sql.Default) else None
        val unique = if (tableKeyColumnUsage.exists(k => k.columnName == column.columnName && k.constraintName.exists(uniqueConstraints.contains))) Some(sql.Unique) else None
        val check = if (tableKeyColumnUsage.exists(k => k.columnName == column.columnName && k.constraintName.exists(checkConstraints.contains))) Some(sql.Constraint) else None

        val foreignKeys = tableKeyColumnUsage
          .filter(k => k.columnName == column.columnName)
          .flatMap { c => foreignKeyConstraints.find(_.constraintName == c.constraintName) }
          .flatMap { constraint =>
            val refConstraint = referentialConstraintsByName.get(constraint.constraintSchema).flatMap(_.get(constraint.constraintName)).get
            val refColumns = keyColumnUsageByConstraint.get(refConstraint.uniqueConstraintSchema).flatMap(_.get(refConstraint.uniqueConstraintName)).get

            // println(s"Found a foreign key: ${column.tableSchema}.${column.tableName}.${column.columnName}")
            // refColumns.foreach(println)

            if (refColumns.length == 1) {
              val refColumn = refColumns.head
              Some(sql.References(sql.TableRef(refColumn.tableSchema, refColumn.tableName.getOrElse("?")), refColumn.columnName.getOrElse("?")))

            } else {
              // At this point, we only support singular foreign keys?
              None
            }
          }

        val columnProperties = Vector(
          nullible,
          maybeSingularPrimaryKey,
          default,
          unique,
          check
        ).flatten ++ foreignKeys

        sql.Column(column.columnName.getOrElse("?"), dataType, columnProperties)
      }

      sql.Table(ref, columns ++ maybeCompositeForeignKey.toList)
    }

    val nonEmptyTables = modelTables.filter(_.columns.nonEmpty)

    DbModel(nonEmptyTables)
  }

  def getDataType(column: db.information_schema.gen.Columns.Row): Option[sql.Type] = {
    column.dataType.flatMap { dataType =>
      val parser = new SqlStatementParser(dataType)
      parser.Type.run().toOption match {
        case st@Some(t) => if (column.columnDefault.exists(_.startsWith("nextval("))) {
          t match {
            case sql.BigInt => Some(sql.BigSerial)
            // case sql.Integer => Some(sql.Serial) TODO define serial
            case _ => st
          }
        } else {
          st
        }
        case None =>
          if (!target.quiet) {
            println(s"Unknown data type: ${dataType}");
          }
          None
      }
    }
  }
}
