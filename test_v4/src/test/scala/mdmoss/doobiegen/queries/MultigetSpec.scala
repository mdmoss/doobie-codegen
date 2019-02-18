package mdmoss.doobiegen.queries

import doobie.imports._
import org.specs2.matcher.ThrownExpectations
import org.specs2.mutable.Specification
import scalaz.concurrent.Task

import mdmoss.doobiegen.db.gen

object MultigetSpec extends Specification with ThrownExpectations {

  val xa = DriverManagerTransactor[Task]("org.postgresql.Driver", "jdbc:postgresql:gen", "test", "test")

  val Fk1RowCount       = 3
  val Fk2RowPerFk1Count = 3

  "multiget query functions" >> {
    val (fk1Rows, fk2Rows) = {
      for {
        fk1 <- gen.TestFk_1.createMany(List.fill(Fk1RowCount)(gen.TestFk_1.Shape()))
        fk2 <- gen.TestFk_2.createMany(fk1.flatMap(fkRow => List.fill(Fk2RowPerFk1Count)(gen.TestFk_2.Shape(fkRow.id))))
      } yield (fk1, fk2)
    }.transact(xa).unsafePerformSync

    "over primary keys must" >> {
      "order returned rows by input order" >> {
        val permutations = fk1Rows.permutations.toList
        permutations must_=== permutations.map { rows =>
          gen.TestFk_1.multiget(rows.map(_.id)).transact(xa).unsafePerformSync
        }
      }

      "return the same row multiple times if a duplicate primary key is specified in the input" >> {
        val permutations = (fk1Rows.tail.head :: fk1Rows).permutations.toList
        permutations must_=== permutations.map { rows =>
          gen.TestFk_1.multiget(rows.map(_.id)).transact(xa).unsafePerformSync
        }
      }
    }

    "over foreign keys must" >> {
      "order returned rows by input order" >> {
        val permutations = fk1Rows.permutations.toList
        val expectedResult = permutations.map(_.flatMap(x => List.fill(Fk2RowPerFk1Count)(x.id)))
        expectedResult must_=== permutations.map { rows =>
          gen.TestFk_2.multigetByFk(rows.map(_.id)).transact(xa).unsafePerformSync.map(_.fk)
        }
      }

      "return the same matching set of rows multiple times if a duplicate foreign key is specified in the input" >> {
        val permutations = (fk1Rows.tail.head :: fk1Rows).permutations.toList
        val expectedResult = permutations.map(_.flatMap(x => List.fill(Fk2RowPerFk1Count)(x.id)))
        expectedResult must_=== permutations.map { rows =>
          gen.TestFk_2.multigetByFk(rows.map(_.id)).transact(xa).unsafePerformSync.map(_.fk)
        }
      }

      "only return all matching rows" >> {
        val fk2RowsByFk = fk2Rows.groupBy(_.fk).mapValues(_.toSet)
        val fkToQuery = fk1Rows.tail.map(_.id)
        val expectedResult = fkToQuery.toSet.flatMap(fk2RowsByFk)
        expectedResult must_=== gen.TestFk_2.multigetByFk(fkToQuery).transact(xa).unsafePerformSync.toSet
      }
    }
  }
}
