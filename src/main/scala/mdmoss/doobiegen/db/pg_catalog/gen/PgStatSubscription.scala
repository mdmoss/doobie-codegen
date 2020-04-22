package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatSubscription extends PgStatSubscription {

  case class Row(
    pid: Option[Int],
    lastMsgSendTime: Option[Timestamp],
    lastMsgReceiptTime: Option[Timestamp],
    latestEndTime: Option[Timestamp]
  ) {
    def toShape: Shape = Shape.NoDefaults(pid, lastMsgSendTime, lastMsgReceiptTime, latestEndTime)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"pid, last_msg_send_time, last_msg_receipt_time, latest_end_time"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".pid, " ++ Fragment.const0(a) ++ fr".last_msg_send_time, " ++ Fragment.const0(a) ++ fr".last_msg_receipt_time, " ++ Fragment.const0(a) ++ fr".latest_end_time"
  }

  case class Shape(pid: Option[Int] = None, lastMsgSendTime: Option[Timestamp] = None, lastMsgReceiptTime: Option[Timestamp] = None, latestEndTime: Option[Timestamp] = None)

  object Shape {
    def NoDefaults(pid: Option[Int], lastMsgSendTime: Option[Timestamp], lastMsgReceiptTime: Option[Timestamp], latestEndTime: Option[Timestamp]): Shape = Shape(pid, lastMsgSendTime, lastMsgReceiptTime, latestEndTime)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.pid, (row.lastMsgSendTime, (row.lastMsgReceiptTime, (row.latestEndTime))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.pid, (row.lastMsgSendTime, (row.lastMsgReceiptTime, (row.latestEndTime))))
      )
    }

}
trait PgStatSubscription {
  import PgStatSubscription._

  def create(pid: Option[Int] = None, lastMsgSendTime: Option[Timestamp] = None, lastMsgReceiptTime: Option[Timestamp] = None, latestEndTime: Option[Timestamp] = None): ConnectionIO[Row] = {
    create(Shape(pid, lastMsgSendTime, lastMsgReceiptTime, latestEndTime))
  }

  def createVoid(pid: Option[Int] = None, lastMsgSendTime: Option[Timestamp] = None, lastMsgReceiptTime: Option[Timestamp] = None, latestEndTime: Option[Timestamp] = None): ConnectionIO[Unit] = {
    createVoid(Shape(pid, lastMsgSendTime, lastMsgReceiptTime, latestEndTime))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatSubscription.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_stat_subscription (pid, last_msg_send_time, last_msg_receipt_time, latest_end_time) VALUES (?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatSubscription.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("pid", "last_msg_send_time", "last_msg_receipt_time", "latest_end_time")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatSubscription.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatSubscription.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatSubscription.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatSubscription.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_stat_subscription
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
      FROM pg_catalog.pg_stat_subscription
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
