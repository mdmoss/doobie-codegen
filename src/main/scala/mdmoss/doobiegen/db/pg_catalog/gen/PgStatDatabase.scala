package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatDatabase extends PgStatDatabase {

  case class Row(
    numbackends: Option[Int],
    xactCommit: Option[Long],
    xactRollback: Option[Long],
    blksRead: Option[Long],
    blksHit: Option[Long],
    tupReturned: Option[Long],
    tupFetched: Option[Long],
    tupInserted: Option[Long],
    tupUpdated: Option[Long],
    tupDeleted: Option[Long],
    conflicts: Option[Long],
    tempFiles: Option[Long],
    tempBytes: Option[Long],
    deadlocks: Option[Long],
    blkReadTime: Option[Double],
    blkWriteTime: Option[Double],
    statsReset: Option[Timestamp]
  ) {
    def toShape: Shape = Shape.NoDefaults(numbackends, xactCommit, xactRollback, blksRead, blksHit, tupReturned, tupFetched, tupInserted, tupUpdated, tupDeleted, conflicts, tempFiles, tempBytes, deadlocks, blkReadTime, blkWriteTime, statsReset)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"numbackends, xact_commit, xact_rollback, blks_read, blks_hit, tup_returned, tup_fetched, tup_inserted, tup_updated, tup_deleted, conflicts, temp_files, temp_bytes, deadlocks, blk_read_time, blk_write_time, stats_reset"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".numbackends, " ++ Fragment.const0(a) ++ fr".xact_commit, " ++ Fragment.const0(a) ++ fr".xact_rollback, " ++ Fragment.const0(a) ++ fr".blks_read, " ++ Fragment.const0(a) ++ fr".blks_hit, " ++ Fragment.const0(a) ++ fr".tup_returned, " ++ Fragment.const0(a) ++ fr".tup_fetched, " ++ Fragment.const0(a) ++ fr".tup_inserted, " ++ Fragment.const0(a) ++ fr".tup_updated, " ++ Fragment.const0(a) ++ fr".tup_deleted, " ++ Fragment.const0(a) ++ fr".conflicts, " ++ Fragment.const0(a) ++ fr".temp_files, " ++ Fragment.const0(a) ++ fr".temp_bytes, " ++ Fragment.const0(a) ++ fr".deadlocks, " ++ Fragment.const0(a) ++ fr".blk_read_time, " ++ Fragment.const0(a) ++ fr".blk_write_time, " ++ Fragment.const0(a) ++ fr".stats_reset"
  }

  case class Shape(numbackends: Option[Int] = None, xactCommit: Option[Long] = None, xactRollback: Option[Long] = None, blksRead: Option[Long] = None, blksHit: Option[Long] = None, tupReturned: Option[Long] = None, tupFetched: Option[Long] = None, tupInserted: Option[Long] = None, tupUpdated: Option[Long] = None, tupDeleted: Option[Long] = None, conflicts: Option[Long] = None, tempFiles: Option[Long] = None, tempBytes: Option[Long] = None, deadlocks: Option[Long] = None, blkReadTime: Option[Double] = None, blkWriteTime: Option[Double] = None, statsReset: Option[Timestamp] = None)

  object Shape {
    def NoDefaults(numbackends: Option[Int], xactCommit: Option[Long], xactRollback: Option[Long], blksRead: Option[Long], blksHit: Option[Long], tupReturned: Option[Long], tupFetched: Option[Long], tupInserted: Option[Long], tupUpdated: Option[Long], tupDeleted: Option[Long], conflicts: Option[Long], tempFiles: Option[Long], tempBytes: Option[Long], deadlocks: Option[Long], blkReadTime: Option[Double], blkWriteTime: Option[Double], statsReset: Option[Timestamp]): Shape = Shape(numbackends, xactCommit, xactRollback, blksRead, blksHit, tupReturned, tupFetched, tupInserted, tupUpdated, tupDeleted, conflicts, tempFiles, tempBytes, deadlocks, blkReadTime, blkWriteTime, statsReset)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.DoubleMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.DoubleMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)))))))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.numbackends, (row.xactCommit, (row.xactRollback, (row.blksRead, (row.blksHit, (row.tupReturned, (row.tupFetched, (row.tupInserted, (row.tupUpdated, (row.tupDeleted, (row.conflicts, (row.tempFiles, (row.tempBytes, (row.deadlocks, (row.blkReadTime, (row.blkWriteTime, (row.statsReset)))))))))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.DoubleMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.DoubleMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)))))))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.numbackends, (row.xactCommit, (row.xactRollback, (row.blksRead, (row.blksHit, (row.tupReturned, (row.tupFetched, (row.tupInserted, (row.tupUpdated, (row.tupDeleted, (row.conflicts, (row.tempFiles, (row.tempBytes, (row.deadlocks, (row.blkReadTime, (row.blkWriteTime, (row.statsReset)))))))))))))))))
      )
    }

}
trait PgStatDatabase {
  import PgStatDatabase._

  def create(numbackends: Option[Int] = None, xactCommit: Option[Long] = None, xactRollback: Option[Long] = None, blksRead: Option[Long] = None, blksHit: Option[Long] = None, tupReturned: Option[Long] = None, tupFetched: Option[Long] = None, tupInserted: Option[Long] = None, tupUpdated: Option[Long] = None, tupDeleted: Option[Long] = None, conflicts: Option[Long] = None, tempFiles: Option[Long] = None, tempBytes: Option[Long] = None, deadlocks: Option[Long] = None, blkReadTime: Option[Double] = None, blkWriteTime: Option[Double] = None, statsReset: Option[Timestamp] = None): ConnectionIO[Row] = {
    create(Shape(numbackends, xactCommit, xactRollback, blksRead, blksHit, tupReturned, tupFetched, tupInserted, tupUpdated, tupDeleted, conflicts, tempFiles, tempBytes, deadlocks, blkReadTime, blkWriteTime, statsReset))
  }

  def createVoid(numbackends: Option[Int] = None, xactCommit: Option[Long] = None, xactRollback: Option[Long] = None, blksRead: Option[Long] = None, blksHit: Option[Long] = None, tupReturned: Option[Long] = None, tupFetched: Option[Long] = None, tupInserted: Option[Long] = None, tupUpdated: Option[Long] = None, tupDeleted: Option[Long] = None, conflicts: Option[Long] = None, tempFiles: Option[Long] = None, tempBytes: Option[Long] = None, deadlocks: Option[Long] = None, blkReadTime: Option[Double] = None, blkWriteTime: Option[Double] = None, statsReset: Option[Timestamp] = None): ConnectionIO[Unit] = {
    createVoid(Shape(numbackends, xactCommit, xactRollback, blksRead, blksHit, tupReturned, tupFetched, tupInserted, tupUpdated, tupDeleted, conflicts, tempFiles, tempBytes, deadlocks, blkReadTime, blkWriteTime, statsReset))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatDatabase.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_stat_database (numbackends, xact_commit, xact_rollback, blks_read, blks_hit, tup_returned, tup_fetched, tup_inserted, tup_updated, tup_deleted, conflicts, temp_files, temp_bytes, deadlocks, blk_read_time, blk_write_time, stats_reset) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatDatabase.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("numbackends", "xact_commit", "xact_rollback", "blks_read", "blks_hit", "tup_returned", "tup_fetched", "tup_inserted", "tup_updated", "tup_deleted", "conflicts", "temp_files", "temp_bytes", "deadlocks", "blk_read_time", "blk_write_time", "stats_reset")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatDatabase.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatDatabase.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatDatabase.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatDatabase.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_stat_database
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
      FROM pg_catalog.pg_stat_database
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
