package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgSeclabels extends PgSeclabels {

  case class Row(
    objsubid: Option[Int],
    objtype: Option[String],
    objname: Option[String],
    provider: Option[String],
    label: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(objsubid, objtype, objname, provider, label)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"objsubid, objtype, objname, provider, label"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".objsubid, " ++ Fragment.const0(a) ++ fr".objtype, " ++ Fragment.const0(a) ++ fr".objname, " ++ Fragment.const0(a) ++ fr".provider, " ++ Fragment.const0(a) ++ fr".label"
  }

  case class Shape(objsubid: Option[Int] = None, objtype: Option[String] = None, objname: Option[String] = None, provider: Option[String] = None, label: Option[String] = None)

  object Shape {
    def NoDefaults(objsubid: Option[Int], objtype: Option[String], objname: Option[String], provider: Option[String], label: Option[String]): Shape = Shape(objsubid, objtype, objname, provider, label)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.objsubid, (row.objtype, (row.objname, (row.provider, (row.label)))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.objsubid, (row.objtype, (row.objname, (row.provider, (row.label)))))
      )
    }

}
trait PgSeclabels {
  import PgSeclabels._

  def create(objsubid: Option[Int] = None, objtype: Option[String] = None, objname: Option[String] = None, provider: Option[String] = None, label: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(objsubid, objtype, objname, provider, label))
  }

  def createVoid(objsubid: Option[Int] = None, objtype: Option[String] = None, objname: Option[String] = None, provider: Option[String] = None, label: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(objsubid, objtype, objname, provider, label))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSeclabels.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_seclabels (objsubid, objtype, objname, provider, label) VALUES (?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSeclabels.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("objsubid", "objtype", "objname", "provider", "label")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSeclabels.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSeclabels.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgSeclabels.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgSeclabels.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_seclabels
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
      FROM pg_catalog.pg_seclabels
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
