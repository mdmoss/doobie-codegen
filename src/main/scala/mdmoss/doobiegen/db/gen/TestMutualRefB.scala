package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TestMutualRefB extends TestMutualRefB {

  case class Id(value: Long)

  case class Row(
    id: mdmoss.doobiegen.db.gen.TestMutualRefB.Id,
    other: mdmoss.doobiegen.db.gen.TestMutualRefA.Id
  ) {
    def toShape: Shape = Shape.NoDefaults(id.value, other)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"id, other"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".id, " ++ Fragment.const0(a) ++ fr".other"
  }

  case class Shape(id: Long, other: mdmoss.doobiegen.db.gen.TestMutualRefA.Id)

  object Shape {
    def NoDefaults(id: Long, other: mdmoss.doobiegen.db.gen.TestMutualRefA.Id): Shape = Shape(id, other)
  }

    implicit def TestMutualRefBIdComposite: Composite[Id] = {
      Composite.fromMeta(doobie.util.meta.Meta.LongMeta).xmap(
        (f1) => Id(f1),
        (a) => a.value
      )
    }

    private val zippedRowComposite = implicitly[Composite[mdmoss.doobiegen.db.gen.TestMutualRefB.Id]]
    .zip(implicitly[Composite[mdmoss.doobiegen.db.gen.TestMutualRefA.Id]])

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.id, (row.other))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(implicitly[Composite[mdmoss.doobiegen.db.gen.TestMutualRefA.Id]])

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.id, (row.other))
      )
    }

}
trait TestMutualRefB {
  import TestMutualRefB._

  def create(id: Long, other: mdmoss.doobiegen.db.gen.TestMutualRefA.Id): ConnectionIO[Row] = {
    create(Shape(id, other))
  }

  def createVoid(id: Long, other: mdmoss.doobiegen.db.gen.TestMutualRefA.Id): ConnectionIO[Unit] = {
    createVoid(Shape(id, other))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestMutualRefB.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_mutual_ref_b (id, other) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestMutualRefB.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("id", "other")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestMutualRefB.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestMutualRefB.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestMutualRefB.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestMutualRefB.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def getInner(id: mdmoss.doobiegen.db.gen.TestMutualRefB.Id): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_mutual_ref_b
      WHERE test_mutual_ref_b.id = ${id}
    """).query[Row]
  }
  def get(id: mdmoss.doobiegen.db.gen.TestMutualRefB.Id): ConnectionIO[Row] = {
    getInner(id).unique
  }

  private[gen] def findInner(id: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_mutual_ref_b
      WHERE test_mutual_ref_b.id = ${id}
    """).query[Row]
  }
  def find(id: Long): ConnectionIO[Option[Row]] = {
    findInner(id).option
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_mutual_ref_b
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
      FROM test_mutual_ref_b
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

  private[gen] def multigetInnerBase(id: Option[Seq[mdmoss.doobiegen.db.gen.TestMutualRefB.Id]], other: Option[Seq[mdmoss.doobiegen.db.gen.TestMutualRefA.Id]]): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_mutual_ref_b
      WHERE (${id.isEmpty} OR test_mutual_ref_b.id = ANY(${{id}.toSeq.flatten.map(_.value).toArray}))
    AND (${other.isEmpty} OR test_mutual_ref_b.other = ANY(${{other}.toSeq.flatten.map(_.value).toArray}))
    """).query[Row]
  }

  def multiget(id: Seq[mdmoss.doobiegen.db.gen.TestMutualRefB.Id]): ConnectionIO[List[Row]] = {
    if (id.nonEmpty) {
      val distinctValues = id.distinct
      for {
        resultRaw    <- multigetInnerBase(Some(distinctValues), None).list
        resultGrouped = resultRaw.groupBy(_.id)
      } yield id.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }
  def multigetByOther(other: Seq[mdmoss.doobiegen.db.gen.TestMutualRefA.Id]): ConnectionIO[List[Row]] = {
    if (other.nonEmpty) {
      val distinctValues = other.distinct
      for {
        resultRaw    <- multigetInnerBase(None, Some(distinctValues)).list
        resultGrouped = resultRaw.groupBy(_.other)
      } yield other.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }
  def getByOther(other: mdmoss.doobiegen.db.gen.TestMutualRefA.Id): ConnectionIO[List[Row]] = {
    multigetInnerBase(None, Some(Seq(other))).list
  }

  private[gen] def updateInner(row: mdmoss.doobiegen.db.gen.TestMutualRefB.Row): Update0 = {
    sql"""
      UPDATE test_mutual_ref_b
      SET id = ${row.id}, other = ${row.other}
      WHERE id = ${row.id}
    """.update
  }
  def update(row: mdmoss.doobiegen.db.gen.TestMutualRefB.Row): ConnectionIO[Int] = {
    updateInner(row).run
  }

}
