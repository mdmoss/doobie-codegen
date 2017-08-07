# doobie-codegen

Generates Doobie database code from sql schema files.

[Here's some sample output](sample.scala). Please excuse the style :)

### Features

- Generates Column types for primary keys, and Row types for all tables.
- Generates Shape types for not-yet-inserted rows.
- Can output for doobie version `0.2.3` or `0.2.4`.
- Distinct types for data that is definitely in the database makes reasoning easy(er)!

### Output

##### All tables

- `create: (Shape) => Row`
- `create: (column, column, column...) => Row`
- `createMany: (List[Shape]) => List[Row]`
- `all(offset: Long, limit: Long) => List[Row]`
- `count => List[Row]`

##### Tables with a primary key

- `get: (Id) => Row`
- `find: (Long) => Option[Row]`

`find` takes the type of the primary key column as a parameter.

- `multiget: (Seq[Id]) => List[Row]`

`multiget` and friends are stable! If you ask for B, C, and A, you'll get matching rows back in that order.

- `getByField: (field) => List[Row]`
- `multigetByField: (Seq[field]) => List[Row]`

`getByField` and `multigetByField` are generated for columns containing foreign keys.

- `update: (Row) => Row`

### Caveats

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
