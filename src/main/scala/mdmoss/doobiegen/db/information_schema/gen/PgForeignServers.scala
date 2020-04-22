package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgForeignServers extends PgForeignServers {

  case class Row(
    foreignServerCatalog: Option[String],
    foreignServerName: Option[String],
    foreignDataWrapperCatalog: Option[String],
    foreignDataWrapperName: Option[String],
    foreignServerType: Option[String],
    foreignServerVersion: Option[String],
    authorizationIdentifier: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(foreignServerCatalog, foreignServerName, foreignDataWrapperCatalog, foreignDataWrapperName, foreignServerType, foreignServerVersion, authorizationIdentifier)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"foreign_server_catalog, foreign_server_name, foreign_data_wrapper_catalog, foreign_data_wrapper_name, foreign_server_type, foreign_server_version, authorization_identifier"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".foreign_server_catalog, " ++ Fragment.const0(a) ++ fr".foreign_server_name, " ++ Fragment.const0(a) ++ fr".foreign_data_wrapper_catalog, " ++ Fragment.const0(a) ++ fr".foreign_data_wrapper_name, " ++ Fragment.const0(a) ++ fr".foreign_server_type, " ++ Fragment.const0(a) ++ fr".foreign_server_version, " ++ Fragment.const0(a) ++ fr".authorization_identifier"
  }

  case class Shape(foreignServerCatalog: Option[String] = None, foreignServerName: Option[String] = None, foreignDataWrapperCatalog: Option[String] = None, foreignDataWrapperName: Option[String] = None, foreignServerType: Option[String] = None, foreignServerVersion: Option[String] = None, authorizationIdentifier: Option[String] = None)

  object Shape {
    def NoDefaults(foreignServerCatalog: Option[String], foreignServerName: Option[String], foreignDataWrapperCatalog: Option[String], foreignDataWrapperName: Option[String], foreignServerType: Option[String], foreignServerVersion: Option[String], authorizationIdentifier: Option[String]): Shape = Shape(foreignServerCatalog, foreignServerName, foreignDataWrapperCatalog, foreignDataWrapperName, foreignServerType, foreignServerVersion, authorizationIdentifier)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
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
        (row) => (row.foreignServerCatalog, (row.foreignServerName, (row.foreignDataWrapperCatalog, (row.foreignDataWrapperName, (row.foreignServerType, (row.foreignServerVersion, (row.authorizationIdentifier)))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
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
        (row) => (row.foreignServerCatalog, (row.foreignServerName, (row.foreignDataWrapperCatalog, (row.foreignDataWrapperName, (row.foreignServerType, (row.foreignServerVersion, (row.authorizationIdentifier)))))))
      )
    }

}
trait PgForeignServers {
  import PgForeignServers._

  def create(foreignServerCatalog: Option[String] = None, foreignServerName: Option[String] = None, foreignDataWrapperCatalog: Option[String] = None, foreignDataWrapperName: Option[String] = None, foreignServerType: Option[String] = None, foreignServerVersion: Option[String] = None, authorizationIdentifier: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(foreignServerCatalog, foreignServerName, foreignDataWrapperCatalog, foreignDataWrapperName, foreignServerType, foreignServerVersion, authorizationIdentifier))
  }

  def createVoid(foreignServerCatalog: Option[String] = None, foreignServerName: Option[String] = None, foreignDataWrapperCatalog: Option[String] = None, foreignDataWrapperName: Option[String] = None, foreignServerType: Option[String] = None, foreignServerVersion: Option[String] = None, authorizationIdentifier: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(foreignServerCatalog, foreignServerName, foreignDataWrapperCatalog, foreignDataWrapperName, foreignServerType, foreignServerVersion, authorizationIdentifier))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.PgForeignServers.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema._pg_foreign_servers (foreign_server_catalog, foreign_server_name, foreign_data_wrapper_catalog, foreign_data_wrapper_name, foreign_server_type, foreign_server_version, authorization_identifier) VALUES (?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.PgForeignServers.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("foreign_server_catalog", "foreign_server_name", "foreign_data_wrapper_catalog", "foreign_data_wrapper_name", "foreign_server_type", "foreign_server_version", "authorization_identifier")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.PgForeignServers.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.PgForeignServers.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.PgForeignServers.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.PgForeignServers.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema._pg_foreign_servers
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
      FROM information_schema._pg_foreign_servers
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
