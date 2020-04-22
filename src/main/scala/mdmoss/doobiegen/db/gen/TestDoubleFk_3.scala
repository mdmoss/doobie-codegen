package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TestDoubleFk_3 extends TestDoubleFk_3 {

  case class Id(value: mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id)

  case class Row(
    id: mdmoss.doobiegen.db.gen.TestDoubleFk_3.Id,
    notpk: mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id
  ) {
    def toShape: Shape = Shape.NoDefaults(id.value, notpk)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"id, notpk"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".id, " ++ Fragment.const0(a) ++ fr".notpk"
  }

  case class Shape(id: mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id, notpk: mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id)

  object Shape {
    def NoDefaults(id: mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id, notpk: mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id): Shape = Shape(id, notpk)
  }

    implicit def TestDoubleFk_3IdComposite: Composite[Id] = {
      implicitly[Composite[mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id]].xmap(
        (f1) => Id(f1),
        (a) => a.value
      )
    }

    private val zippedRowComposite = implicitly[Composite[mdmoss.doobiegen.db.gen.TestDoubleFk_3.Id]]
    .zip(implicitly[Composite[mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id]])

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.id, (row.notpk))
      )
    }

    private val zippedShapeComposite = implicitly[Composite[mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id]]
    .zip(implicitly[Composite[mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id]])

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.id, (row.notpk))
      )
    }

}
trait TestDoubleFk_3 {
  import TestDoubleFk_3._

  def create(id: mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id, notpk: mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id): ConnectionIO[Row] = {
    create(Shape(id, notpk))
  }

  def createVoid(id: mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id, notpk: mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id): ConnectionIO[Unit] = {
    createVoid(Shape(id, notpk))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestDoubleFk_3.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_double_fk_3 (id, notpk) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestDoubleFk_3.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("id", "notpk")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestDoubleFk_3.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestDoubleFk_3.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestDoubleFk_3.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestDoubleFk_3.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def getInner(id: mdmoss.doobiegen.db.gen.TestDoubleFk_3.Id): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_double_fk_3
      WHERE test_double_fk_3.id = ${id}
    """).query[Row]
  }
  def get(id: mdmoss.doobiegen.db.gen.TestDoubleFk_3.Id): ConnectionIO[Row] = {
    getInner(id).unique
  }

  private[gen] def findInner(id: mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_double_fk_3
      WHERE test_double_fk_3.id = ${id}
    """).query[Row]
  }
  def find(id: mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id): ConnectionIO[Option[Row]] = {
    findInner(id).option
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_double_fk_3
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
      FROM test_double_fk_3
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

  private[gen] def multigetInnerBase(id: Option[Seq[mdmoss.doobiegen.db.gen.TestDoubleFk_3.Id]], notpk: Option[Seq[mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id]]): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_double_fk_3
      WHERE (${id.isEmpty} OR test_double_fk_3.id = ANY(${{id}.toSeq.flatten.map(_.value.value.value).toArray}))
    AND (${notpk.isEmpty} OR test_double_fk_3.notpk = ANY(${{notpk}.toSeq.flatten.map(_.value.value).toArray}))
    """).query[Row]
  }

  def multiget(id: Seq[mdmoss.doobiegen.db.gen.TestDoubleFk_3.Id]): ConnectionIO[List[Row]] = {
    if (id.nonEmpty) {
      val distinctValues = id.distinct
      for {
        resultRaw    <- multigetInnerBase(Some(distinctValues), None).list
        resultGrouped = resultRaw.groupBy(_.id)
      } yield id.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }
  def multigetById(id: Seq[mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id]): ConnectionIO[List[Row]] = {
    if (id.nonEmpty) {
      val distinctValues = id.distinct
      for {
        resultRaw    <- multigetInnerBase(Some(distinctValues.map(Id(_))), None).list
        resultGrouped = resultRaw.groupBy(_.id.value)
      } yield id.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }
  def multigetByNotpk(notpk: Seq[mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id]): ConnectionIO[List[Row]] = {
    if (notpk.nonEmpty) {
      val distinctValues = notpk.distinct
      for {
        resultRaw    <- multigetInnerBase(None, Some(distinctValues)).list
        resultGrouped = resultRaw.groupBy(_.notpk)
      } yield notpk.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }
  def getByNotpk(notpk: mdmoss.doobiegen.db.gen.TestDoubleFk_2.Id): ConnectionIO[List[Row]] = {
    multigetInnerBase(None, Some(Seq(notpk))).list
  }

  private[gen] def updateInner(row: mdmoss.doobiegen.db.gen.TestDoubleFk_3.Row): Update0 = {
    sql"""
      UPDATE test_double_fk_3
      SET id = ${row.id}, notpk = ${row.notpk}
      WHERE id = ${row.id}
    """.update
  }
  def update(row: mdmoss.doobiegen.db.gen.TestDoubleFk_3.Row): ConnectionIO[Int] = {
    updateInner(row).run
  }

}
