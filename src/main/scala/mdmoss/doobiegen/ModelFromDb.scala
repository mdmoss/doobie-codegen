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

  } yield {
    val columnsMap = columns.groupBy(_.tableSchema).mapValues(_.groupBy(_.tableName))
    val tableConstraintsMap = tableConstraints.groupBy(_.tableSchema).mapValues(_.groupBy(_.tableName))
    val keyColumnUsageMap = keyColumnUsage.groupBy(_.tableSchema).mapValues(_.groupBy(_.tableName))

    val modelTables = tables.map { table =>
      val ref = sql.TableRef(table.tableSchema.filter(_ != "public"), table.tableName.getOrElse("?"))

      val tableColumns = columnsMap.get(table.tableSchema).flatMap(_.get(table.tableName)).getOrElse(List.empty)

      val orderedTableColumns = tableColumns.sortBy(_.ordinalPosition.getOrElse(0))

      val tableConstraints = tableConstraintsMap.get(table.tableSchema).flatMap(_.get(table.tableName)).getOrElse(List.empty)
      val tableKeyColumnUsage = keyColumnUsageMap.get(table.tableSchema).flatMap(_.get(table.tableName)).getOrElse(List.empty)

      val columnsWithTypes = orderedTableColumns.map { c => (c, getDataType(c)) }

      val primaryKeyConstraints = tableConstraints.filter(_.constraintType.contains("PRIMARY KEY")).flatMap(_.constraintName)

      val properties = columnsWithTypes.collect { case (column, Some(dataType)) =>

        val nullible = if (column.isNullable.contains("NO")) Some(sql.NotNull) else Some(sql.Null)
        val maybePrimaryKey = if (tableKeyColumnUsage.exists(k => k.columnName == column.columnName && k.constraintName.exists(primaryKeyConstraints.contains))) Some(sql.PrimaryKey) else None
        val default = if (column.columnDefault.isDefined) Some(sql.Default) else None

        val columnProperties = Vector(
          nullible,
          maybePrimaryKey,
          default
        ).flatten

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
