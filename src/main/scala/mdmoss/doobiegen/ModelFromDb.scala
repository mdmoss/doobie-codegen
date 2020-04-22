package mdmoss.doobiegen

import doobie.imports._
import doobie.postgres.imports._
import mdmoss.doobiegen.Runner.TestDatabase
import scalaz._
import Scalaz._
import org.parboiled2.ParseError
import scalaz.concurrent.Task

object ModelFromDb {

  def apply(target: Runner.Target): DbModel = {
    val xa = target.testDb match {
      case TestDatabase(driver, url, username, password) => DriverManagerTransactor[Task](driver, url, username, password)
      case _ => assert(false); null
    }
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

      val columnsWithTypes = orderedTableColumns.map { c => (c, getDataType(c)) }

      val primaryKeyConstraints = tableConstraints.filter(_.constraintType.contains("PRIMARY KEY")).flatMap(_.constraintName)
      val uniqueConstraints = tableConstraints.filter(_.constraintType.contains("UNIQUE")).flatMap(_.constraintName)
      val checkConstraints = tableConstraints.filter(_.constraintType.contains("CHECK")).flatMap(_.constraintName)
      val foreignKeyConstraints = tableConstraints.filter(_.constraintType.contains("FOREIGN KEY"))

      val properties = columnsWithTypes.collect { case (column, Some(dataType)) =>

        val nullible = if (column.isNullable.contains("NO")) Some(sql.NotNull) else Some(sql.Null)
        val maybePrimaryKey = if (tableKeyColumnUsage.exists(k => k.columnName == column.columnName && k.constraintName.exists(primaryKeyConstraints.contains))) Some(sql.PrimaryKey) else None
        val default = if (column.columnDefault.isDefined) Some(sql.Default) else None
        val unique = if (tableKeyColumnUsage.exists(k => k.columnName == column.columnName && k.constraintName.exists(uniqueConstraints.contains))) Some(sql.Unique) else None
        val check = if (tableKeyColumnUsage.exists(k => k.columnName == column.columnName && k.constraintName.exists(checkConstraints.contains))) Some(sql.Constraint) else None

        val foreignKeys = tableKeyColumnUsage
          .filter(k => k.columnName == column.columnName)
          .flatMap { c => foreignKeyConstraints.find(_.constraintName == c.constraintName) }
          .flatMap { constraint =>
            val refConstraint = referentialConstraintsByName.get(constraint.constraintSchema).flatMap(_.get(constraint.constraintName)).get
            val refColumns = keyColumnUsageByConstraint.get(refConstraint.uniqueConstraintSchema).flatMap(_.get(refConstraint.uniqueConstraintName)).get

            println(s"Found a foreign key: ${column.tableSchema}.${column.tableName}.${column.columnName}")
            refColumns.foreach(println)

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
          maybePrimaryKey,
          default,
          unique,
          check
        ).flatten ++ foreignKeys

        sql.Column(column.columnName.getOrElse("?"), dataType, columnProperties)
      }

      sql.Table(ref, properties)
    }

    val nonEmptyTables = modelTables.filter(_.columns.nonEmpty)

    DbModel(nonEmptyTables)
  }

  def getDataType(column: db.information_schema.gen.Columns.Row): Option[sql.Type] = {
    column.dataType.flatMap { dataType =>
      val parser = new SqlStatementParser(dataType)
      parser.Type.run().toOption match {
        case t@Some(_) => t
        case None => println(s"Unknown data type: ${dataType}"); None
      }
    }
  }
}
