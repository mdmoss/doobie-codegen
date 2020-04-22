package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgAvailableExtensions extends PgAvailableExtensions {

  case class Row(
    defaultVersion: Option[String],
    installedVersion: Option[String],
    comment: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(defaultVersion, installedVersion, comment)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"default_version, installed_version, comment"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".default_version, " ++ Fragment.const0(a) ++ fr".installed_version, " ++ Fragment.const0(a) ++ fr".comment"
  }

  case class Shape(defaultVersion: Option[String] = None, installedVersion: Option[String] = None, comment: Option[String] = None)

  object Shape {
    def NoDefaults(defaultVersion: Option[String], installedVersion: Option[String], comment: Option[String]): Shape = Shape(defaultVersion, installedVersion, comment)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.defaultVersion, (row.installedVersion, (row.comment)))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.defaultVersion, (row.installedVersion, (row.comment)))
      )
    }

}
trait PgAvailableExtensions {
  import PgAvailableExtensions._

  def create(defaultVersion: Option[String] = None, installedVersion: Option[String] = None, comment: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(defaultVersion, installedVersion, comment))
  }

  def createVoid(defaultVersion: Option[String] = None, installedVersion: Option[String] = None, comment: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(defaultVersion, installedVersion, comment))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAvailableExtensions.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_available_extensions (default_version, installed_version, comment) VALUES (?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAvailableExtensions.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("default_version", "installed_version", "comment")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAvailableExtensions.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAvailableExtensions.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgAvailableExtensions.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgAvailableExtensions.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_available_extensions
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
      FROM pg_catalog.pg_available_extensions
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
