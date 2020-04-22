package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatArchiver extends PgStatArchiver {

  case class Row(
    archivedCount: Option[Long],
    lastArchivedWal: Option[String],
    lastArchivedTime: Option[Timestamp],
    failedCount: Option[Long],
    lastFailedWal: Option[String],
    lastFailedTime: Option[Timestamp],
    statsReset: Option[Timestamp]
  ) {
    def toShape: Shape = Shape.NoDefaults(archivedCount, lastArchivedWal, lastArchivedTime, failedCount, lastFailedWal, lastFailedTime, statsReset)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"archived_count, last_archived_wal, last_archived_time, failed_count, last_failed_wal, last_failed_time, stats_reset"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".archived_count, " ++ Fragment.const0(a) ++ fr".last_archived_wal, " ++ Fragment.const0(a) ++ fr".last_archived_time, " ++ Fragment.const0(a) ++ fr".failed_count, " ++ Fragment.const0(a) ++ fr".last_failed_wal, " ++ Fragment.const0(a) ++ fr".last_failed_time, " ++ Fragment.const0(a) ++ fr".stats_reset"
  }

  case class Shape(archivedCount: Option[Long] = None, lastArchivedWal: Option[String] = None, lastArchivedTime: Option[Timestamp] = None, failedCount: Option[Long] = None, lastFailedWal: Option[String] = None, lastFailedTime: Option[Timestamp] = None, statsReset: Option[Timestamp] = None)

  object Shape {
    def NoDefaults(archivedCount: Option[Long], lastArchivedWal: Option[String], lastArchivedTime: Option[Timestamp], failedCount: Option[Long], lastFailedWal: Option[String], lastFailedTime: Option[Timestamp], statsReset: Option[Timestamp]): Shape = Shape(archivedCount, lastArchivedWal, lastArchivedTime, failedCount, lastFailedWal, lastFailedTime, statsReset)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2),
        (row) => (row.archivedCount, (row.lastArchivedWal, (row.lastArchivedTime, (row.failedCount, (row.lastFailedWal, (row.lastFailedTime, (row.statsReset)))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2),
        (row) => (row.archivedCount, (row.lastArchivedWal, (row.lastArchivedTime, (row.failedCount, (row.lastFailedWal, (row.lastFailedTime, (row.statsReset)))))))
      )
    }

}
trait PgStatArchiver {
  import PgStatArchiver._

  def create(archivedCount: Option[Long] = None, lastArchivedWal: Option[String] = None, lastArchivedTime: Option[Timestamp] = None, failedCount: Option[Long] = None, lastFailedWal: Option[String] = None, lastFailedTime: Option[Timestamp] = None, statsReset: Option[Timestamp] = None): ConnectionIO[Row] = {
    create(Shape(archivedCount, lastArchivedWal, lastArchivedTime, failedCount, lastFailedWal, lastFailedTime, statsReset))
  }

  def createVoid(archivedCount: Option[Long] = None, lastArchivedWal: Option[String] = None, lastArchivedTime: Option[Timestamp] = None, failedCount: Option[Long] = None, lastFailedWal: Option[String] = None, lastFailedTime: Option[Timestamp] = None, statsReset: Option[Timestamp] = None): ConnectionIO[Unit] = {
    createVoid(Shape(archivedCount, lastArchivedWal, lastArchivedTime, failedCount, lastFailedWal, lastFailedTime, statsReset))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatArchiver.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_stat_archiver (archived_count, last_archived_wal, last_archived_time, failed_count, last_failed_wal, last_failed_time, stats_reset) VALUES (?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatArchiver.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("archived_count", "last_archived_wal", "last_archived_time", "failed_count", "last_failed_wal", "last_failed_time", "stats_reset")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatArchiver.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatArchiver.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatArchiver.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatArchiver.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_stat_archiver
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
      FROM pg_catalog.pg_stat_archiver
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
