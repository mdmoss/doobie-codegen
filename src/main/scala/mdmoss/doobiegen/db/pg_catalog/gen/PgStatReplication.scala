package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatReplication extends PgStatReplication {

  case class Row(
    pid: Option[Int],
    applicationName: Option[String],
    clientHostname: Option[String],
    clientPort: Option[Int],
    backendStart: Option[Timestamp],
    state: Option[String],
    writeLag: Option[Int],
    flushLag: Option[Int],
    replayLag: Option[Int],
    syncPriority: Option[Int],
    syncState: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(pid, applicationName, clientHostname, clientPort, backendStart, state, writeLag, flushLag, replayLag, syncPriority, syncState)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"pid, application_name, client_hostname, client_port, backend_start, state, write_lag, flush_lag, replay_lag, sync_priority, sync_state"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".pid, " ++ Fragment.const0(a) ++ fr".application_name, " ++ Fragment.const0(a) ++ fr".client_hostname, " ++ Fragment.const0(a) ++ fr".client_port, " ++ Fragment.const0(a) ++ fr".backend_start, " ++ Fragment.const0(a) ++ fr".state, " ++ Fragment.const0(a) ++ fr".write_lag, " ++ Fragment.const0(a) ++ fr".flush_lag, " ++ Fragment.const0(a) ++ fr".replay_lag, " ++ Fragment.const0(a) ++ fr".sync_priority, " ++ Fragment.const0(a) ++ fr".sync_state"
  }

  case class Shape(pid: Option[Int] = None, applicationName: Option[String] = None, clientHostname: Option[String] = None, clientPort: Option[Int] = None, backendStart: Option[Timestamp] = None, state: Option[String] = None, writeLag: Option[Int] = None, flushLag: Option[Int] = None, replayLag: Option[Int] = None, syncPriority: Option[Int] = None, syncState: Option[String] = None)

  object Shape {
    def NoDefaults(pid: Option[Int], applicationName: Option[String], clientHostname: Option[String], clientPort: Option[Int], backendStart: Option[Timestamp], state: Option[String], writeLag: Option[Int], flushLag: Option[Int], replayLag: Option[Int], syncPriority: Option[Int], syncState: Option[String]): Shape = Shape(pid, applicationName, clientHostname, clientPort, backendStart, state, writeLag, flushLag, replayLag, syncPriority, syncState)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))))))))

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
        (row) => (row.pid, (row.applicationName, (row.clientHostname, (row.clientPort, (row.backendStart, (row.state, (row.writeLag, (row.flushLag, (row.replayLag, (row.syncPriority, (row.syncState)))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))))))))

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
        (row) => (row.pid, (row.applicationName, (row.clientHostname, (row.clientPort, (row.backendStart, (row.state, (row.writeLag, (row.flushLag, (row.replayLag, (row.syncPriority, (row.syncState)))))))))))
      )
    }

}
trait PgStatReplication {
  import PgStatReplication._

  def create(pid: Option[Int] = None, applicationName: Option[String] = None, clientHostname: Option[String] = None, clientPort: Option[Int] = None, backendStart: Option[Timestamp] = None, state: Option[String] = None, writeLag: Option[Int] = None, flushLag: Option[Int] = None, replayLag: Option[Int] = None, syncPriority: Option[Int] = None, syncState: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(pid, applicationName, clientHostname, clientPort, backendStart, state, writeLag, flushLag, replayLag, syncPriority, syncState))
  }

  def createVoid(pid: Option[Int] = None, applicationName: Option[String] = None, clientHostname: Option[String] = None, clientPort: Option[Int] = None, backendStart: Option[Timestamp] = None, state: Option[String] = None, writeLag: Option[Int] = None, flushLag: Option[Int] = None, replayLag: Option[Int] = None, syncPriority: Option[Int] = None, syncState: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(pid, applicationName, clientHostname, clientPort, backendStart, state, writeLag, flushLag, replayLag, syncPriority, syncState))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatReplication.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_stat_replication (pid, application_name, client_hostname, client_port, backend_start, state, write_lag, flush_lag, replay_lag, sync_priority, sync_state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatReplication.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("pid", "application_name", "client_hostname", "client_port", "backend_start", "state", "write_lag", "flush_lag", "replay_lag", "sync_priority", "sync_state")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatReplication.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatReplication.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatReplication.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatReplication.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_stat_replication
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
      FROM pg_catalog.pg_stat_replication
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
