package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatioSysTables extends PgStatioSysTables {

  case class Row(
    heapBlksRead: Option[Long],
    heapBlksHit: Option[Long],
    idxBlksRead: Option[Long],
    idxBlksHit: Option[Long],
    toastBlksRead: Option[Long],
    toastBlksHit: Option[Long],
    tidxBlksRead: Option[Long],
    tidxBlksHit: Option[Long]
  ) {
    def toShape: Shape = Shape.NoDefaults(heapBlksRead, heapBlksHit, idxBlksRead, idxBlksHit, toastBlksRead, toastBlksHit, tidxBlksRead, tidxBlksHit)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"heap_blks_read, heap_blks_hit, idx_blks_read, idx_blks_hit, toast_blks_read, toast_blks_hit, tidx_blks_read, tidx_blks_hit"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".heap_blks_read, " ++ Fragment.const0(a) ++ fr".heap_blks_hit, " ++ Fragment.const0(a) ++ fr".idx_blks_read, " ++ Fragment.const0(a) ++ fr".idx_blks_hit, " ++ Fragment.const0(a) ++ fr".toast_blks_read, " ++ Fragment.const0(a) ++ fr".toast_blks_hit, " ++ Fragment.const0(a) ++ fr".tidx_blks_read, " ++ Fragment.const0(a) ++ fr".tidx_blks_hit"
  }

  case class Shape(heapBlksRead: Option[Long] = None, heapBlksHit: Option[Long] = None, idxBlksRead: Option[Long] = None, idxBlksHit: Option[Long] = None, toastBlksRead: Option[Long] = None, toastBlksHit: Option[Long] = None, tidxBlksRead: Option[Long] = None, tidxBlksHit: Option[Long] = None)

  object Shape {
    def NoDefaults(heapBlksRead: Option[Long], heapBlksHit: Option[Long], idxBlksRead: Option[Long], idxBlksHit: Option[Long], toastBlksRead: Option[Long], toastBlksHit: Option[Long], tidxBlksRead: Option[Long], tidxBlksHit: Option[Long]): Shape = Shape(heapBlksRead, heapBlksHit, idxBlksRead, idxBlksHit, toastBlksRead, toastBlksHit, tidxBlksRead, tidxBlksHit)
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
        (row) => (row.heapBlksRead, (row.heapBlksHit, (row.idxBlksRead, (row.idxBlksHit, (row.toastBlksRead, (row.toastBlksHit, (row.tidxBlksRead, (row.tidxBlksHit))))))))
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
        (row) => (row.heapBlksRead, (row.heapBlksHit, (row.idxBlksRead, (row.idxBlksHit, (row.toastBlksRead, (row.toastBlksHit, (row.tidxBlksRead, (row.tidxBlksHit))))))))
      )
    }

}
trait PgStatioSysTables {
  import PgStatioSysTables._

  def create(heapBlksRead: Option[Long] = None, heapBlksHit: Option[Long] = None, idxBlksRead: Option[Long] = None, idxBlksHit: Option[Long] = None, toastBlksRead: Option[Long] = None, toastBlksHit: Option[Long] = None, tidxBlksRead: Option[Long] = None, tidxBlksHit: Option[Long] = None): ConnectionIO[Row] = {
    create(Shape(heapBlksRead, heapBlksHit, idxBlksRead, idxBlksHit, toastBlksRead, toastBlksHit, tidxBlksRead, tidxBlksHit))
  }

  def createVoid(heapBlksRead: Option[Long] = None, heapBlksHit: Option[Long] = None, idxBlksRead: Option[Long] = None, idxBlksHit: Option[Long] = None, toastBlksRead: Option[Long] = None, toastBlksHit: Option[Long] = None, tidxBlksRead: Option[Long] = None, tidxBlksHit: Option[Long] = None): ConnectionIO[Unit] = {
    createVoid(Shape(heapBlksRead, heapBlksHit, idxBlksRead, idxBlksHit, toastBlksRead, toastBlksHit, tidxBlksRead, tidxBlksHit))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatioSysTables.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_statio_sys_tables (heap_blks_read, heap_blks_hit, idx_blks_read, idx_blks_hit, toast_blks_read, toast_blks_hit, tidx_blks_read, tidx_blks_hit) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatioSysTables.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("heap_blks_read", "heap_blks_hit", "idx_blks_read", "idx_blks_hit", "toast_blks_read", "toast_blks_hit", "tidx_blks_read", "tidx_blks_hit")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatioSysTables.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatioSysTables.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatioSysTables.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatioSysTables.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_statio_sys_tables
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
      FROM pg_catalog.pg_statio_sys_tables
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
