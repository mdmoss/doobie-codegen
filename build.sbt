val commonSettings = Seq(
  organization := "mdmoss",
  scalaVersion := "2.11.7",
  scalacOptions ++= Seq("-deprecation", "-feature"),
  scalacOptions in Test ++= Seq("-Yrangepos")
)

addCommandAlias("fullTest", ";out_v2_3/test;test_v2_3/test;out_v2_4/test;test_v2_4/test")

/* General settings */

lazy val main = (project in file(""))
  .settings(commonSettings:_*)
  .settings(
    /* Don't attempt to compile the sample code. */
    sourcesInBase := false,
    libraryDependencies += "org.parboiled" %% "parboiled" % "2.1.4"
  )

/* Test generation settings */

lazy val testgen = (project in file("testgen"))
  .settings(commonSettings:_*)
  .dependsOn(main)

/* Version-specific tests - doobie v2.3 */

lazy val deps_v2_3 = Seq(
  "io.argonaut"   %% "argonaut"                  % "6.1",
  "org.tpolecat"  %% "doobie-core"               % "0.2.3",
  "org.tpolecat"  %% "doobie-contrib-postgresql" % "0.2.3",
  "org.tpolecat"  %% "doobie-contrib-specs2"     % "0.2.3" % "test",
  "org.scalaz"    %% "scalaz-core"               % "7.1.14"
)

lazy val settings_v2_3 = Seq(
  resolvers += "tpolecat" at "http://dl.bintray.com/tpolecat/maven",
  libraryDependencies ++= deps_v2_3,
  scalaVersion := "2.11.7"
)

lazy val out_v2_3 = (project in file("out_v2_3"))
  .settings(settings_v2_3)

lazy val test_v2_3 = (project in file("test_v2_3"))
  .settings(settings_v2_3)
  .dependsOn(out_v2_3)

/* Version-specific tests - doobie v2.4 */

lazy val deps_v2_4 = Seq(
  "io.argonaut"   %% "argonaut"                  % "6.1",
  "org.tpolecat"  %% "doobie-core"               % "0.2.4",
  "org.tpolecat"  %% "doobie-contrib-postgresql" % "0.2.4",
  "org.tpolecat"  %% "doobie-contrib-specs2"     % "0.2.4" % "test",
  "org.scalaz"    %% "scalaz-core"               % "7.1.14"
)

lazy val settings_v2_4 = Seq(
  resolvers += "tpolecat" at "http://dl.bintray.com/tpolecat/maven",
  libraryDependencies ++= deps_v2_4,
  scalaVersion := "2.11.7"
)

lazy val out_v2_4 = (project in file("out_v2_4"))
  .settings(settings_v2_4)

lazy val test_v2_4 = (project in file("test_v2_4"))
  .settings(settings_v2_4)
  .dependsOn(out_v2_4)


