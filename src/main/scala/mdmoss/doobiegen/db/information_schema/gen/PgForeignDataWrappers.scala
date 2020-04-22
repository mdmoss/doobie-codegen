package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgForeignDataWrappers extends PgForeignDataWrappers {

  case class Row(
    foreignDataWrapperCatalog: Option[String],
    foreignDataWrapperName: Option[String],
    authorizationIdentifier: Option[String],
    foreignDataWrapperLanguage: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(foreignDataWrapperCatalog, foreignDataWrapperName, authorizationIdentifier, foreignDataWrapperLanguage)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"foreign_data_wrapper_catalog, foreign_data_wrapper_name, authorization_identifier, foreign_data_wrapper_language"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".foreign_data_wrapper_catalog, " ++ Fragment.const0(a) ++ fr".foreign_data_wrapper_name, " ++ Fragment.const0(a) ++ fr".authorization_identifier, " ++ Fragment.const0(a) ++ fr".foreign_data_wrapper_language"
  }

  case class Shape(foreignDataWrapperCatalog: Option[String] = None, foreignDataWrapperName: Option[String] = None, authorizationIdentifier: Option[String] = None, foreignDataWrapperLanguage: Option[String] = None)

  object Shape {
    def NoDefaults(foreignDataWrapperCatalog: Option[String], foreignDataWrapperName: Option[String], authorizationIdentifier: Option[String], foreignDataWrapperLanguage: Option[String]): Shape = Shape(foreignDataWrapperCatalog, foreignDataWrapperName, authorizationIdentifier, foreignDataWrapperLanguage)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.foreignDataWrapperCatalog, (row.foreignDataWrapperName, (row.authorizationIdentifier, (row.foreignDataWrapperLanguage))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.foreignDataWrapperCatalog, (row.foreignDataWrapperName, (row.authorizationIdentifier, (row.foreignDataWrapperLanguage))))
      )
    }

}
trait PgForeignDataWrappers {
  import PgForeignDataWrappers._

  def create(foreignDataWrapperCatalog: Option[String] = None, foreignDataWrapperName: Option[String] = None, authorizationIdentifier: Option[String] = None, foreignDataWrapperLanguage: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(foreignDataWrapperCatalog, foreignDataWrapperName, authorizationIdentifier, foreignDataWrapperLanguage))
  }

  def createVoid(foreignDataWrapperCatalog: Option[String] = None, foreignDataWrapperName: Option[String] = None, authorizationIdentifier: Option[String] = None, foreignDataWrapperLanguage: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(foreignDataWrapperCatalog, foreignDataWrapperName, authorizationIdentifier, foreignDataWrapperLanguage))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.PgForeignDataWrappers.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema._pg_foreign_data_wrappers (foreign_data_wrapper_catalog, foreign_data_wrapper_name, authorization_identifier, foreign_data_wrapper_language) VALUES (?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.PgForeignDataWrappers.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("foreign_data_wrapper_catalog", "foreign_data_wrapper_name", "authorization_identifier", "foreign_data_wrapper_language")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.PgForeignDataWrappers.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.PgForeignDataWrappers.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.PgForeignDataWrappers.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.PgForeignDataWrappers.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema._pg_foreign_data_wrappers
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
      FROM information_schema._pg_foreign_data_wrappers
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
