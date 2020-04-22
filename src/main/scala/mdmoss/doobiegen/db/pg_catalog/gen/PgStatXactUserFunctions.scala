package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatXactUserFunctions extends PgStatXactUserFunctions {

  case class Row(
    calls: Option[Long],
    totalTime: Option[Double],
    selfTime: Option[Double]
  ) {
    def toShape: Shape = Shape.NoDefaults(calls, totalTime, selfTime)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"calls, total_time, self_time"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".calls, " ++ Fragment.const0(a) ++ fr".total_time, " ++ Fragment.const0(a) ++ fr".self_time"
  }

  case class Shape(calls: Option[Long] = None, totalTime: Option[Double] = None, selfTime: Option[Double] = None)

  object Shape {
    def NoDefaults(calls: Option[Long], totalTime: Option[Double], selfTime: Option[Double]): Shape = Shape(calls, totalTime, selfTime)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.DoubleMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.DoubleMeta)))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.calls, (row.totalTime, (row.selfTime)))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.DoubleMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.DoubleMeta)))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.calls, (row.totalTime, (row.selfTime)))
      )
    }

}
trait PgStatXactUserFunctions {
  import PgStatXactUserFunctions._

  def create(calls: Option[Long] = None, totalTime: Option[Double] = None, selfTime: Option[Double] = None): ConnectionIO[Row] = {
    create(Shape(calls, totalTime, selfTime))
  }

  def createVoid(calls: Option[Long] = None, totalTime: Option[Double] = None, selfTime: Option[Double] = None): ConnectionIO[Unit] = {
    createVoid(Shape(calls, totalTime, selfTime))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatXactUserFunctions.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_stat_xact_user_functions (calls, total_time, self_time) VALUES (?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatXactUserFunctions.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("calls", "total_time", "self_time")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatXactUserFunctions.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatXactUserFunctions.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatXactUserFunctions.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatXactUserFunctions.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_stat_xact_user_functions
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
      FROM pg_catalog.pg_stat_xact_user_functions
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
