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
    columnA: Long,
    columnB: Long
  ) {
    def toShape: Shape = Shape.NoDefaults(columnA, columnB)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"column_a, column_b"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".column_a, " ++ Fragment.const0(a) ++ fr".column_b"
  }

  case class Shape(columnA: Long, columnB: Long)

  object Shape {
    def NoDefaults(columnA: Long, columnB: Long): Shape = Shape(columnA, columnB)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.LongMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.columnA, (row.columnB))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.LongMeta))

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

  def create(columnA: Long, columnB: Long): ConnectionIO[Row] = {
    create(Shape(columnA, columnB))
  }

  def createVoid(columnA: Long, columnB: Long): ConnectionIO[Unit] = {
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

}
