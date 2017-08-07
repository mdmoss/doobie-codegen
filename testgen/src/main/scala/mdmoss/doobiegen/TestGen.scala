package mdmoss.doobiegen

import mdmoss.doobiegen.Runner.{Target, TestDatabase, TargetVersion}
import GenOptions._

object TestGen {

  val target_v2_3 = Target(
    schemaDir = "sql/",
    TestDatabase(
      "org.postgresql.Driver",
      "jdbc:postgresql:gen",
      "test",
      "test"
    ),
    src = "out_v2_3/src",
    `package` = "mdmoss.doobiegen.db",
    statements = None,
    columnOptions = Map(
      "test_gen_options" -> Map(
        "created_at" -> (NoWrite :: Nil),
        "thing_with_default" -> (AlwaysInsertDefault :: Nil),
        "nullible_thing_with_default" -> (AlwaysInsertDefault :: Nil),
        "test_ignore_default" -> (IgnoreDefault :: Nil)
      ),
      "test_column_ignore" -> Map(
        "ignore_me" -> (Ignore :: Nil)
      )
    )
  )

  val target_v2_4 = target_v2_3.copy(
    src = "out_v2_4/src",
    targetVersion = TargetVersion.DoobieV024
  )

  def main(args: Array[String]) {
    Runner.run(target_v2_3)
    Runner.run(target_v2_4)
  }
}