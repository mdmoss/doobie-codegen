package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TestPoints extends TestPoints {

  case class Row(
    lat: Double,
    lon: Double
  ) {
    def toShape: Shape = Shape.NoDefaults(lat, lon)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"lat, lon"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".lat, " ++ Fragment.const0(a) ++ fr".lon"
  }

  case class Shape(lat: Double, lon: Double)

  object Shape {
    def NoDefaults(lat: Double, lon: Double): Shape = Shape(lat, lon)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.DoubleMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.DoubleMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.lat, (row.lon))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.DoubleMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.DoubleMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.lat, (row.lon))
      )
    }

}
trait TestPoints {
  import TestPoints._

  def create(lat: Double, lon: Double): ConnectionIO[Row] = {
    create(Shape(lat, lon))
  }

  def createVoid(lat: Double, lon: Double): ConnectionIO[Unit] = {
    createVoid(Shape(lat, lon))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestPoints.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_points (lat, lon) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestPoints.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("lat", "lon")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestPoints.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestPoints.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestPoints.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestPoints.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_points
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
      FROM test_points
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
