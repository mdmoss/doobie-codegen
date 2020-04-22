package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TestSkipBigserial extends TestSkipBigserial {

  case class Id1(value: Long)

  case class Row(
    id1: mdmoss.doobiegen.db.gen.TestSkipBigserial.Id1,
    id2: Long
  ) {
    def toShape: Shape = Shape.NoDefaults(id1.value, id2)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"id1, id2"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".id1, " ++ Fragment.const0(a) ++ fr".id2"
  }

  case class Shape(id1: Long, id2: Long)

  object Shape {
    def NoDefaults(id1: Long, id2: Long): Shape = Shape(id1, id2)
  }

    implicit def TestSkipBigserialIdComposite: Composite[Id1] = {
      Composite.fromMeta(doobie.util.meta.Meta.LongMeta).xmap(
        (f1) => Id1(f1),
        (a) => a.value
      )
    }

    private val zippedRowComposite = implicitly[Composite[mdmoss.doobiegen.db.gen.TestSkipBigserial.Id1]]
    .zip(Composite.fromMeta(doobie.util.meta.Meta.LongMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.id1, (row.id2))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.LongMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.id1, (row.id2))
      )
    }

}
trait TestSkipBigserial {
  import TestSkipBigserial._

  def create(id1: Long, id2: Long): ConnectionIO[Row] = {
    create(Shape(id1, id2))
  }

  def createVoid(id1: Long, id2: Long): ConnectionIO[Unit] = {
    createVoid(Shape(id1, id2))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestSkipBigserial.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_skip_bigserial (id1, id2) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestSkipBigserial.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("id1", "id2")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestSkipBigserial.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestSkipBigserial.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestSkipBigserial.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestSkipBigserial.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def getInner(id1: mdmoss.doobiegen.db.gen.TestSkipBigserial.Id1): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_skip_bigserial
      WHERE test_skip_bigserial.id1 = ${id1}
    """).query[Row]
  }
  def get(id1: mdmoss.doobiegen.db.gen.TestSkipBigserial.Id1): ConnectionIO[Row] = {
    getInner(id1).unique
  }

  private[gen] def findInner(id1: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_skip_bigserial
      WHERE test_skip_bigserial.id1 = ${id1}
    """).query[Row]
  }
  def find(id1: Long): ConnectionIO[Option[Row]] = {
    findInner(id1).option
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_skip_bigserial
      OFFSET $offset
      LIMIT $limit
    """).query[Row]
  }

  def all(offset: Long, limit: Long): ConnectionIO[List[Row]] = {
    allInner(offset, limit).list
  }

  def allUnbounded(): ConnectionIO[List[Row]] = {
    allInner(0, 9223372036854775807L).list
  }

  private[gen] def countInner(): Query0[Long] = {
    sql"""
      SELECT COUNT(*)
      FROM test_skip_bigserial
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

  private[gen] def multigetInnerBase(id1: Option[Seq[mdmoss.doobiegen.db.gen.TestSkipBigserial.Id1]]): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_skip_bigserial
      WHERE (${id1.isEmpty} OR test_skip_bigserial.id1 = ANY(${{id1}.toSeq.flatten.map(_.value).toArray}))
    """).query[Row]
  }

  def multiget(id1: Seq[mdmoss.doobiegen.db.gen.TestSkipBigserial.Id1]): ConnectionIO[List[Row]] = {
    if (id1.nonEmpty) {
      val distinctValues = id1.distinct
      for {
        resultRaw    <- multigetInnerBase(Some(distinctValues)).list
        resultGrouped = resultRaw.groupBy(_.id1)
      } yield id1.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }

  private[gen] def updateInner(row: mdmoss.doobiegen.db.gen.TestSkipBigserial.Row): Update0 = {
    sql"""
      UPDATE test_skip_bigserial
      SET id1 = ${row.id1}, id2 = ${row.id2}
      WHERE id1 = ${row.id1}
    """.update
  }
  def update(row: mdmoss.doobiegen.db.gen.TestSkipBigserial.Row): ConnectionIO[Int] = {
    updateInner(row).run
  }

}
