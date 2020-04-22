package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatXactSysTables extends PgStatXactSysTables {

  case class Row(
    seqScan: Option[Long],
    seqTupRead: Option[Long],
    idxScan: Option[Long],
    idxTupFetch: Option[Long],
    nTupIns: Option[Long],
    nTupUpd: Option[Long],
    nTupDel: Option[Long],
    nTupHotUpd: Option[Long]
  ) {
    def toShape: Shape = Shape.NoDefaults(seqScan, seqTupRead, idxScan, idxTupFetch, nTupIns, nTupUpd, nTupDel, nTupHotUpd)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"seq_scan, seq_tup_read, idx_scan, idx_tup_fetch, n_tup_ins, n_tup_upd, n_tup_del, n_tup_hot_upd"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".seq_scan, " ++ Fragment.const0(a) ++ fr".seq_tup_read, " ++ Fragment.const0(a) ++ fr".idx_scan, " ++ Fragment.const0(a) ++ fr".idx_tup_fetch, " ++ Fragment.const0(a) ++ fr".n_tup_ins, " ++ Fragment.const0(a) ++ fr".n_tup_upd, " ++ Fragment.const0(a) ++ fr".n_tup_del, " ++ Fragment.const0(a) ++ fr".n_tup_hot_upd"
  }

  case class Shape(seqScan: Option[Long] = None, seqTupRead: Option[Long] = None, idxScan: Option[Long] = None, idxTupFetch: Option[Long] = None, nTupIns: Option[Long] = None, nTupUpd: Option[Long] = None, nTupDel: Option[Long] = None, nTupHotUpd: Option[Long] = None)

  object Shape {
    def NoDefaults(seqScan: Option[Long], seqTupRead: Option[Long], idxScan: Option[Long], idxTupFetch: Option[Long], nTupIns: Option[Long], nTupUpd: Option[Long], nTupDel: Option[Long], nTupHotUpd: Option[Long]): Shape = Shape(seqScan, seqTupRead, idxScan, idxTupFetch, nTupIns, nTupUpd, nTupDel, nTupHotUpd)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta))))))))

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
        (row) => (row.seqScan, (row.seqTupRead, (row.idxScan, (row.idxTupFetch, (row.nTupIns, (row.nTupUpd, (row.nTupDel, (row.nTupHotUpd))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta))))))))

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
        (row) => (row.seqScan, (row.seqTupRead, (row.idxScan, (row.idxTupFetch, (row.nTupIns, (row.nTupUpd, (row.nTupDel, (row.nTupHotUpd))))))))
      )
    }

}
trait PgStatXactSysTables {
  import PgStatXactSysTables._

  def create(seqScan: Option[Long] = None, seqTupRead: Option[Long] = None, idxScan: Option[Long] = None, idxTupFetch: Option[Long] = None, nTupIns: Option[Long] = None, nTupUpd: Option[Long] = None, nTupDel: Option[Long] = None, nTupHotUpd: Option[Long] = None): ConnectionIO[Row] = {
    create(Shape(seqScan, seqTupRead, idxScan, idxTupFetch, nTupIns, nTupUpd, nTupDel, nTupHotUpd))
  }

  def createVoid(seqScan: Option[Long] = None, seqTupRead: Option[Long] = None, idxScan: Option[Long] = None, idxTupFetch: Option[Long] = None, nTupIns: Option[Long] = None, nTupUpd: Option[Long] = None, nTupDel: Option[Long] = None, nTupHotUpd: Option[Long] = None): ConnectionIO[Unit] = {
    createVoid(Shape(seqScan, seqTupRead, idxScan, idxTupFetch, nTupIns, nTupUpd, nTupDel, nTupHotUpd))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatXactSysTables.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_stat_xact_sys_tables (seq_scan, seq_tup_read, idx_scan, idx_tup_fetch, n_tup_ins, n_tup_upd, n_tup_del, n_tup_hot_upd) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatXactSysTables.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("seq_scan", "seq_tup_read", "idx_scan", "idx_tup_fetch", "n_tup_ins", "n_tup_upd", "n_tup_del", "n_tup_hot_upd")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatXactSysTables.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatXactSysTables.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatXactSysTables.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatXactSysTables.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_stat_xact_sys_tables
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
      FROM pg_catalog.pg_stat_xact_sys_tables
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
