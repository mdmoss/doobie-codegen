package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatistic extends PgStatistic {

  case class Row(
    staattnum: Short,
    stainherit: Boolean,
    stawidth: Int,
    stakind1: Short,
    stakind2: Short,
    stakind3: Short,
    stakind4: Short,
    stakind5: Short
  ) {
    def toShape: Shape = Shape.NoDefaults(staattnum, stainherit, stawidth, stakind1, stakind2, stakind3, stakind4, stakind5)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"staattnum, stainherit, stawidth, stakind1, stakind2, stakind3, stakind4, stakind5"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".staattnum, " ++ Fragment.const0(a) ++ fr".stainherit, " ++ Fragment.const0(a) ++ fr".stawidth, " ++ Fragment.const0(a) ++ fr".stakind1, " ++ Fragment.const0(a) ++ fr".stakind2, " ++ Fragment.const0(a) ++ fr".stakind3, " ++ Fragment.const0(a) ++ fr".stakind4, " ++ Fragment.const0(a) ++ fr".stakind5"
  }

  case class Shape(staattnum: Short, stainherit: Boolean, stawidth: Int, stakind1: Short, stakind2: Short, stakind3: Short, stakind4: Short, stakind5: Short)

  object Shape {
    def NoDefaults(staattnum: Short, stainherit: Boolean, stawidth: Int, stakind1: Short, stakind2: Short, stakind3: Short, stakind4: Short, stakind5: Short): Shape = Shape(staattnum, stainherit, stawidth, stakind1, stakind2, stakind3, stakind4, stakind5)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta))))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2),
        (row) => (row.staattnum, (row.stainherit, (row.stawidth, (row.stakind1, (row.stakind2, (row.stakind3, (row.stakind4, (row.stakind5))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta))))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2),
        (row) => (row.staattnum, (row.stainherit, (row.stawidth, (row.stakind1, (row.stakind2, (row.stakind3, (row.stakind4, (row.stakind5))))))))
      )
    }

}
trait PgStatistic {
  import PgStatistic._

  def create(staattnum: Short, stainherit: Boolean, stawidth: Int, stakind1: Short, stakind2: Short, stakind3: Short, stakind4: Short, stakind5: Short): ConnectionIO[Row] = {
    create(Shape(staattnum, stainherit, stawidth, stakind1, stakind2, stakind3, stakind4, stakind5))
  }

  def createVoid(staattnum: Short, stainherit: Boolean, stawidth: Int, stakind1: Short, stakind2: Short, stakind3: Short, stakind4: Short, stakind5: Short): ConnectionIO[Unit] = {
    createVoid(Shape(staattnum, stainherit, stawidth, stakind1, stakind2, stakind3, stakind4, stakind5))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatistic.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_statistic (staattnum, stainherit, stawidth, stakind1, stakind2, stakind3, stakind4, stakind5) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatistic.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("staattnum", "stainherit", "stawidth", "stakind1", "stakind2", "stakind3", "stakind4", "stakind5")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatistic.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatistic.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatistic.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatistic.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_statistic
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
      FROM pg_catalog.pg_statistic
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
