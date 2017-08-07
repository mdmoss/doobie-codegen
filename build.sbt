val commonSettings = Seq(
  name := "doobiegen",
  organization := "mdmoss",
  scalaVersion := "2.11.7",
  scalacOptions ++= Seq("-deprecation", "-feature"),
  scalacOptions in Test ++= Seq("-Yrangepos")
)

lazy val main = (project in file(""))
  .settings(commonSettings:_*)
  .settings(
    libraryDependencies += "org.parboiled" %% "parboiled" % "2.1.4"
  )

lazy val v2deps = Seq(
  "io.argonaut"   %% "argonaut"                  % "6.1",
  "org.tpolecat"  %% "doobie-core"               % "0.2.3",
  "org.tpolecat"  %% "doobie-contrib-postgresql" % "0.2.3",
  "org.tpolecat"  %% "doobie-contrib-specs2"     % "0.2.3" % "test",
  "org.scalaz"    %% "scalaz-core"               % "7.1.9"
)

lazy val v2settings = Seq(
  resolvers += "tpolecat" at "http://dl.bintray.com/tpolecat/maven",
  libraryDependencies ++= v2deps,
  scalaVersion := "2.11.7"
)

lazy val out = (project in file("out"))
  .settings(v2settings)

lazy val test = (project in file("test"))
  .settings(v2settings)
