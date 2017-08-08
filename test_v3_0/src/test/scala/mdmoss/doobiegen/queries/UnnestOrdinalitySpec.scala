package mdmoss.doobiegen.queries

import doobie.imports._
import org.specs2.mutable.Specification
import doobie.contrib.postgresql.pgtypes._
import org.specs2.matcher.ThrownExpectations

import scalaz.concurrent.Task

object UnnestOrdinalitySpec extends Specification with ThrownExpectations {

  val xa = DriverManagerTransactor[Task]("org.postgresql.Driver", "jdbc:postgresql:gen", "test", "test")

  sequential

  def unnestOrdinalityQ(as: Option[List[Int]], bs: Option[List[String]]) = {
    sql"""SELECT unnest_ordinality.a, unnest_ordinality.b
          FROM unnest_ordinality
          LEFT JOIN unnest(${{as}.toSeq.flatten.toArray}::int[]) WITH ORDINALITY t1(val, ord) ON t1.val = unnest_ordinality.a
          LEFT JOIN unnest(${{bs}.toSeq.flatten.toArray}::text[]) WITH ORDINALITY t2(val, ord) ON t2.val = unnest_ordinality.b

          WHERE (${as.isEmpty} OR unnest_ordinality.a = ANY(${{as}.toSeq.flatten.toArray}))
            AND (${bs.isEmpty} OR unnest_ordinality.b = ANY(${{bs}.toSeq.flatten.toArray}))

          ORDER BY t1.ord, t2.ord
          """.query[(Int, String)]
  }

  "unnest-ordinality based queries should" >> {

    xa.trans(sql"""CREATE TABLE IF NOT EXISTS unnest_ordinality (a INT NOT NULL, b TEXT NOT NULL);""".update.run).run
    xa.trans(sql"""DELETE FROM unnest_ordinality""".update.run).run

    "return nothing as expected from an empty table" >> {
      xa.trans(unnestOrdinalityQ(None, None).list).run must_== List()
    }

    "select everything when there's no filters applied" >> {
      xa.trans(sql"""INSERT INTO unnest_ordinality VALUES (1, 'a'), (2, 'b'), (3, 'a')""".update.run).run
      xa.trans(unnestOrdinalityQ(None, None).list).run.toSet must_== Set((1, "a"), (2, "b"), (3, "a"))
    }

    "filter properly when there's only one filter" >> {
      xa.trans(unnestOrdinalityQ(Some(List(1)), None).list).run must_== List((1, "a"))
    }

    "filter properly cross-filters" >> {
      xa.trans(unnestOrdinalityQ(Some(List(1)), Some(List("c"))).list).run must_== List()
    }

    "return results in the expected order" >> {
      xa.trans(unnestOrdinalityQ(Some(List(1, 2)), None).list).run must_== List((1, "a"), (2, "b"))
      xa.trans(unnestOrdinalityQ(Some(List(2, 1)), None).list).run must_== List((2, "b"), (1, "a"))
    }

    "filter multiple results properly" >> {
      xa.trans(unnestOrdinalityQ(Some(List(1)), Some(List("a"))).list).run must_== List((1, "a"))
    }

    "order in list-order" >> {
      xa.trans(sql"""INSERT INTO unnest_ordinality VALUES (1, 'c'), (3, 'c')""".update.run).run
      xa.trans(unnestOrdinalityQ(Some(List(1, 3)), Some(List("a", "c"))).list).run must_== List((1, "a"), (1, "c"), (3, "a"), (3, "c"))
    }


  }

}
