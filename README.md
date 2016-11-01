# doobie-codegen

Generates Doobie database code from sql statements.

Currently only targets PostgreSQL. Attempts to output straightforward code and tests.

This probably won't work unless your sql looks like my sql. Could be made more robust.

Definitely a work in progress! But has CI, now.

### Usage

I tend to add a project to my build.sbt like the following.

```
lazy val gen = (project in file("doobie-codegen"))
  .settings(
    scalaVersion := "2.11.7",
    resolvers += "Jitpack" at "https://jitpack.io",
    libraryDependencies += "com.github.mdmoss" %% "doobie-codegen" % "v0.0.8"
  )
```

Then in the doobie-codegen directory, I add an object with a main something like the following:

```
package gen

import mdmoss.doobiegen.Runner.{Target, TestDatabase}

object Generator {

  def main(args: Array[String]) {

    val target = Target(
      "schema/",
      TestDatabase(
        "org.postgresql.Driver",
        "jdbc:postgresql:db",
        "user",
        "password"
      ),
      "src",
      "com.project.db"
    )

    mdmoss.doobiegen.Runner.run(target)
  }
}
```

Then run the project with sbt `gen/run`
