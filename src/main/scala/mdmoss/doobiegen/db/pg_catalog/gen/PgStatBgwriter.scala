package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatBgwriter extends PgStatBgwriter {

  case class Row(
    checkpointsTimed: Option[Long],
    checkpointsReq: Option[Long],
    checkpointWriteTime: Option[Double],
    checkpointSyncTime: Option[Double],
    buffersCheckpoint: Option[Long],
    buffersClean: Option[Long],
    maxwrittenClean: Option[Long],
    buffersBackend: Option[Long],
    buffersBackendFsync: Option[Long],
    buffersAlloc: Option[Long],
    statsReset: Option[Timestamp]
  ) {
    def toShape: Shape = Shape.NoDefaults(checkpointsTimed, checkpointsReq, checkpointWriteTime, checkpointSyncTime, buffersCheckpoint, buffersClean, maxwrittenClean, buffersBackend, buffersBackendFsync, buffersAlloc, statsReset)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"checkpoints_timed, checkpoints_req, checkpoint_write_time, checkpoint_sync_time, buffers_checkpoint, buffers_clean, maxwritten_clean, buffers_backend, buffers_backend_fsync, buffers_alloc, stats_reset"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".checkpoints_timed, " ++ Fragment.const0(a) ++ fr".checkpoints_req, " ++ Fragment.const0(a) ++ fr".checkpoint_write_time, " ++ Fragment.const0(a) ++ fr".checkpoint_sync_time, " ++ Fragment.const0(a) ++ fr".buffers_checkpoint, " ++ Fragment.const0(a) ++ fr".buffers_clean, " ++ Fragment.const0(a) ++ fr".maxwritten_clean, " ++ Fragment.const0(a) ++ fr".buffers_backend, " ++ Fragment.const0(a) ++ fr".buffers_backend_fsync, " ++ Fragment.const0(a) ++ fr".buffers_alloc, " ++ Fragment.const0(a) ++ fr".stats_reset"
  }

  case class Shape(checkpointsTimed: Option[Long] = None, checkpointsReq: Option[Long] = None, checkpointWriteTime: Option[Double] = None, checkpointSyncTime: Option[Double] = None, buffersCheckpoint: Option[Long] = None, buffersClean: Option[Long] = None, maxwrittenClean: Option[Long] = None, buffersBackend: Option[Long] = None, buffersBackendFsync: Option[Long] = None, buffersAlloc: Option[Long] = None, statsReset: Option[Timestamp] = None)

  object Shape {
    def NoDefaults(checkpointsTimed: Option[Long], checkpointsReq: Option[Long], checkpointWriteTime: Option[Double], checkpointSyncTime: Option[Double], buffersCheckpoint: Option[Long], buffersClean: Option[Long], maxwrittenClean: Option[Long], buffersBackend: Option[Long], buffersBackendFsync: Option[Long], buffersAlloc: Option[Long], statsReset: Option[Timestamp]): Shape = Shape(checkpointsTimed, checkpointsReq, checkpointWriteTime, checkpointSyncTime, buffersCheckpoint, buffersClean, maxwrittenClean, buffersBackend, buffersBackendFsync, buffersAlloc, statsReset)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.DoubleMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.DoubleMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)))))))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.checkpointsTimed, (row.checkpointsReq, (row.checkpointWriteTime, (row.checkpointSyncTime, (row.buffersCheckpoint, (row.buffersClean, (row.maxwrittenClean, (row.buffersBackend, (row.buffersBackendFsync, (row.buffersAlloc, (row.statsReset)))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.DoubleMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.DoubleMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)))))))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.checkpointsTimed, (row.checkpointsReq, (row.checkpointWriteTime, (row.checkpointSyncTime, (row.buffersCheckpoint, (row.buffersClean, (row.maxwrittenClean, (row.buffersBackend, (row.buffersBackendFsync, (row.buffersAlloc, (row.statsReset)))))))))))
      )
    }

}
trait PgStatBgwriter {
  import PgStatBgwriter._

  def create(checkpointsTimed: Option[Long] = None, checkpointsReq: Option[Long] = None, checkpointWriteTime: Option[Double] = None, checkpointSyncTime: Option[Double] = None, buffersCheckpoint: Option[Long] = None, buffersClean: Option[Long] = None, maxwrittenClean: Option[Long] = None, buffersBackend: Option[Long] = None, buffersBackendFsync: Option[Long] = None, buffersAlloc: Option[Long] = None, statsReset: Option[Timestamp] = None): ConnectionIO[Row] = {
    create(Shape(checkpointsTimed, checkpointsReq, checkpointWriteTime, checkpointSyncTime, buffersCheckpoint, buffersClean, maxwrittenClean, buffersBackend, buffersBackendFsync, buffersAlloc, statsReset))
  }

  def createVoid(checkpointsTimed: Option[Long] = None, checkpointsReq: Option[Long] = None, checkpointWriteTime: Option[Double] = None, checkpointSyncTime: Option[Double] = None, buffersCheckpoint: Option[Long] = None, buffersClean: Option[Long] = None, maxwrittenClean: Option[Long] = None, buffersBackend: Option[Long] = None, buffersBackendFsync: Option[Long] = None, buffersAlloc: Option[Long] = None, statsReset: Option[Timestamp] = None): ConnectionIO[Unit] = {
    createVoid(Shape(checkpointsTimed, checkpointsReq, checkpointWriteTime, checkpointSyncTime, buffersCheckpoint, buffersClean, maxwrittenClean, buffersBackend, buffersBackendFsync, buffersAlloc, statsReset))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatBgwriter.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_stat_bgwriter (checkpoints_timed, checkpoints_req, checkpoint_write_time, checkpoint_sync_time, buffers_checkpoint, buffers_clean, maxwritten_clean, buffers_backend, buffers_backend_fsync, buffers_alloc, stats_reset) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatBgwriter.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("checkpoints_timed", "checkpoints_req", "checkpoint_write_time", "checkpoint_sync_time", "buffers_checkpoint", "buffers_clean", "maxwritten_clean", "buffers_backend", "buffers_backend_fsync", "buffers_alloc", "stats_reset")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatBgwriter.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatBgwriter.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatBgwriter.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatBgwriter.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_stat_bgwriter
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
      FROM pg_catalog.pg_stat_bgwriter
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
