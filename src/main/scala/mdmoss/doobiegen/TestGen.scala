package mdmoss.doobiegen

import mdmoss.doobiegen.Runner.{Target, TestDatabase}
import GenOptions._

object TestGen {

  val TestTarget = Target(
    schemaDir = "sql/",
    TestDatabase(
      "org.postgresql.Driver",
      "jdbc:postgresql:gen",
      "test",
      "test"
    ),
    src = "out/src",
    `package` = "mdmoss.doobiegen.db",
    statements = None,
    columnOptions = Map(
      "test_gen_options" -> Map(
        "created_at" -> (NoWrite :: Nil),
        "thing_with_default" -> (ScalaDefault("\"Hello\"") :: Nil),
        "nullible_thing_with_default" -> (ScalaDefault("Some(\"Hello\")") :: Nil)
      )
    )
  )

  def main(args: Array[String]) {
    Runner.run(TestTarget)
  }
}