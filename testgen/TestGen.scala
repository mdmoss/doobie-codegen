package mdmoss.doobiegen

import mdmoss.doobiegen.Runner.{Target, TestDatabase, TargetVersion}
import GenOptions._

object TestGen {

  val target_v2_3 = Target(
    schemaDir = "",
    testDb = TestDatabase(
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
    ),
    tableSpecificStatements = Map(
      "test_filtered_multiget" -> (StatementTypes.RefinedMultiget(excludeColumns = Some("column_b" :: Nil)) :: Nil)
    ),
    generateFromTestDb = Some(Runner.Database(
      "org.postgresql.Driver",
      "jdbc:postgresql:gen",
      "test",
      "test"
    ))
  )

  val target_v2_4 = target_v2_3.copy(
    src = "out_v2_4/src",
    targetVersion = TargetVersion.DoobieV024
  )

  val target_v3_0 = target_v2_3.copy(
    src = "out_v3_0/src",
    targetVersion = TargetVersion.DoobieV030
  )

  val target_v4 = target_v2_3.copy(
    src = "out_v4/src",
    targetVersion = TargetVersion.DoobieV04
  )

  val target_v4_scala_either = target_v4.copy(
    src = "out_v4_scala_either/src",
    targetVersion = TargetVersion.DoobieV04
  )

  def main(args: Array[String]) {
    Runner.run(target_v2_3)
    Runner.run(target_v2_4)
    Runner.run(target_v3_0)
    Runner.run(target_v4)
    Runner.run(target_v4_scala_either)
  }
}
