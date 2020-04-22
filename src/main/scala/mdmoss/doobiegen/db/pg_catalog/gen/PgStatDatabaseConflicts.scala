package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatDatabaseConflicts extends PgStatDatabaseConflicts {

  case class Row(
    conflTablespace: Option[Long],
    conflLock: Option[Long],
    conflSnapshot: Option[Long],
    conflBufferpin: Option[Long],
    conflDeadlock: Option[Long]
  ) {
    def toShape: Shape = Shape.NoDefaults(conflTablespace, conflLock, conflSnapshot, conflBufferpin, conflDeadlock)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"confl_tablespace, confl_lock, confl_snapshot, confl_bufferpin, confl_deadlock"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".confl_tablespace, " ++ Fragment.const0(a) ++ fr".confl_lock, " ++ Fragment.const0(a) ++ fr".confl_snapshot, " ++ Fragment.const0(a) ++ fr".confl_bufferpin, " ++ Fragment.const0(a) ++ fr".confl_deadlock"
  }

  case class Shape(conflTablespace: Option[Long] = None, conflLock: Option[Long] = None, conflSnapshot: Option[Long] = None, conflBufferpin: Option[Long] = None, conflDeadlock: Option[Long] = None)

  object Shape {
    def NoDefaults(conflTablespace: Option[Long], conflLock: Option[Long], conflSnapshot: Option[Long], conflBufferpin: Option[Long], conflDeadlock: Option[Long]): Shape = Shape(conflTablespace, conflLock, conflSnapshot, conflBufferpin, conflDeadlock)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.conflTablespace, (row.conflLock, (row.conflSnapshot, (row.conflBufferpin, (row.conflDeadlock)))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.conflTablespace, (row.conflLock, (row.conflSnapshot, (row.conflBufferpin, (row.conflDeadlock)))))
      )
    }

}
trait PgStatDatabaseConflicts {
  import PgStatDatabaseConflicts._

  def create(conflTablespace: Option[Long] = None, conflLock: Option[Long] = None, conflSnapshot: Option[Long] = None, conflBufferpin: Option[Long] = None, conflDeadlock: Option[Long] = None): ConnectionIO[Row] = {
    create(Shape(conflTablespace, conflLock, conflSnapshot, conflBufferpin, conflDeadlock))
  }

  def createVoid(conflTablespace: Option[Long] = None, conflLock: Option[Long] = None, conflSnapshot: Option[Long] = None, conflBufferpin: Option[Long] = None, conflDeadlock: Option[Long] = None): ConnectionIO[Unit] = {
    createVoid(Shape(conflTablespace, conflLock, conflSnapshot, conflBufferpin, conflDeadlock))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatDatabaseConflicts.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_stat_database_conflicts (confl_tablespace, confl_lock, confl_snapshot, confl_bufferpin, confl_deadlock) VALUES (?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatDatabaseConflicts.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("confl_tablespace", "confl_lock", "confl_snapshot", "confl_bufferpin", "confl_deadlock")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatDatabaseConflicts.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatDatabaseConflicts.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatDatabaseConflicts.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatDatabaseConflicts.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_stat_database_conflicts
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
      FROM pg_catalog.pg_stat_database_conflicts
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
