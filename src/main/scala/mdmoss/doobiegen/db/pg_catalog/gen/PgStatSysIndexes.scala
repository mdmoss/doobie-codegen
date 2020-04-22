package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatSysIndexes extends PgStatSysIndexes {

  case class Row(
    idxScan: Option[Long],
    idxTupRead: Option[Long],
    idxTupFetch: Option[Long]
  ) {
    def toShape: Shape = Shape.NoDefaults(idxScan, idxTupRead, idxTupFetch)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"idx_scan, idx_tup_read, idx_tup_fetch"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".idx_scan, " ++ Fragment.const0(a) ++ fr".idx_tup_read, " ++ Fragment.const0(a) ++ fr".idx_tup_fetch"
  }

  case class Shape(idxScan: Option[Long] = None, idxTupRead: Option[Long] = None, idxTupFetch: Option[Long] = None)

  object Shape {
    def NoDefaults(idxScan: Option[Long], idxTupRead: Option[Long], idxTupFetch: Option[Long]): Shape = Shape(idxScan, idxTupRead, idxTupFetch)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.idxScan, (row.idxTupRead, (row.idxTupFetch)))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.idxScan, (row.idxTupRead, (row.idxTupFetch)))
      )
    }

}
trait PgStatSysIndexes {
  import PgStatSysIndexes._

  def create(idxScan: Option[Long] = None, idxTupRead: Option[Long] = None, idxTupFetch: Option[Long] = None): ConnectionIO[Row] = {
    create(Shape(idxScan, idxTupRead, idxTupFetch))
  }

  def createVoid(idxScan: Option[Long] = None, idxTupRead: Option[Long] = None, idxTupFetch: Option[Long] = None): ConnectionIO[Unit] = {
    createVoid(Shape(idxScan, idxTupRead, idxTupFetch))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatSysIndexes.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_stat_sys_indexes (idx_scan, idx_tup_read, idx_tup_fetch) VALUES (?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatSysIndexes.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("idx_scan", "idx_tup_read", "idx_tup_fetch")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatSysIndexes.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatSysIndexes.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatSysIndexes.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatSysIndexes.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_stat_sys_indexes
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
      FROM pg_catalog.pg_stat_sys_indexes
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
