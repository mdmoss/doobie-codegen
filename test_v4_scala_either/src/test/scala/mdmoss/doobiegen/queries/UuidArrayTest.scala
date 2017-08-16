package mdmoss.doobiegen.queries

import doobie.postgres.imports._
import doobie.specs2.imports._
import doobie.imports._
import mdmoss.doobiegen.db.gen.TestUuidMultiget
import org.specs2.mutable.Specification
import java.util.UUID

import scalaz.concurrent.Task

object UuidArrayTest extends Specification {

  val transactor = DriverManagerTransactor[Task]("org.postgresql.Driver", "jdbc:postgresql:gen", "test", "test")

  "UUID Arrays" >> {

    "must work as expected for single elements" >> {

      val uuid = UUID.randomUUID()

      val work = for {
        uuidRow <- TestUuidMultiget.create(uuid)
        result  <- TestUuidMultiget.multiget(uuidRow.uuid :: Nil)
      } yield result

      work.transact(transactor).run.head.uuid.value must_== uuid
    }

    "must work as expected for multiple elements" >> {

      val uuids = UUID.randomUUID() :: UUID.randomUUID() :: UUID.randomUUID() :: Nil

      val work = for {
        uuidRows <- TestUuidMultiget.createMany(uuids.map(TestUuidMultiget.Shape(_)))
        result   <- TestUuidMultiget.multiget(uuidRows.map(_.uuid))
      } yield result

      work.transact(transactor).run.map(_.uuid.value).toSet must_== uuids.toSet
    }

  }

}