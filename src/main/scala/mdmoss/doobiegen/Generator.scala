package mdmoss.doobiegen

import mdmoss.doobiegen.output.File
import mdmoss.doobiegen.sql.Table
import Analysis._
import mdmoss.doobiegen.Runner.{InsertString, TestDatabase}
import mdmoss.doobiegen.StatementTypes.Statement

class Generator(analysis: Analysis) {

  val a = analysis
  val db = analysis.model
  val target = analysis.target
  val tr = target.testDb

  def hasTargetStatements(table: Table, statement: Statement) = {
    val statementIsTargeted = target.statements.flatMap(_.get(table.ref.fullName)).exists(_.contains(statement))
    (!target.statements.isDefined || statementIsTargeted)
  }

  def checkTargetStatements(table: Table, statement: Statement, out: String) = {
    if (!hasTargetStatements(table, statement)) {
      s"/* ${statement.getClass.getCanonicalName} omitted because of StatementTypes */"
    } else {
      out
    }
  }

  def gen: Seq[File] = {
    /* First aim - objects for each database table */

    val tableFiles = db.tables.map { t =>

      def checkTarget(statement: Statement, out: String) = checkTargetStatements(t, statement, out)

      val contents =
        s"""package ${a.targetPackage(t)}
            |
            |/* Todo handle imports better */
            |import doobie.imports._
            |import java.sql.{Timestamp, Time}
            |import java.util.UUID
            |import java.time.LocalDateTime
            |
            |${genImports(t)}
            |
            |object ${a.targetObject(t)} extends ${a.targetObject(t)} {
            |
            |  ${genPkNewType(t)}
            |
            |  ${genRowType(t)}
            |
            |  ${genShapeType(t)}
            |}
            |
            |trait ${a.targetObject(t)} {
            |  import ${a.targetObject(t)}._
            |
            |  ${genMisc(t)}
            |
            |  ${checkTarget(StatementTypes.CreateMany, ppFunctionDef(a.create(t).fn))}
            |
            |  ${checkTarget(StatementTypes.CreateMany, ppFunctionDef(a.insertMany(t).fn))}
            |
            |  ${checkTarget(StatementTypes.CreateMany, ppFunctionDef(a.createMany(t).process))}
            |
            |  ${checkTarget(StatementTypes.CreateMany, ppFunctionDef(a.createMany(t).list))}
            |
            |  ${checkTarget(StatementTypes.CreateMany, ppFunctionDef(a.createShape(t).fn))}
            |
            |  ${a.get(t).map { g =>
                  checkTarget(StatementTypes.Get, ppFunctionDef(g.inner) + "\n" + ppFunctionDef(g.outer))
                }.getOrElse("")}
            |
            |  ${a.find(t).map { f =>
                  checkTarget(StatementTypes.Find, ppFunctionDef(f.inner) + "\n" + ppFunctionDef(f.outer))
               }.getOrElse("")}
            |
            |  ${checkTarget(StatementTypes.All, ppFunctionDef(a.all(t).inner))}
            |
            |  ${checkTarget(StatementTypes.All, ppFunctionDef(a.all(t).outer))}
            |
            |  ${checkTarget(StatementTypes.All, ppFunctionDef(a.allUnbounded(t).fn))}
            |
            |  ${checkTarget(StatementTypes.Count, ppFunctionDef(a.count(t).inner))}
            |  ${checkTarget(StatementTypes.Count, ppFunctionDef(a.count(t).outer))}
            |
            |  ${a.baseMultiget(t).map { f =>
                 checkTarget(StatementTypes.MultiGet, ppFunctionDef(f.fn))
                }.getOrElse("")}
            |
            |  ${a.multigets(t).map { m => checkTarget(StatementTypes.MultiGet, ppFunctionDef(m.inner)) }.mkString("\n") }
            |
            |  ${a.update(t).map { u =>
                 checkTarget(StatementTypes.Update, ppFunctionDef(u.inner) + "\n" + ppFunctionDef(u.outer))
              }.getOrElse("")}
            |
            |}
         """.stripMargin


      File(
        a.targetPackage(t),
        a.targetObject(t) + ".scala",
        contents,
        isTest = false
      )
    }

    /* We're going to follow up with tests */
    val testFiles = db.tables.map { t =>

      def checkTarget(statement: Statement, out: String) = checkTargetStatements(t, statement, out)

      val contents =
        s"""package ${a.targetPackage(t)}
            |
            |/* Todo handle imports better */
            |import doobie.imports._
            |import java.sql.Timestamp
            |import java.sql.{Timestamp, Time}
            |import java.time.LocalDateTime
            |import org.specs2.mutable.Specification
            |import scalaz.concurrent.Task
            |import doobie.contrib.specs2.analysisspec.AnalysisSpec
            |import scalaz._, Scalaz._
            |import org.postgis._
            |import java.util.UUID
            |
            |object ${a.targetObject(t)}Spec extends Specification with AnalysisSpec {
            |
            |  ${
                  tr match {
                    case TestDatabase(driver, url, username, password) =>
                      s"""val transactor = DriverManagerTransactor[Task]("$driver", "$url", "$username", "$password")"""
                    case InsertString(str) => str
                  }
                }
            |
            |  ${checkTarget(StatementTypes.CreateMany, checkTest(t, a.insertMany(t).fn))}
            |
            |  ${a.get(t).map { g => checkTarget(StatementTypes.Get, checkTest(t, g.inner))}.getOrElse("")}
            |
            |  ${a.find(t).map { f => checkTarget(StatementTypes.Find, checkTest(t, f.inner))}.getOrElse("")}
            |
            |  ${checkTarget(StatementTypes.All, checkTest(t, a.all(t).inner))}
            |
            |  ${checkTarget(StatementTypes.Count, checkTest(t, a.count(t).inner))}
            |
            |  ${a.baseMultiget(t).map(f => checkTarget(StatementTypes.MultiGet, checkTest(t, f.fn))).getOrElse("")}
            |
            |  ${a.update(t).map { u => checkTarget(StatementTypes.Update, checkTest(t, u.inner)) }.mkString("\n") }
            |}
         """.stripMargin

      File(
        a.targetPackage(t),
        a.targetObject(t) + "Spec.scala",
        contents,
        isTest = true
      )
    }

    val schemas = db.tables.map(_.ref.schema).distinct
    println(s"SCHEMAS: $schemas")

    val packageObjects = schemas.map { schema =>

      val tables = db.tables.filter(_.ref.schema == schema)

      val tableObjects = tables.map { t =>
        s"val ${a.targetObject(t)}: ${a.targetPackage(t)}.${a.targetObject(t)} = ${a.targetPackage(t)}.${a.targetObject(t)}"
      }

      /** There's a special case later for schema == NONE, to handle vals for other schemas. */
      def childSchemas = schemas.flatten.map { s =>
        s"val $s: ${target.`package`}.$s.gen.Gen = ${target.`package`}.$s.gen.Gen"
      }.mkString("\n")

      val contents =
        s"""package ${a.targetPackage(tables.head).dropRight(".gen".length)}
           |
           |package object gen {
           |
           |  object Gen extends Gen
           |
           |  trait Gen {
           |    ${if (schema.isEmpty) childSchemas else ""}
           |    ${tableObjects.mkString("\n")}
           |  }
           |}
         """.stripMargin

      File(
        a.targetPackage(tables.head),
        "package.scala",
        contents,
        false
      )
    }

    tableFiles ++ testFiles ++ packageObjects
  }

  def getTypes(table: Table): Set[sql.Type] = {
    table.properties.flatMap {
      case sql.Column(_, sqlType, _) => List(sqlType)
      case _ => List.empty
    }.toSet
  }

  def genImports(table: Table): String = {
    val types = getTypes(table)

    def ifElseEmpty(b: Boolean, xs: List[String]): List[String] = b match {
      case true => xs
      case false => Nil
    }

    List(
      ifElseEmpty(types.contains(sql.JsonB), List("argonaut.{Json, Parse}", "org.postgresql.util.PGobject")),
      ifElseEmpty(types.contains(sql.Json), List("argonaut.{Json, Parse}", "org.postgresql.util.PGobject")),
      ifElseEmpty(types.contains(sql.Geometry), List("org.postgis._")),
      ifElseEmpty(
        hasTargetStatements(table, StatementTypes.MultiGet) || getTypes(table).contains(sql.Uuid),
        List("doobie.contrib.postgresql.pgtypes._")
      ),
      ifElseEmpty(hasTargetStatements(table, StatementTypes.CreateMany), List("scalaz._, Scalaz._"))
    )
      .flatten
      .map("import " + _)
      .mkString("\n")
  }

  def jsonMetaImpl(jsonType: String) = s"""implicit val JsonMeta: doobie.imports.Meta[Json] =
  doobie.imports.Meta.other[PGobject]("$jsonType").nxmap[Json](
    a => Parse.parse(a.getValue).leftMap[Json](sys.error).merge, // failure raises an exception
    a => {
      val p = new PGobject
      p.setType("$jsonType")
      p.setValue(a.nospaces)
      p
    }
  )"""

  def genMisc(table: Table): String = {
    val types = getTypes(table)

    val jsonMeta = if (types.contains(sql.JsonB)) {
      jsonMetaImpl("jsonb")
    } else if (types.contains(sql.Json)) {
      jsonMetaImpl("json")
    } else ""

    val uuidArrayMeta = if (types.contains(sql.Uuid)) {
      """implicit val UuidArrayMeta: doobie.imports.Meta[Array[UUID]] = doobie.imports.Meta.array("uuid", "_uuid")"""
    } else {
      ""
    }

    val localDateTimeMeta = if (types.contains(sql.Timestamp)) {
      """implicit val LocalDateTimeMeta: doobie.imports.Meta[LocalDateTime] =
      doobie.imports.Meta[Timestamp].nxmap(_.toLocalDateTime, Timestamp.valueOf(_))"""
    } else {
      ""
    }

    jsonMeta + "\n\n" + uuidArrayMeta + "\n\n" + localDateTimeMeta
  }

  def genPkNewType(table: Table): String = {
    a.pkNewType(table).map { pk =>
      s"case class ${pk._2.symbol}(${pk._1.map(f => s"${f.scalaName}: ${f.scalaType.qualifiedSymbol}").mkString(", ")})"
    }.getOrElse("")
  }

  def genRowType(table: Table): String = {
    val row = a.rowNewType(table)
    s"case class ${row._2.symbol}(${row._1.map(f => s"${f.scalaName}: ${f.scalaType.qualifiedSymbol}").mkString(", ")})"
  }

  def genShapeType(table: Table): String = {
    val shape = a.rowShape(table)
    s"""
       |case class ${shape._2.symbol}(${shape._1.map(f => s"${f.scalaName}: ${f.scalaType.qualifiedSymbol}" + f.defaultValue.map(d => s" = $d").getOrElse("")).mkString(", ")})
       |
       |object ${shape._2.symbol} {
       |  def NoDefaults(${shape._1.map(f => s"${f.scalaName}: ${f.scalaType.qualifiedSymbol}").mkString(", ")}): ${shape._2.symbol} = ${shape._2.symbol}(${shape._1.map(_.scalaName).mkString(", ")})
       |}
     """.stripMargin
  }

  def genInsert(table: Table): String = {
    val in = a.insert(table)
    ppFunctionDef(in.fn)
  }

  def genCreate(table: Table): String = {
    val create = a.create(table)
    ppFunctionDef(create.fn)
  }

  def ppFunctionDef(fn: FunctionDef): String = fn.pp

  def genGet(table: Table): String = {
    a.get(table).map { get =>
      s"""${ppFunctionDef(get.inner)}
         |${ppFunctionDef(get.outer)}
       """.stripMargin
    }.getOrElse("")
  }

  def checkTest(table: Table, fn: FunctionDef): String = {
    val obj = a.targetObject(table)
    s"""check($obj.${fn.name}(${fn.params.map(_.`type`.qualifiedArb).mkString(", ")}))"""
  }

}
