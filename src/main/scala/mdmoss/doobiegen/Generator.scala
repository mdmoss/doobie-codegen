package mdmoss.doobiegen

import mdmoss.doobiegen.output.File
import mdmoss.doobiegen.sql.Table
import Analysis._
import mdmoss.doobiegen.Runner.{InsertString, TargetVersion, TestDatabase}
import mdmoss.doobiegen.Runner.TargetVersion.{DoobieV023, DoobieV024, DoobieV030, DoobieV04}
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

  implicit class StringOps(s: String) {
    def indented(nSpaces: Int = 2): String = s.replace("\n", "\n" + " " * nSpaces)
    def compressRepeatedBlankLines: String = s.replaceAll("\n( *\n)+", "\n\n")
  }

  def v4Compat = {
    target.targetVersion != TargetVersion.DoobieV023 &&
      target.targetVersion != TargetVersion.DoobieV024 &&
      target.targetVersion != TargetVersion.DoobieV030
  }

  def gen: Seq[File] = {

    val fragmentAvailable = v4Compat

    /* First aim - objects for each database table */

    val tableFiles = db.tables.map { t =>

      def checkTarget(statement: Statement, out: String) = checkTargetStatements(t, statement, out)

      val contents =
        s"""package ${a.targetPackage(t)}
            |
            |/* Todo handle imports better */
            |import doobie.imports._
            |import java.sql.{Date, Timestamp, Time}
            |import java.util.UUID
            |import java.time.{LocalDate, LocalDateTime}
            |import scalaz._, Scalaz._
            |
            |${genImports(t)}
            |
            |object ${a.targetObject(t)} extends ${a.targetObject(t)} {
            |
            |  ${genPkNewType(t).indented()}
            |
            |  ${genRowType(t).indented()}
            |
            |  ${genShapeType(t).indented()}
            |
            |  ${
                  {if (v4Compat) {
                    s"""
                       |${CompositeGen.id(this, t).indented()}
                       |
                       |${CompositeGen.row(this, t).indented()}
                       |
                       |${CompositeGen.shape(this, t).indented()}
                       |
                       |
                     """.stripMargin
                  } else ""

                }.indented()
            }
            |}
            |trait ${a.targetObject(t)} {
            |  import ${a.targetObject(t)}._
            |
            |  ${genMisc(t).indented()}
            |
            |  ${checkTarget(StatementTypes.CreateMany, ppFunctionDef(a.create(t).fn)).indented()}
            |
            |  ${checkTarget(StatementTypes.CreateMany, ppFunctionDef(a.create(t).void)).indented()}
            |
            |  ${checkTarget(StatementTypes.CreateMany, ppFunctionDef(a.insertMany(t).fn)).indented()}
            |
            |  ${checkTarget(StatementTypes.CreateMany, ppFunctionDef(a.createMany(t).process)).indented()}
            |
            |  ${checkTarget(StatementTypes.CreateMany, ppFunctionDef(a.createMany(t).list)).indented()}
            |
            |  ${checkTarget(StatementTypes.CreateMany, ppFunctionDef(a.createMany(t).void)).indented()}
            |
            |  ${checkTarget(StatementTypes.CreateMany, ppFunctionDef(a.createShape(t).fn)).indented()}
            |
            |  ${checkTarget(StatementTypes.CreateMany, ppFunctionDef(a.createShape(t).void)).indented()}
            |
            |  ${a.get(t, fragmentAvailable).map { g =>
                  checkTarget(StatementTypes.Get, ppFunctionDef(g.inner) + "\n" + ppFunctionDef(g.outer))
                }.getOrElse("").indented()}
            |
            |  ${a.find(t, fragmentAvailable).map { f =>
                  checkTarget(StatementTypes.Find, ppFunctionDef(f.inner) + "\n" + ppFunctionDef(f.outer))
               }.getOrElse("").indented()}
            |
            |  ${checkTarget(StatementTypes.All, ppFunctionDef(a.all(t, fragmentAvailable).inner)).indented()}
            |
            |  ${checkTarget(StatementTypes.All, ppFunctionDef(a.all(t, fragmentAvailable).outer)).indented()}
            |
            |  ${checkTarget(StatementTypes.All, ppFunctionDef(a.allUnbounded(t).fn)).indented()}
            |
            |  ${checkTarget(StatementTypes.Count, ppFunctionDef(a.count(t).inner)).indented()}
            |  ${checkTarget(StatementTypes.Count, ppFunctionDef(a.count(t).outer)).indented()}
            |
            |  ${a.baseMultiget(t, fragmentAvailable).map { f =>
                 checkTarget(StatementTypes.MultiGet, ppFunctionDef(f.fn))
                }.getOrElse("").indented()}
            |
            |  ${a.multigets(t, fragmentAvailable).map { m => checkTarget(StatementTypes.MultiGet, ppFunctionDef(m.inner)) }.mkString("\n").indented()}
            |
            |  ${a.update(t).map { u =>
                 checkTarget(StatementTypes.Update, ppFunctionDef(u.inner) + "\n" + ppFunctionDef(u.outer))
              }.getOrElse("").indented()}
            |
            |}
            |""".stripMargin.compressRepeatedBlankLines


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

      val TestInTaskVersions = DoobieV030 :: DoobieV024 :: DoobieV023 :: Nil

      val contents =
        s"""package ${a.targetPackage(t)}
            |
            |/* Todo handle imports better */
            |import doobie.imports._
            |import java.sql.{Date, Timestamp, Time}
            |import java.time.{LocalDate, LocalDateTime}
            |import org.specs2.mutable.Specification
            |import scalaz.concurrent.Task
            |
            |${if (OldStyleContribImports.contains(target.targetVersion))
                "import doobie.contrib.specs2.analysisspec.AnalysisSpec"
               else
                "import doobie.specs2.imports._"
             }
            |
            |import scalaz._, Scalaz._
            |import org.postgis._
            |import java.util.UUID
            |
            |object ${a.targetObject(t)}Spec extends Specification with AnalysisSpec {
            |
            |  ${
                  tr match {
                    case TestDatabase(driver, url, username, password) =>
                      if (TestInTaskVersions.contains(target.targetVersion)) {
                        s"""val transactor = DriverManagerTransactor[Task]("$driver", "$url", "$username", "$password")"""
                      } else {
                        s"""val transactor = DriverManagerTransactor[IOLite]("$driver", "$url", "$username", "$password")"""
                      }

                    case InsertString(str) => str
                  }
                }
            |
            |  ${if (fragmentAvailable) checkAliasedColumnsFragment(t) else ""}
            |
            |  ${checkTarget(StatementTypes.CreateMany, checkTest(t, a.insertMany(t).fn))}
            |
            |  ${a.get(t, fragmentAvailable).map { g => checkTarget(StatementTypes.Get, checkTest(t, g.inner))}.getOrElse("")}
            |
            |  ${a.find(t, fragmentAvailable).map { f => checkTarget(StatementTypes.Find, checkTest(t, f.inner))}.getOrElse("")}
            |
            |  ${checkTarget(StatementTypes.All, checkTest(t, a.all(t, fragmentAvailable).inner))}
            |
            |  ${checkTarget(StatementTypes.Count, checkTest(t, a.count(t).inner))}
            |
            |  ${a.baseMultiget(t, fragmentAvailable).map(f => checkTarget(StatementTypes.MultiGet, checkTest(t, f.fn))).getOrElse("")}
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

    val packageObjects = schemas.map { schema =>

      val tables = db.tables.filter(_.ref.schema == schema)

      val tableObjects = tables.map { t =>
        s"val ${a.targetObject(t)}: ${a.targetPackage(t)}.${a.targetObject(t)} = ${a.targetPackage(t)}.${a.targetObject(t)}"
      }

      /** There's a special case later for schema == NONE, to handle vals for other schemas. */
      def childSchemas = schemas.flatten.map { s =>
        s"val $s: ${target.`package`}.$s.gen.Gen = ${target.`package`}.$s.gen.Gen"
      }.mkString("\n")

      val usePackageObjectVersions = TargetVersion.DoobieV023 :: TargetVersion.DoobieV024 :: Nil
      val usePackageObject = usePackageObjectVersions.contains(target.targetVersion)

      val contents =
        s"""package ${a.targetPackage(tables.head).dropRight(".gen".length)}
           |
           |package ${if (usePackageObject) "object " else ""} gen {
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

  val OldStyleContribImports = DoobieV030 :: DoobieV024 :: DoobieV023 :: Nil

  def genImports(table: Table): String = {
    val types = getTypes(table)

    def ifElseEmpty(b: Boolean, xs: List[String]): List[String] = b match {
      case true => xs
      case false => Nil
    }



    List(
      ifElseEmpty(types.contains(sql.JsonB), List("argonaut.{Json, Parse}", "org.postgresql.util.PGobject")),
      ifElseEmpty(types.contains(sql.Json), List("argonaut.{Json, Parse}", "org.postgresql.util.PGobject")),
      ifElseEmpty(types.contains(sql.Geometry),
        if (OldStyleContribImports.contains(target.targetVersion)) {
          List("org.postgis._", "doobie.contrib.postgresql.pgtypes._")
        } else {
          List("org.postgis._", "doobie.postgres.pgistypes._")
        }),
      ifElseEmpty(
        hasTargetStatements(table, StatementTypes.MultiGet) || getTypes(table).contains(sql.Uuid),
        if (OldStyleContribImports.contains(target.targetVersion)) {
          List("doobie.contrib.postgresql.pgtypes._")
        } else {
          List("doobie.postgres.imports._")
        }
      )
    )
      .flatten
      .distinct
      .map("import " + _)
      .mkString("\n")
  }

  val useNxmap: Set[TargetVersion] = Set(DoobieV023, DoobieV024, DoobieV030)
  val mapMethod: String = if (useNxmap.contains(target.targetVersion)) "nxmap" else "xmap"

  def jsonMetaImpl(jsonType: String) = s"""implicit val JsonMeta: doobie.imports.Meta[Json] =
  doobie.imports.Meta.other[PGobject]("$jsonType").$mapMethod[Json](
    a => Parse.parse(a.getValue).fold(sys.error, identity), // failure raises an exception
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

    val uuidArrayMeta = if (types.contains(sql.Uuid) && target.targetVersion == TargetVersion.DoobieV023) {
      """implicit val UuidArrayMeta: doobie.imports.Meta[Array[UUID]] = doobie.imports.Meta.array("uuid", "_uuid")"""
    } else {
      ""
    }

    val localDateTimeMeta = if (types.contains(sql.Timestamp)) {
      s"""implicit val LocalDateTimeMeta: doobie.imports.Meta[LocalDateTime] =
      doobie.imports.Meta[Timestamp].$mapMethod(_.toLocalDateTime, Timestamp.valueOf)"""
    } else {
      ""
    }

    val localDateMeta = if (types.contains(sql.Date)) {
      s"""implicit val LocalDateMeta: doobie.imports.Meta[LocalDate] =
      doobie.imports.Meta[Date].$mapMethod(_.toLocalDate, Date.valueOf)"""
    } else {
      ""
    }

    List(
      jsonMeta,
      uuidArrayMeta,
      localDateTimeMeta,
      localDateMeta
    ).mkString("\n\n")
  }

  def genPkNewType(table: Table): String = {
    a.pkNewType(table).map { pk =>
      s"case class ${pk._2.symbol}(${pk._1.map(f => s"${f.scalaName}: ${f.scalaType.qualifiedSymbol}").mkString(", ")})"
    }.getOrElse("")
  }

  def genRowType(table: Table): String = {
    val row = a.rowNewType(table)
    val shape = a.rowShape(table)

    val shapeFields = shape._1.map { field =>
      if (field.source.head.isSingularPrimaryKey) field.scalaName + ".value"
      else field.scalaName
    }

    def columnNames = row._1.flatMap(_.source).map(_.sqlName)

    s"""
       |case class ${row._2.symbol}(
       |  ${row._1.map(f => s"${f.scalaName}: ${f.scalaType.qualifiedSymbol}").mkString(",\n  ")}
       |) {
       |  def toShape: ${shape._2.symbol} = ${shape._2.symbol}.NoDefaults(${shapeFields.mkString(", ")})
       |}
       |
       |object ${row._2.symbol} {
       |  ${ if (v4Compat) {
            s"""val ColumnsFragment: Fragment = fr"${columnNames.mkString(", ")}"
               |def aliasedColumnsFragment(a: String): Fragment = ${
              columnNames.map { c => "Fragment.const0(a)" + " ++ fr\"." + c}.mkString(", \" ++ ") + "\""
            }""".stripMargin.indented()
        }}
       |}
     """.stripMargin
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

  def genGet(table: Table, withFragment: Boolean): String = {
    a.get(table, withFragment).map { get =>
      s"""${ppFunctionDef(get.inner)}
         |${ppFunctionDef(get.outer)}
       """.stripMargin
    }.getOrElse("")
  }

  def checkTest(table: Table, fn: FunctionDef): String = {
    val obj = a.targetObject(table)
    s"""check($obj.${fn.name}(${fn.params.map(_.`type`.qualifiedArb).mkString(", ")}))"""
  }

  def checkAliasedColumnsFragment(table: Table): String = {
    val obj = a.targetObject(table)
    val row = a.rowNewType(table)
    s"""check((fr"SELECT" ++ $obj.${row._2.symbol}.aliasedColumnsFragment("test_alias") ++ fr"FROM ${table.ref.fullName} test_alias").query[$obj.${row._2.symbol}])"""
  }

}
