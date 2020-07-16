package mdmoss.doobiegen

import java.io.{File, PrintWriter}
import java.nio.file.Paths

import mdmoss.doobiegen.GenOptions.{GenOption, Ignore}
import mdmoss.doobiegen.StatementTypes.Statement
import mdmoss.doobiegen.output.SourceWriter
import mdmoss.doobiegen.sql.TableRef
import org.parboiled2.ParseError

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}

object Runner {

  sealed trait TestDBSource
  case class TestDatabase(driver: String, url: String, username: String, password: String) extends TestDBSource
  case class InsertString(source: String) extends TestDBSource

  case class Database(driver: String, url: String, username: String, password: String)

  sealed trait TargetVersion

  object TargetVersion {
    case object DoobieV023 extends TargetVersion
    case object DoobieV024 extends TargetVersion
    case object DoobieV030 extends TargetVersion
    case object DoobieV04 extends TargetVersion
  }

  case class Target(
    schemaDir: String,
    testDb: TestDBSource,
    src: String,
    `package`: String,
    statements: Option[Map[String, List[Statement]]] = None,
    columnOptions: Map[String, Map[String, List[GenOption]]] = Map(),
    quiet: Boolean = false,
    targetVersion: TargetVersion = TargetVersion.DoobieV023,
    // This is mainly an override for testing
    tableSpecificStatements: Map[String, List[Statement]] = Map.empty,
    generateFromTestDb: Option[Database] = None,

    /**
      * Filter output to particular schemas or tables.
      * An empty map will generate for all schemas (except information_schema and pg_catalog)
      * An empty list will generate all tables within a schema.
      */
    filterSchemasAndTables: Map[String, List[String]] = Map()
  ) {
    def enclosingPackage = `package`.split('.').reverse.headOption
  }

  val Default = Target(
    schemaDir = "sql/",
    TestDatabase(
      "org.postgresql.Driver",
      "jdbc:postgresql:gen",
      "test",
      "test"
    ),
    src = "out/src",
    `package` = "mdmoss.doobiegen.db"
  )

  def loadSqlModel(target: Target) = {
    /* We could do something more intelligent here, but this works for now */
    val sql = {
      import scala.sys.process._
      val buffer = new ListBuffer[String]()
      val proc = ("bash" :: "-c" :: s"cat ${target.schemaDir}*.sql" :: Nil).run(ProcessLogger(s => buffer.append(s)))
      proc.exitValue()
      buffer.mkString("\n")
    }

    /* We need to clean the input a little */
    import ParserUtils.SqlStringHelpers
    val cleanedAndSplit = sql
      .removeOffSections
      .stripComments
      .trim
      .splitOnUnescapedSemicolons
      .map(_.trim)

    val parsers = cleanedAndSplit.map(new SqlStatementParser(_))

    if (target.quiet) {
      parsers.foreach { s =>
        s.StatementLine.run() match {
          case r@Success(_) => ()
          case r@Failure(f) => f match {
            case e@ParseError(_, _, _) =>
              println(Seperator)
              println(s.input.sliceString(0, s.input.length))
              println(s.formatError(e))
              throw f
          }
        }
      }
    } else {
      parsers.foreach { s =>
        println(Seperator)
        println(s.input.sliceString(0, s.input.length))
        s.StatementLine.run() match {
          case r@Success(_) => println(r)
          case r@Failure(f) => f match {
            case e@ParseError(_, _, _) => println(s.formatError(e))
              throw f
          }
        }
      }
    }



    val statements = parsers.flatMap(_.StatementLine.run().toOption)

    if (!target.quiet) {
      println(Seperator)
    }

    if (statements.length != parsers.length) throw new Throwable("Failed parsing. Exiting.")

    statements.foldLeft(DbModel.empty)(DbModel.update)
  }

  val DefaultExcludeSchemas = List(
    "information_schema",
    "pg_catalog"
  )

  val DefaultExcludeTables = List(
    "geography_columns",
    "geometry_columns",
    "raster_columns",
    "raster_overviews"
  )

  def isAllowedByFilter(target: Target, ref: TableRef): Boolean = {
    val allowedForSchema = target.filterSchemasAndTables.get(ref.schema.getOrElse("public"))

    val schemaImplicitlyAllowed = target.filterSchemasAndTables.isEmpty && !ref.schema.exists(DefaultExcludeSchemas.contains)
    val schemaExplicitlyAllowed = allowedForSchema.isDefined

    val tableImplicitlyAllowed = allowedForSchema.forall(_.isEmpty) && !DefaultExcludeTables.contains(ref.sqlName)
    val tableExplicitlyAllowed = allowedForSchema.exists(_.contains(ref.sqlName))

    (schemaImplicitlyAllowed || schemaExplicitlyAllowed) && (tableImplicitlyAllowed || tableExplicitlyAllowed)
  }

  def run(target: Target) = {
    val model = target.generateFromTestDb match {
      case Some(db) => ModelFromDb(target, db)
      case None => loadSqlModel(target)
    }

    val targetSchemas = model.tables.filter(t => isAllowedByFilter(target, t.ref))

    if (!target.quiet) {
      targetSchemas.foreach { s =>
        println("========================================================================")
        println(s.ref)
        s.properties.foreach(println)
      }
    }

    val filteredModel = FilterIgnoredFields(model.copy(tables = targetSchemas), target)

    val analysis = new Analysis(filteredModel, target)

    val generator = new Generator(analysis)

    val files = generator.gen

    cleanOldGenDirectories(Paths.get(target.src), files)
    SourceWriter.write(Paths.get(target.src), files)
  }

  def cleanOldGenDirectories(sourceRoot: java.nio.file.Path, files: Seq[output.File]): Unit = {
    files.foreach { f =>
      Try {
        val mainOrTest = if (f.isTest) "test" else "main"

        val destDir = Paths.get(sourceRoot.toString, List(mainOrTest, "scala") ++ f.`package`.split('.'):_*)

        /* This is very heavy-handed. Todo make this nicer */

        /* Step one - go up a level, descend a level */
        destDir.toFile.getParentFile.listFiles()
          .filter(_.isDirectory)
          .flatMap(_.listFiles())
          .filter(_.isDirectory)
          .filter(_.toPath.endsWith("gen"))
          .foreach(delete)

        /* Step two - go up a level */
        destDir.toFile.getParentFile.listFiles()
          .filter(_.isDirectory)
          .filter(_.toPath.endsWith("gen"))
          .foreach(delete)
      }
    }
  }

  def delete(file: File): Unit = {
    if (file.isDirectory) {
      file.listFiles().foreach(delete)
      file.delete()
    } else {
      file.delete()
    }
  }

  val Seperator = "*" * 80
}
