package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatSsl extends PgStatSsl {

  case class Row(
    pid: Option[Int],
    ssl: Option[Boolean],
    version: Option[String],
    cipher: Option[String],
    bits: Option[Int],
    compression: Option[Boolean],
    clientdn: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(pid, ssl, version, cipher, bits, compression, clientdn)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"pid, ssl, version, cipher, bits, compression, clientdn"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".pid, " ++ Fragment.const0(a) ++ fr".ssl, " ++ Fragment.const0(a) ++ fr".version, " ++ Fragment.const0(a) ++ fr".cipher, " ++ Fragment.const0(a) ++ fr".bits, " ++ Fragment.const0(a) ++ fr".compression, " ++ Fragment.const0(a) ++ fr".clientdn"
  }

  case class Shape(pid: Option[Int] = None, ssl: Option[Boolean] = None, version: Option[String] = None, cipher: Option[String] = None, bits: Option[Int] = None, compression: Option[Boolean] = None, clientdn: Option[String] = None)

  object Shape {
    def NoDefaults(pid: Option[Int], ssl: Option[Boolean], version: Option[String], cipher: Option[String], bits: Option[Int], compression: Option[Boolean], clientdn: Option[String]): Shape = Shape(pid, ssl, version, cipher, bits, compression, clientdn)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2),
        (row) => (row.pid, (row.ssl, (row.version, (row.cipher, (row.bits, (row.compression, (row.clientdn)))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2),
        (row) => (row.pid, (row.ssl, (row.version, (row.cipher, (row.bits, (row.compression, (row.clientdn)))))))
      )
    }

}
trait PgStatSsl {
  import PgStatSsl._

  def create(pid: Option[Int] = None, ssl: Option[Boolean] = None, version: Option[String] = None, cipher: Option[String] = None, bits: Option[Int] = None, compression: Option[Boolean] = None, clientdn: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(pid, ssl, version, cipher, bits, compression, clientdn))
  }

  def createVoid(pid: Option[Int] = None, ssl: Option[Boolean] = None, version: Option[String] = None, cipher: Option[String] = None, bits: Option[Int] = None, compression: Option[Boolean] = None, clientdn: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(pid, ssl, version, cipher, bits, compression, clientdn))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatSsl.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_stat_ssl (pid, ssl, version, cipher, bits, compression, clientdn) VALUES (?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatSsl.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("pid", "ssl", "version", "cipher", "bits", "compression", "clientdn")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatSsl.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatSsl.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatSsl.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatSsl.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_stat_ssl
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
      FROM pg_catalog.pg_stat_ssl
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
