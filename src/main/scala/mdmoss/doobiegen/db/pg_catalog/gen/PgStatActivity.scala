package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatActivity extends PgStatActivity {

  case class Row(
    pid: Option[Int],
    applicationName: Option[String],
    clientHostname: Option[String],
    clientPort: Option[Int],
    backendStart: Option[Timestamp],
    xactStart: Option[Timestamp],
    queryStart: Option[Timestamp],
    stateChange: Option[Timestamp],
    waitEventType: Option[String],
    waitEvent: Option[String],
    state: Option[String],
    query: Option[String],
    backendType: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(pid, applicationName, clientHostname, clientPort, backendStart, xactStart, queryStart, stateChange, waitEventType, waitEvent, state, query, backendType)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"pid, application_name, client_hostname, client_port, backend_start, xact_start, query_start, state_change, wait_event_type, wait_event, state, query, backend_type"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".pid, " ++ Fragment.const0(a) ++ fr".application_name, " ++ Fragment.const0(a) ++ fr".client_hostname, " ++ Fragment.const0(a) ++ fr".client_port, " ++ Fragment.const0(a) ++ fr".backend_start, " ++ Fragment.const0(a) ++ fr".xact_start, " ++ Fragment.const0(a) ++ fr".query_start, " ++ Fragment.const0(a) ++ fr".state_change, " ++ Fragment.const0(a) ++ fr".wait_event_type, " ++ Fragment.const0(a) ++ fr".wait_event, " ++ Fragment.const0(a) ++ fr".state, " ++ Fragment.const0(a) ++ fr".query, " ++ Fragment.const0(a) ++ fr".backend_type"
  }

  case class Shape(pid: Option[Int] = None, applicationName: Option[String] = None, clientHostname: Option[String] = None, clientPort: Option[Int] = None, backendStart: Option[Timestamp] = None, xactStart: Option[Timestamp] = None, queryStart: Option[Timestamp] = None, stateChange: Option[Timestamp] = None, waitEventType: Option[String] = None, waitEvent: Option[String] = None, state: Option[String] = None, query: Option[String] = None, backendType: Option[String] = None)

  object Shape {
    def NoDefaults(pid: Option[Int], applicationName: Option[String], clientHostname: Option[String], clientPort: Option[Int], backendStart: Option[Timestamp], xactStart: Option[Timestamp], queryStart: Option[Timestamp], stateChange: Option[Timestamp], waitEventType: Option[String], waitEvent: Option[String], state: Option[String], query: Option[String], backendType: Option[String]): Shape = Shape(pid, applicationName, clientHostname, clientPort, backendStart, xactStart, queryStart, stateChange, waitEventType, waitEvent, state, query, backendType)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.pid, (row.applicationName, (row.clientHostname, (row.clientPort, (row.backendStart, (row.xactStart, (row.queryStart, (row.stateChange, (row.waitEventType, (row.waitEvent, (row.state, (row.query, (row.backendType)))))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.pid, (row.applicationName, (row.clientHostname, (row.clientPort, (row.backendStart, (row.xactStart, (row.queryStart, (row.stateChange, (row.waitEventType, (row.waitEvent, (row.state, (row.query, (row.backendType)))))))))))))
      )
    }

}
trait PgStatActivity {
  import PgStatActivity._

  def create(pid: Option[Int] = None, applicationName: Option[String] = None, clientHostname: Option[String] = None, clientPort: Option[Int] = None, backendStart: Option[Timestamp] = None, xactStart: Option[Timestamp] = None, queryStart: Option[Timestamp] = None, stateChange: Option[Timestamp] = None, waitEventType: Option[String] = None, waitEvent: Option[String] = None, state: Option[String] = None, query: Option[String] = None, backendType: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(pid, applicationName, clientHostname, clientPort, backendStart, xactStart, queryStart, stateChange, waitEventType, waitEvent, state, query, backendType))
  }

  def createVoid(pid: Option[Int] = None, applicationName: Option[String] = None, clientHostname: Option[String] = None, clientPort: Option[Int] = None, backendStart: Option[Timestamp] = None, xactStart: Option[Timestamp] = None, queryStart: Option[Timestamp] = None, stateChange: Option[Timestamp] = None, waitEventType: Option[String] = None, waitEvent: Option[String] = None, state: Option[String] = None, query: Option[String] = None, backendType: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(pid, applicationName, clientHostname, clientPort, backendStart, xactStart, queryStart, stateChange, waitEventType, waitEvent, state, query, backendType))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatActivity.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_stat_activity (pid, application_name, client_hostname, client_port, backend_start, xact_start, query_start, state_change, wait_event_type, wait_event, state, query, backend_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatActivity.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("pid", "application_name", "client_hostname", "client_port", "backend_start", "xact_start", "query_start", "state_change", "wait_event_type", "wait_event", "state", "query", "backend_type")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatActivity.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatActivity.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatActivity.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatActivity.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_stat_activity
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
      FROM pg_catalog.pg_stat_activity
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
