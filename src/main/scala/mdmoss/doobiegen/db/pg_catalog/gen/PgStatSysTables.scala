package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatSysTables extends PgStatSysTables {

  case class Row(
    seqScan: Option[Long],
    seqTupRead: Option[Long],
    idxScan: Option[Long],
    idxTupFetch: Option[Long],
    nTupIns: Option[Long],
    nTupUpd: Option[Long],
    nTupDel: Option[Long],
    nTupHotUpd: Option[Long],
    nLiveTup: Option[Long],
    nDeadTup: Option[Long],
    nModSinceAnalyze: Option[Long],
    lastVacuum: Option[Timestamp],
    lastAutovacuum: Option[Timestamp],
    lastAnalyze: Option[Timestamp],
    lastAutoanalyze: Option[Timestamp],
    vacuumCount: Option[Long],
    autovacuumCount: Option[Long],
    analyzeCount: Option[Long],
    autoanalyzeCount: Option[Long]
  ) {
    def toShape: Shape = Shape.NoDefaults(seqScan, seqTupRead, idxScan, idxTupFetch, nTupIns, nTupUpd, nTupDel, nTupHotUpd, nLiveTup, nDeadTup, nModSinceAnalyze, lastVacuum, lastAutovacuum, lastAnalyze, lastAutoanalyze, vacuumCount, autovacuumCount, analyzeCount, autoanalyzeCount)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"seq_scan, seq_tup_read, idx_scan, idx_tup_fetch, n_tup_ins, n_tup_upd, n_tup_del, n_tup_hot_upd, n_live_tup, n_dead_tup, n_mod_since_analyze, last_vacuum, last_autovacuum, last_analyze, last_autoanalyze, vacuum_count, autovacuum_count, analyze_count, autoanalyze_count"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".seq_scan, " ++ Fragment.const0(a) ++ fr".seq_tup_read, " ++ Fragment.const0(a) ++ fr".idx_scan, " ++ Fragment.const0(a) ++ fr".idx_tup_fetch, " ++ Fragment.const0(a) ++ fr".n_tup_ins, " ++ Fragment.const0(a) ++ fr".n_tup_upd, " ++ Fragment.const0(a) ++ fr".n_tup_del, " ++ Fragment.const0(a) ++ fr".n_tup_hot_upd, " ++ Fragment.const0(a) ++ fr".n_live_tup, " ++ Fragment.const0(a) ++ fr".n_dead_tup, " ++ Fragment.const0(a) ++ fr".n_mod_since_analyze, " ++ Fragment.const0(a) ++ fr".last_vacuum, " ++ Fragment.const0(a) ++ fr".last_autovacuum, " ++ Fragment.const0(a) ++ fr".last_analyze, " ++ Fragment.const0(a) ++ fr".last_autoanalyze, " ++ Fragment.const0(a) ++ fr".vacuum_count, " ++ Fragment.const0(a) ++ fr".autovacuum_count, " ++ Fragment.const0(a) ++ fr".analyze_count, " ++ Fragment.const0(a) ++ fr".autoanalyze_count"
  }

  case class Shape(seqScan: Option[Long] = None, seqTupRead: Option[Long] = None, idxScan: Option[Long] = None, idxTupFetch: Option[Long] = None, nTupIns: Option[Long] = None, nTupUpd: Option[Long] = None, nTupDel: Option[Long] = None, nTupHotUpd: Option[Long] = None, nLiveTup: Option[Long] = None, nDeadTup: Option[Long] = None, nModSinceAnalyze: Option[Long] = None, lastVacuum: Option[Timestamp] = None, lastAutovacuum: Option[Timestamp] = None, lastAnalyze: Option[Timestamp] = None, lastAutoanalyze: Option[Timestamp] = None, vacuumCount: Option[Long] = None, autovacuumCount: Option[Long] = None, analyzeCount: Option[Long] = None, autoanalyzeCount: Option[Long] = None)

  object Shape {
    def NoDefaults(seqScan: Option[Long], seqTupRead: Option[Long], idxScan: Option[Long], idxTupFetch: Option[Long], nTupIns: Option[Long], nTupUpd: Option[Long], nTupDel: Option[Long], nTupHotUpd: Option[Long], nLiveTup: Option[Long], nDeadTup: Option[Long], nModSinceAnalyze: Option[Long], lastVacuum: Option[Timestamp], lastAutovacuum: Option[Timestamp], lastAnalyze: Option[Timestamp], lastAutoanalyze: Option[Timestamp], vacuumCount: Option[Long], autovacuumCount: Option[Long], analyzeCount: Option[Long], autoanalyzeCount: Option[Long]): Shape = Shape(seqScan, seqTupRead, idxScan, idxTupFetch, nTupIns, nTupUpd, nTupDel, nTupHotUpd, nLiveTup, nDeadTup, nModSinceAnalyze, lastVacuum, lastAutovacuum, lastAnalyze, lastAutoanalyze, vacuumCount, autovacuumCount, analyzeCount, autoanalyzeCount)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
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
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)))))))))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.seqScan, (row.seqTupRead, (row.idxScan, (row.idxTupFetch, (row.nTupIns, (row.nTupUpd, (row.nTupDel, (row.nTupHotUpd, (row.nLiveTup, (row.nDeadTup, (row.nModSinceAnalyze, (row.lastVacuum, (row.lastAutovacuum, (row.lastAnalyze, (row.lastAutoanalyze, (row.vacuumCount, (row.autovacuumCount, (row.analyzeCount, (row.autoanalyzeCount)))))))))))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
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
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)))))))))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.seqScan, (row.seqTupRead, (row.idxScan, (row.idxTupFetch, (row.nTupIns, (row.nTupUpd, (row.nTupDel, (row.nTupHotUpd, (row.nLiveTup, (row.nDeadTup, (row.nModSinceAnalyze, (row.lastVacuum, (row.lastAutovacuum, (row.lastAnalyze, (row.lastAutoanalyze, (row.vacuumCount, (row.autovacuumCount, (row.analyzeCount, (row.autoanalyzeCount)))))))))))))))))))
      )
    }

}
trait PgStatSysTables {
  import PgStatSysTables._

  def create(seqScan: Option[Long] = None, seqTupRead: Option[Long] = None, idxScan: Option[Long] = None, idxTupFetch: Option[Long] = None, nTupIns: Option[Long] = None, nTupUpd: Option[Long] = None, nTupDel: Option[Long] = None, nTupHotUpd: Option[Long] = None, nLiveTup: Option[Long] = None, nDeadTup: Option[Long] = None, nModSinceAnalyze: Option[Long] = None, lastVacuum: Option[Timestamp] = None, lastAutovacuum: Option[Timestamp] = None, lastAnalyze: Option[Timestamp] = None, lastAutoanalyze: Option[Timestamp] = None, vacuumCount: Option[Long] = None, autovacuumCount: Option[Long] = None, analyzeCount: Option[Long] = None, autoanalyzeCount: Option[Long] = None): ConnectionIO[Row] = {
    create(Shape(seqScan, seqTupRead, idxScan, idxTupFetch, nTupIns, nTupUpd, nTupDel, nTupHotUpd, nLiveTup, nDeadTup, nModSinceAnalyze, lastVacuum, lastAutovacuum, lastAnalyze, lastAutoanalyze, vacuumCount, autovacuumCount, analyzeCount, autoanalyzeCount))
  }

  def createVoid(seqScan: Option[Long] = None, seqTupRead: Option[Long] = None, idxScan: Option[Long] = None, idxTupFetch: Option[Long] = None, nTupIns: Option[Long] = None, nTupUpd: Option[Long] = None, nTupDel: Option[Long] = None, nTupHotUpd: Option[Long] = None, nLiveTup: Option[Long] = None, nDeadTup: Option[Long] = None, nModSinceAnalyze: Option[Long] = None, lastVacuum: Option[Timestamp] = None, lastAutovacuum: Option[Timestamp] = None, lastAnalyze: Option[Timestamp] = None, lastAutoanalyze: Option[Timestamp] = None, vacuumCount: Option[Long] = None, autovacuumCount: Option[Long] = None, analyzeCount: Option[Long] = None, autoanalyzeCount: Option[Long] = None): ConnectionIO[Unit] = {
    createVoid(Shape(seqScan, seqTupRead, idxScan, idxTupFetch, nTupIns, nTupUpd, nTupDel, nTupHotUpd, nLiveTup, nDeadTup, nModSinceAnalyze, lastVacuum, lastAutovacuum, lastAnalyze, lastAutoanalyze, vacuumCount, autovacuumCount, analyzeCount, autoanalyzeCount))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatSysTables.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_stat_sys_tables (seq_scan, seq_tup_read, idx_scan, idx_tup_fetch, n_tup_ins, n_tup_upd, n_tup_del, n_tup_hot_upd, n_live_tup, n_dead_tup, n_mod_since_analyze, last_vacuum, last_autovacuum, last_analyze, last_autoanalyze, vacuum_count, autovacuum_count, analyze_count, autoanalyze_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatSysTables.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("seq_scan", "seq_tup_read", "idx_scan", "idx_tup_fetch", "n_tup_ins", "n_tup_upd", "n_tup_del", "n_tup_hot_upd", "n_live_tup", "n_dead_tup", "n_mod_since_analyze", "last_vacuum", "last_autovacuum", "last_analyze", "last_autoanalyze", "vacuum_count", "autovacuum_count", "analyze_count", "autoanalyze_count")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatSysTables.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatSysTables.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatSysTables.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatSysTables.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_stat_sys_tables
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
      FROM pg_catalog.pg_stat_sys_tables
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
