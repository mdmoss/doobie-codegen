package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgAvailableExtensionVersions extends PgAvailableExtensionVersions {

  case class Row(
    version: Option[String],
    installed: Option[Boolean],
    superuser: Option[Boolean],
    relocatable: Option[Boolean],
    comment: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(version, installed, superuser, relocatable, comment)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"version, installed, superuser, relocatable, comment"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".version, " ++ Fragment.const0(a) ++ fr".installed, " ++ Fragment.const0(a) ++ fr".superuser, " ++ Fragment.const0(a) ++ fr".relocatable, " ++ Fragment.const0(a) ++ fr".comment"
  }

  case class Shape(version: Option[String] = None, installed: Option[Boolean] = None, superuser: Option[Boolean] = None, relocatable: Option[Boolean] = None, comment: Option[String] = None)

  object Shape {
    def NoDefaults(version: Option[String], installed: Option[Boolean], superuser: Option[Boolean], relocatable: Option[Boolean], comment: Option[String]): Shape = Shape(version, installed, superuser, relocatable, comment)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.version, (row.installed, (row.superuser, (row.relocatable, (row.comment)))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.version, (row.installed, (row.superuser, (row.relocatable, (row.comment)))))
      )
    }

}
trait PgAvailableExtensionVersions {
  import PgAvailableExtensionVersions._

  def create(version: Option[String] = None, installed: Option[Boolean] = None, superuser: Option[Boolean] = None, relocatable: Option[Boolean] = None, comment: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(version, installed, superuser, relocatable, comment))
  }

  def createVoid(version: Option[String] = None, installed: Option[Boolean] = None, superuser: Option[Boolean] = None, relocatable: Option[Boolean] = None, comment: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(version, installed, superuser, relocatable, comment))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAvailableExtensionVersions.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_available_extension_versions (version, installed, superuser, relocatable, comment) VALUES (?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAvailableExtensionVersions.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("version", "installed", "superuser", "relocatable", "comment")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAvailableExtensionVersions.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAvailableExtensionVersions.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgAvailableExtensionVersions.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgAvailableExtensionVersions.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_available_extension_versions
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
      FROM pg_catalog.pg_available_extension_versions
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
