package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatProgressVacuum extends PgStatProgressVacuum {

  case class Row(
    pid: Option[Int],
    phase: Option[String],
    heapBlksTotal: Option[Long],
    heapBlksScanned: Option[Long],
    heapBlksVacuumed: Option[Long],
    indexVacuumCount: Option[Long],
    maxDeadTuples: Option[Long],
    numDeadTuples: Option[Long]
  ) {
    def toShape: Shape = Shape.NoDefaults(pid, phase, heapBlksTotal, heapBlksScanned, heapBlksVacuumed, indexVacuumCount, maxDeadTuples, numDeadTuples)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"pid, phase, heap_blks_total, heap_blks_scanned, heap_blks_vacuumed, index_vacuum_count, max_dead_tuples, num_dead_tuples"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".pid, " ++ Fragment.const0(a) ++ fr".phase, " ++ Fragment.const0(a) ++ fr".heap_blks_total, " ++ Fragment.const0(a) ++ fr".heap_blks_scanned, " ++ Fragment.const0(a) ++ fr".heap_blks_vacuumed, " ++ Fragment.const0(a) ++ fr".index_vacuum_count, " ++ Fragment.const0(a) ++ fr".max_dead_tuples, " ++ Fragment.const0(a) ++ fr".num_dead_tuples"
  }

  case class Shape(pid: Option[Int] = None, phase: Option[String] = None, heapBlksTotal: Option[Long] = None, heapBlksScanned: Option[Long] = None, heapBlksVacuumed: Option[Long] = None, indexVacuumCount: Option[Long] = None, maxDeadTuples: Option[Long] = None, numDeadTuples: Option[Long] = None)

  object Shape {
    def NoDefaults(pid: Option[Int], phase: Option[String], heapBlksTotal: Option[Long], heapBlksScanned: Option[Long], heapBlksVacuumed: Option[Long], indexVacuumCount: Option[Long], maxDeadTuples: Option[Long], numDeadTuples: Option[Long]): Shape = Shape(pid, phase, heapBlksTotal, heapBlksScanned, heapBlksVacuumed, indexVacuumCount, maxDeadTuples, numDeadTuples)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
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
        (row) => (row.pid, (row.phase, (row.heapBlksTotal, (row.heapBlksScanned, (row.heapBlksVacuumed, (row.indexVacuumCount, (row.maxDeadTuples, (row.numDeadTuples))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
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
        (row) => (row.pid, (row.phase, (row.heapBlksTotal, (row.heapBlksScanned, (row.heapBlksVacuumed, (row.indexVacuumCount, (row.maxDeadTuples, (row.numDeadTuples))))))))
      )
    }

}
trait PgStatProgressVacuum {
  import PgStatProgressVacuum._

  def create(pid: Option[Int] = None, phase: Option[String] = None, heapBlksTotal: Option[Long] = None, heapBlksScanned: Option[Long] = None, heapBlksVacuumed: Option[Long] = None, indexVacuumCount: Option[Long] = None, maxDeadTuples: Option[Long] = None, numDeadTuples: Option[Long] = None): ConnectionIO[Row] = {
    create(Shape(pid, phase, heapBlksTotal, heapBlksScanned, heapBlksVacuumed, indexVacuumCount, maxDeadTuples, numDeadTuples))
  }

  def createVoid(pid: Option[Int] = None, phase: Option[String] = None, heapBlksTotal: Option[Long] = None, heapBlksScanned: Option[Long] = None, heapBlksVacuumed: Option[Long] = None, indexVacuumCount: Option[Long] = None, maxDeadTuples: Option[Long] = None, numDeadTuples: Option[Long] = None): ConnectionIO[Unit] = {
    createVoid(Shape(pid, phase, heapBlksTotal, heapBlksScanned, heapBlksVacuumed, indexVacuumCount, maxDeadTuples, numDeadTuples))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatProgressVacuum.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_stat_progress_vacuum (pid, phase, heap_blks_total, heap_blks_scanned, heap_blks_vacuumed, index_vacuum_count, max_dead_tuples, num_dead_tuples) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatProgressVacuum.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("pid", "phase", "heap_blks_total", "heap_blks_scanned", "heap_blks_vacuumed", "index_vacuum_count", "max_dead_tuples", "num_dead_tuples")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatProgressVacuum.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatProgressVacuum.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatProgressVacuum.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatProgressVacuum.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_stat_progress_vacuum
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
      FROM pg_catalog.pg_stat_progress_vacuum
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
