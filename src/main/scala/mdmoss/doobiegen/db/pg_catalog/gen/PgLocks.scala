package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgLocks extends PgLocks {

  case class Row(
    locktype: Option[String],
    page: Option[Int],
    tuple: Option[Short],
    virtualxid: Option[String],
    objsubid: Option[Short],
    virtualtransaction: Option[String],
    pid: Option[Int],
    mode: Option[String],
    granted: Option[Boolean],
    fastpath: Option[Boolean]
  ) {
    def toShape: Shape = Shape.NoDefaults(locktype, page, tuple, virtualxid, objsubid, virtualtransaction, pid, mode, granted, fastpath)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"locktype, page, tuple, virtualxid, objsubid, virtualtransaction, pid, mode, granted, fastpath"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".locktype, " ++ Fragment.const0(a) ++ fr".page, " ++ Fragment.const0(a) ++ fr".tuple, " ++ Fragment.const0(a) ++ fr".virtualxid, " ++ Fragment.const0(a) ++ fr".objsubid, " ++ Fragment.const0(a) ++ fr".virtualtransaction, " ++ Fragment.const0(a) ++ fr".pid, " ++ Fragment.const0(a) ++ fr".mode, " ++ Fragment.const0(a) ++ fr".granted, " ++ Fragment.const0(a) ++ fr".fastpath"
  }

  case class Shape(locktype: Option[String] = None, page: Option[Int] = None, tuple: Option[Short] = None, virtualxid: Option[String] = None, objsubid: Option[Short] = None, virtualtransaction: Option[String] = None, pid: Option[Int] = None, mode: Option[String] = None, granted: Option[Boolean] = None, fastpath: Option[Boolean] = None)

  object Shape {
    def NoDefaults(locktype: Option[String], page: Option[Int], tuple: Option[Short], virtualxid: Option[String], objsubid: Option[Short], virtualtransaction: Option[String], pid: Option[Int], mode: Option[String], granted: Option[Boolean], fastpath: Option[Boolean]): Shape = Shape(locktype, page, tuple, virtualxid, objsubid, virtualtransaction, pid, mode, granted, fastpath)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta))))))))))

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
    t._2._2._2._2._2._2._2._2._2),
        (row) => (row.locktype, (row.page, (row.tuple, (row.virtualxid, (row.objsubid, (row.virtualtransaction, (row.pid, (row.mode, (row.granted, (row.fastpath))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta))))))))))

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
    t._2._2._2._2._2._2._2._2._2),
        (row) => (row.locktype, (row.page, (row.tuple, (row.virtualxid, (row.objsubid, (row.virtualtransaction, (row.pid, (row.mode, (row.granted, (row.fastpath))))))))))
      )
    }

}
trait PgLocks {
  import PgLocks._

  def create(locktype: Option[String] = None, page: Option[Int] = None, tuple: Option[Short] = None, virtualxid: Option[String] = None, objsubid: Option[Short] = None, virtualtransaction: Option[String] = None, pid: Option[Int] = None, mode: Option[String] = None, granted: Option[Boolean] = None, fastpath: Option[Boolean] = None): ConnectionIO[Row] = {
    create(Shape(locktype, page, tuple, virtualxid, objsubid, virtualtransaction, pid, mode, granted, fastpath))
  }

  def createVoid(locktype: Option[String] = None, page: Option[Int] = None, tuple: Option[Short] = None, virtualxid: Option[String] = None, objsubid: Option[Short] = None, virtualtransaction: Option[String] = None, pid: Option[Int] = None, mode: Option[String] = None, granted: Option[Boolean] = None, fastpath: Option[Boolean] = None): ConnectionIO[Unit] = {
    createVoid(Shape(locktype, page, tuple, virtualxid, objsubid, virtualtransaction, pid, mode, granted, fastpath))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgLocks.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_locks (locktype, page, tuple, virtualxid, objsubid, virtualtransaction, pid, mode, granted, fastpath) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgLocks.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("locktype", "page", "tuple", "virtualxid", "objsubid", "virtualtransaction", "pid", "mode", "granted", "fastpath")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgLocks.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgLocks.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgLocks.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgLocks.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_locks
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
      FROM pg_catalog.pg_locks
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
