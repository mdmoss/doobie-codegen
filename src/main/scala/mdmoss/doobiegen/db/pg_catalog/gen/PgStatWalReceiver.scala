package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatWalReceiver extends PgStatWalReceiver {

  case class Row(
    pid: Option[Int],
    status: Option[String],
    receiveStartTli: Option[Int],
    receivedTli: Option[Int],
    lastMsgSendTime: Option[Timestamp],
    lastMsgReceiptTime: Option[Timestamp],
    latestEndTime: Option[Timestamp],
    slotName: Option[String],
    senderHost: Option[String],
    senderPort: Option[Int],
    conninfo: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(pid, status, receiveStartTli, receivedTli, lastMsgSendTime, lastMsgReceiptTime, latestEndTime, slotName, senderHost, senderPort, conninfo)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"pid, status, receive_start_tli, received_tli, last_msg_send_time, last_msg_receipt_time, latest_end_time, slot_name, sender_host, sender_port, conninfo"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".pid, " ++ Fragment.const0(a) ++ fr".status, " ++ Fragment.const0(a) ++ fr".receive_start_tli, " ++ Fragment.const0(a) ++ fr".received_tli, " ++ Fragment.const0(a) ++ fr".last_msg_send_time, " ++ Fragment.const0(a) ++ fr".last_msg_receipt_time, " ++ Fragment.const0(a) ++ fr".latest_end_time, " ++ Fragment.const0(a) ++ fr".slot_name, " ++ Fragment.const0(a) ++ fr".sender_host, " ++ Fragment.const0(a) ++ fr".sender_port, " ++ Fragment.const0(a) ++ fr".conninfo"
  }

  case class Shape(pid: Option[Int] = None, status: Option[String] = None, receiveStartTli: Option[Int] = None, receivedTli: Option[Int] = None, lastMsgSendTime: Option[Timestamp] = None, lastMsgReceiptTime: Option[Timestamp] = None, latestEndTime: Option[Timestamp] = None, slotName: Option[String] = None, senderHost: Option[String] = None, senderPort: Option[Int] = None, conninfo: Option[String] = None)

  object Shape {
    def NoDefaults(pid: Option[Int], status: Option[String], receiveStartTli: Option[Int], receivedTli: Option[Int], lastMsgSendTime: Option[Timestamp], lastMsgReceiptTime: Option[Timestamp], latestEndTime: Option[Timestamp], slotName: Option[String], senderHost: Option[String], senderPort: Option[Int], conninfo: Option[String]): Shape = Shape(pid, status, receiveStartTli, receivedTli, lastMsgSendTime, lastMsgReceiptTime, latestEndTime, slotName, senderHost, senderPort, conninfo)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
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
        (row) => (row.pid, (row.status, (row.receiveStartTli, (row.receivedTli, (row.lastMsgSendTime, (row.lastMsgReceiptTime, (row.latestEndTime, (row.slotName, (row.senderHost, (row.senderPort, (row.conninfo)))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
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
        (row) => (row.pid, (row.status, (row.receiveStartTli, (row.receivedTli, (row.lastMsgSendTime, (row.lastMsgReceiptTime, (row.latestEndTime, (row.slotName, (row.senderHost, (row.senderPort, (row.conninfo)))))))))))
      )
    }

}
trait PgStatWalReceiver {
  import PgStatWalReceiver._

  def create(pid: Option[Int] = None, status: Option[String] = None, receiveStartTli: Option[Int] = None, receivedTli: Option[Int] = None, lastMsgSendTime: Option[Timestamp] = None, lastMsgReceiptTime: Option[Timestamp] = None, latestEndTime: Option[Timestamp] = None, slotName: Option[String] = None, senderHost: Option[String] = None, senderPort: Option[Int] = None, conninfo: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(pid, status, receiveStartTli, receivedTli, lastMsgSendTime, lastMsgReceiptTime, latestEndTime, slotName, senderHost, senderPort, conninfo))
  }

  def createVoid(pid: Option[Int] = None, status: Option[String] = None, receiveStartTli: Option[Int] = None, receivedTli: Option[Int] = None, lastMsgSendTime: Option[Timestamp] = None, lastMsgReceiptTime: Option[Timestamp] = None, latestEndTime: Option[Timestamp] = None, slotName: Option[String] = None, senderHost: Option[String] = None, senderPort: Option[Int] = None, conninfo: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(pid, status, receiveStartTli, receivedTli, lastMsgSendTime, lastMsgReceiptTime, latestEndTime, slotName, senderHost, senderPort, conninfo))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatWalReceiver.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_stat_wal_receiver (pid, status, receive_start_tli, received_tli, last_msg_send_time, last_msg_receipt_time, latest_end_time, slot_name, sender_host, sender_port, conninfo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatWalReceiver.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("pid", "status", "receive_start_tli", "received_tli", "last_msg_send_time", "last_msg_receipt_time", "latest_end_time", "slot_name", "sender_host", "sender_port", "conninfo")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatWalReceiver.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatWalReceiver.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatWalReceiver.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatWalReceiver.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_stat_wal_receiver
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
      FROM pg_catalog.pg_stat_wal_receiver
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
