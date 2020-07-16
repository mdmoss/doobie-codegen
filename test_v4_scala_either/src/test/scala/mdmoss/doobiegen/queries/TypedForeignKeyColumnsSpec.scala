package mdmoss.doobiegen.queries

import doobie.imports._
import mdmoss.doobiegen.db.gen.TestForeignPk
import org.specs2.mutable.Specification
import scalaz.concurrent.Task

object TypedForeignKeyColumnsSpec extends Specification {

  val transactor = DriverManagerTransactor[Task]("org.postgresql.Driver", "jdbc:postgresql:gen", "test", "test")

  "Foreign key columns" >> {
    "must use the newtype of the column they reference" >> {
      TestForeignPk.allUnbounded().map(_.head.name.value)
      ok
    }
  }
}
