package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgReplicationSlots extends PgReplicationSlots {

  case class Row(
    slotType: Option[String],
    temporary: Option[Boolean],
    active: Option[Boolean],
    activePid: Option[Int]
  ) {
    def toShape: Shape = Shape.NoDefaults(slotType, temporary, active, activePid)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"slot_type, temporary, active, active_pid"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".slot_type, " ++ Fragment.const0(a) ++ fr".temporary, " ++ Fragment.const0(a) ++ fr".active, " ++ Fragment.const0(a) ++ fr".active_pid"
  }

  case class Shape(slotType: Option[String] = None, temporary: Option[Boolean] = None, active: Option[Boolean] = None, activePid: Option[Int] = None)

  object Shape {
    def NoDefaults(slotType: Option[String], temporary: Option[Boolean], active: Option[Boolean], activePid: Option[Int]): Shape = Shape(slotType, temporary, active, activePid)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.slotType, (row.temporary, (row.active, (row.activePid))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.slotType, (row.temporary, (row.active, (row.activePid))))
      )
    }

}
trait PgReplicationSlots {
  import PgReplicationSlots._

  def create(slotType: Option[String] = None, temporary: Option[Boolean] = None, active: Option[Boolean] = None, activePid: Option[Int] = None): ConnectionIO[Row] = {
    create(Shape(slotType, temporary, active, activePid))
  }

  def createVoid(slotType: Option[String] = None, temporary: Option[Boolean] = None, active: Option[Boolean] = None, activePid: Option[Int] = None): ConnectionIO[Unit] = {
    createVoid(Shape(slotType, temporary, active, activePid))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgReplicationSlots.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_replication_slots (slot_type, temporary, active, active_pid) VALUES (?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgReplicationSlots.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("slot_type", "temporary", "active", "active_pid")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgReplicationSlots.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgReplicationSlots.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgReplicationSlots.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgReplicationSlots.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_replication_slots
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
      FROM pg_catalog.pg_replication_slots
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
