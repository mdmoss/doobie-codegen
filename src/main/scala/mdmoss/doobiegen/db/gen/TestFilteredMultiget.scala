package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TestFilteredMultiget extends TestFilteredMultiget {

  case class Row(
    columnA: mdmoss.doobiegen.db.gen.TestFk_1.Id,
    columnB: mdmoss.doobiegen.db.gen.TestFk_2.Id
  ) {
    def toShape: Shape = Shape.NoDefaults(columnA, columnB)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"column_a, column_b"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".column_a, " ++ Fragment.const0(a) ++ fr".column_b"
  }

  case class Shape(columnA: mdmoss.doobiegen.db.gen.TestFk_1.Id, columnB: mdmoss.doobiegen.db.gen.TestFk_2.Id)

  object Shape {
    def NoDefaults(columnA: mdmoss.doobiegen.db.gen.TestFk_1.Id, columnB: mdmoss.doobiegen.db.gen.TestFk_2.Id): Shape = Shape(columnA, columnB)
  }

    private val zippedRowComposite = implicitly[Composite[mdmoss.doobiegen.db.gen.TestFk_1.Id]]
    .zip(implicitly[Composite[mdmoss.doobiegen.db.gen.TestFk_2.Id]])

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.columnA, (row.columnB))
      )
    }

    private val zippedShapeComposite = implicitly[Composite[mdmoss.doobiegen.db.gen.TestFk_1.Id]]
    .zip(implicitly[Composite[mdmoss.doobiegen.db.gen.TestFk_2.Id]])

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.columnA, (row.columnB))
      )
    }

}
trait TestFilteredMultiget {
  import TestFilteredMultiget._

  def create(columnA: mdmoss.doobiegen.db.gen.TestFk_1.Id, columnB: mdmoss.doobiegen.db.gen.TestFk_2.Id): ConnectionIO[Row] = {
    create(Shape(columnA, columnB))
  }

  def createVoid(columnA: mdmoss.doobiegen.db.gen.TestFk_1.Id, columnB: mdmoss.doobiegen.db.gen.TestFk_2.Id): ConnectionIO[Unit] = {
    createVoid(Shape(columnA, columnB))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestFilteredMultiget.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_filtered_multiget (column_a, column_b) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestFilteredMultiget.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("column_a", "column_b")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestFilteredMultiget.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestFilteredMultiget.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestFilteredMultiget.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestFilteredMultiget.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_filtered_multiget
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
      FROM test_filtered_multiget
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

  private[gen] def multigetInnerBase(columnA: Option[Seq[mdmoss.doobiegen.db.gen.TestFk_1.Id]], columnB: Option[Seq[mdmoss.doobiegen.db.gen.TestFk_2.Id]]): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_filtered_multiget
      WHERE (${columnA.isEmpty} OR test_filtered_multiget.column_a = ANY(${{columnA}.toSeq.flatten.map(_.value).toArray}))
    AND (${columnB.isEmpty} OR test_filtered_multiget.column_b = ANY(${{columnB}.toSeq.flatten.map(_.value).toArray}))
    """).query[Row]
  }

  def multigetByColumnA(columnA: Seq[mdmoss.doobiegen.db.gen.TestFk_1.Id]): ConnectionIO[List[Row]] = {
    if (columnA.nonEmpty) {
      val distinctValues = columnA.distinct
      for {
        resultRaw    <- multigetInnerBase(Some(distinctValues), None).list
        resultGrouped = resultRaw.groupBy(_.columnA)
      } yield columnA.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }
  def multigetByColumnB(columnB: Seq[mdmoss.doobiegen.db.gen.TestFk_2.Id]): ConnectionIO[List[Row]] = {
    if (columnB.nonEmpty) {
      val distinctValues = columnB.distinct
      for {
        resultRaw    <- multigetInnerBase(None, Some(distinctValues)).list
        resultGrouped = resultRaw.groupBy(_.columnB)
      } yield columnB.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }
  def getByColumnA(columnA: mdmoss.doobiegen.db.gen.TestFk_1.Id): ConnectionIO[List[Row]] = {
    multigetInnerBase(Some(Seq(columnA)), None).list
  }
  def getByColumnB(columnB: mdmoss.doobiegen.db.gen.TestFk_2.Id): ConnectionIO[List[Row]] = {
    multigetInnerBase(None, Some(Seq(columnB))).list
  }

}
