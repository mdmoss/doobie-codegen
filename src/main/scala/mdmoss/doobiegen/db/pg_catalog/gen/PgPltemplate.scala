package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgPltemplate extends PgPltemplate {

  case class Row(
    tmpltrusted: Boolean,
    tmpldbacreate: Boolean,
    tmplhandler: String,
    tmplinline: Option[String],
    tmplvalidator: Option[String],
    tmpllibrary: String
  ) {
    def toShape: Shape = Shape.NoDefaults(tmpltrusted, tmpldbacreate, tmplhandler, tmplinline, tmplvalidator, tmpllibrary)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"tmpltrusted, tmpldbacreate, tmplhandler, tmplinline, tmplvalidator, tmpllibrary"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".tmpltrusted, " ++ Fragment.const0(a) ++ fr".tmpldbacreate, " ++ Fragment.const0(a) ++ fr".tmplhandler, " ++ Fragment.const0(a) ++ fr".tmplinline, " ++ Fragment.const0(a) ++ fr".tmplvalidator, " ++ Fragment.const0(a) ++ fr".tmpllibrary"
  }

  case class Shape(tmpltrusted: Boolean, tmpldbacreate: Boolean, tmplhandler: String, tmplinline: Option[String] = None, tmplvalidator: Option[String] = None, tmpllibrary: String)

  object Shape {
    def NoDefaults(tmpltrusted: Boolean, tmpldbacreate: Boolean, tmplhandler: String, tmplinline: Option[String], tmplvalidator: Option[String], tmpllibrary: String): Shape = Shape(tmpltrusted, tmpldbacreate, tmplhandler, tmplinline, tmplvalidator, tmpllibrary)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2),
        (row) => (row.tmpltrusted, (row.tmpldbacreate, (row.tmplhandler, (row.tmplinline, (row.tmplvalidator, (row.tmpllibrary))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2),
        (row) => (row.tmpltrusted, (row.tmpldbacreate, (row.tmplhandler, (row.tmplinline, (row.tmplvalidator, (row.tmpllibrary))))))
      )
    }

}
trait PgPltemplate {
  import PgPltemplate._

  def create(tmpltrusted: Boolean, tmpldbacreate: Boolean, tmplhandler: String, tmplinline: Option[String] = None, tmplvalidator: Option[String] = None, tmpllibrary: String): ConnectionIO[Row] = {
    create(Shape(tmpltrusted, tmpldbacreate, tmplhandler, tmplinline, tmplvalidator, tmpllibrary))
  }

  def createVoid(tmpltrusted: Boolean, tmpldbacreate: Boolean, tmplhandler: String, tmplinline: Option[String] = None, tmplvalidator: Option[String] = None, tmpllibrary: String): ConnectionIO[Unit] = {
    createVoid(Shape(tmpltrusted, tmpldbacreate, tmplhandler, tmplinline, tmplvalidator, tmpllibrary))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPltemplate.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_pltemplate (tmpltrusted, tmpldbacreate, tmplhandler, tmplinline, tmplvalidator, tmpllibrary) VALUES (?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPltemplate.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("tmpltrusted", "tmpldbacreate", "tmplhandler", "tmplinline", "tmplvalidator", "tmpllibrary")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPltemplate.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPltemplate.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgPltemplate.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgPltemplate.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_pltemplate
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
      FROM pg_catalog.pg_pltemplate
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
