val commonSettings = Seq(
  organization := "mdmoss",
  scalaVersion := "2.11.7",
  scalacOptions ++= Seq("-deprecation", "-feature"),
  scalacOptions in Test ++= Seq("-Yrangepos")
)

/* Thanks https://tpolecat.github.io/2014/04/11/scalac-flags.html */
val commonScalacOptions = Seq(
  "-deprecation",           
  "-encoding", "UTF-8",       // yes, this is 2 args
  "-feature",                
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",       
  "-Xlint",
  "-Yno-adapted-args",       
  "-Ywarn-dead-code",        // N.B. doesn't work well with the ??? hole
  "-Ywarn-numeric-widen",   
  "-Ywarn-value-discard",
  "-Xfuture"
  //"-Ystatistics:_"
  //"-Ywarn-unused-import"     // 2.11 only - also removed because it's too ambitious right now todo
)

val commonResolvers = Seq(
  "tpolecat" at "http://dl.bintray.com/tpolecat/maven",
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

addCommandAlias("fullTest", 
  ";out_v2_3/test;test_v2_3/test" + 
  ";out_v2_4/test;test_v2_4/test" + 
  ";out_v3_0/test;test_v3_0/test" +
  ";out_v4/test;test_v4/test" +
  ";out_v4_scala_either/test;test_v4_scala_either/test"
)

/* General settings */

lazy val main = (project in file(""))
  .settings(commonSettings:_*)
  .settings(
    /* Don't attempt to compile the sample code. */
    sourcesInBase := false,
    libraryDependencies += "org.parboiled" %% "parboiled" % "2.1.4"
  )
  .settings(
    name := "doobie-codegen"
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
  resolvers ++= commonResolvers,
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
  resolvers ++= commonResolvers,
  libraryDependencies ++= deps_v2_4,
  scalaVersion := "2.11.7"
)

lazy val out_v2_4 = (project in file("out_v2_4"))
  .settings(settings_v2_4)

lazy val test_v2_4 = (project in file("test_v2_4"))
  .settings(settings_v2_4)
  .dependsOn(out_v2_4)

/* Version-specific tests - doobie v3.0 */

lazy val deps_v3_0 = Seq(
  "io.argonaut"   %% "argonaut-scalaz"           % "6.2",
  "org.tpolecat"  %% "doobie-core"               % "0.3.0a",
  "org.tpolecat"  %% "doobie-contrib-postgresql" % "0.3.0a",
  "org.tpolecat"  %% "doobie-contrib-specs2"     % "0.3.0a" % "test",
  "org.scalaz"    %% "scalaz-core"               % "7.2.14"
)

lazy val settings_v3_0 = Seq(
  resolvers ++= commonResolvers,
  libraryDependencies ++= deps_v3_0,
  scalaVersion := "2.12.3"
)

lazy val out_v3_0 = (project in file("out_v3_0"))
  .settings(settings_v3_0)

lazy val test_v3_0 = (project in file("test_v3_0"))
  .settings(settings_v3_0)
  .dependsOn(out_v3_0)

/* Version-specific tests - doobie v4 */

lazy val deps_v4 = Seq(
  "io.argonaut"   %% "argonaut-scalaz"   % "6.2",
  "org.tpolecat"  %% "doobie-core"       % "0.4.2",
  "org.tpolecat"  %% "doobie-postgres"   % "0.4.2",
  "org.tpolecat"  %% "doobie-specs2"     % "0.4.2" % "test",
  "org.scalaz"    %% "scalaz-concurrent" % "7.2.14",
  "org.postgis"   %  "postgis-jdbc"      % "1.3.3"
)

lazy val settings_v4 = Seq(
  resolvers ++= commonResolvers,
  libraryDependencies ++= deps_v4,
  scalaVersion := "2.12.3"
)

lazy val out_v4 = (project in file("out_v4"))
  .settings(settings_v4)
  .settings()
  .settings(
    scalacOptions ++= Seq("-Ystatistics:_")
  )

lazy val test_v4 = (project in file("test_v4"))
  .settings(settings_v4)
  .dependsOn(out_v4)

/* Version-specific tests - doobie v4 scala either */

lazy val deps_v4_scala_either = Seq(
  "io.argonaut"   %% "argonaut"          % "6.2",
  "org.tpolecat"  %% "doobie-core"       % "0.4.2",
  "org.tpolecat"  %% "doobie-postgres"   % "0.4.2",
  "org.tpolecat"  %% "doobie-specs2"     % "0.4.2" % "test",
  "org.scalaz"    %% "scalaz-concurrent" % "7.2.14",
  "org.postgis"   %  "postgis-jdbc"      % "1.3.3"
)

lazy val settings_v4_scala_either = Seq(
  resolvers ++= commonResolvers,
  libraryDependencies ++= deps_v4_scala_either,
  scalaVersion := "2.12.3"
)

lazy val out_v4_scala_either = (project in file("out_v4_scala_either"))
  .settings(settings_v4_scala_either)

lazy val test_v4_scala_either = (project in file("test_v4_scala_either"))
  .settings(settings_v4_scala_either)
  .dependsOn(out_v4_scala_either)
