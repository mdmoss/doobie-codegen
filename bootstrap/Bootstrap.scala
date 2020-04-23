package mdmoss.doobiegen

import mdmoss.doobiegen.Runner.{Target, TargetVersion, TestDatabase}
import GenOptions._
import mdmoss.doobiegen.Runner.TargetVersion.DoobieV04

object Bootstrap {
  val target = Target(
    schemaDir = "",
    targetVersion = DoobieV04,
    testDb = TestDatabase(
      "org.postgresql.Driver",
      "jdbc:postgresql:gen",
      "test",
      "test"
    ),
    src = "src/",
    `package` = "mdmoss.doobiegen.db",
    statements = None,
    columnOptions = Map(),
    tableSpecificStatements = Map(),
    generateFromTestDb = Some(Runner.Database(
      "org.postgresql.Driver",
      "jdbc:postgresql:gen",
      "test",
      "test"
    )),
    quiet = false,
    filterSchemas = List("information_schema")
  )

  def main(args: Array[String]) {
    Runner.run(target)
  }
}
