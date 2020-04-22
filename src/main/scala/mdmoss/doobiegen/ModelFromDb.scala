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
    tables <- readTables.vector
    _ = println(s"Found ${tables.length} tables in information_schema.tables")
    // _ = tables.foreach(println)

    columns <- readColumns.vector
    _  = println(s"Found ${columns.length} columns in information_schema.tables")
    // _ = columns.foreach(println)







  } yield {
    val columnsMap = columns.groupBy(_.table_schema).mapValues(_.groupBy(_.table_name))

    val modelTables = tables.map { table =>
      val ref = sql.TableRef(Some(table.table_schema).filter(_ != "public"), table.table_name)
      val tableColumns = columnsMap.get(table.table_schema).flatMap(_.get(table.table_name)).getOrElse(Vector.empty)
      val columnsWithTypes = tableColumns.map { c => (c, getDataType(c)) }

      val properties = columnsWithTypes.collect { case (column, Some(dataType)) =>

        val nullible = if (column.is_nullable) sql.Null else sql.NotNull

        val columnProperties = Vector(
          nullible
        )

        sql.Column(column.column_name, dataType, columnProperties)
      }

      sql.Table(ref, properties)
    }

    val nonEmptyTables = modelTables.filter(_.columns.nonEmpty)

    DbModel(nonEmptyTables)
  }

  def getDataType(column: InformationSchemaColumnRow): Option[sql.Type] = {
    val parser = new SqlStatementParser(column.data_type)
    parser.Type.run().toOption match {
      case t@Some(_) => t
      case None => println(column.data_type); None
    }
  }


  case class InformationSchemaTableRow(table_schema: String, table_name: String, table_type: String)
  def readTables: Query0[InformationSchemaTableRow] = {
    sql"""
      SELECT table_schema, table_name, table_type
      FROM information_schema.tables
      """.query[InformationSchemaTableRow]
  }

  case class InformationSchemaColumnRow(table_schema: String, table_name: String, column_name: String, data_type: String, numeric_precision: Option[Int], is_nullable: Boolean)
  def readColumns: Query0[InformationSchemaColumnRow] = {
    sql"""
      SELECT table_schema, table_name, column_name, data_type, numeric_precision, is_nullable
      FROM information_schema.columns
      WHERE (table_schema = 'information_schema' OR table_schema = 'pg_catalog')
      """.query[InformationSchemaColumnRow]
  }
}
