package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgUserMappings extends PgUserMappings {

  case class Row(
    authorizationIdentifier: Option[String],
    foreignServerCatalog: Option[String],
    foreignServerName: Option[String],
    srvowner: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(authorizationIdentifier, foreignServerCatalog, foreignServerName, srvowner)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"authorization_identifier, foreign_server_catalog, foreign_server_name, srvowner"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".authorization_identifier, " ++ Fragment.const0(a) ++ fr".foreign_server_catalog, " ++ Fragment.const0(a) ++ fr".foreign_server_name, " ++ Fragment.const0(a) ++ fr".srvowner"
  }

  case class Shape(authorizationIdentifier: Option[String] = None, foreignServerCatalog: Option[String] = None, foreignServerName: Option[String] = None, srvowner: Option[String] = None)

  object Shape {
    def NoDefaults(authorizationIdentifier: Option[String], foreignServerCatalog: Option[String], foreignServerName: Option[String], srvowner: Option[String]): Shape = Shape(authorizationIdentifier, foreignServerCatalog, foreignServerName, srvowner)
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
        (row) => (row.authorizationIdentifier, (row.foreignServerCatalog, (row.foreignServerName, (row.srvowner))))
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
        (row) => (row.authorizationIdentifier, (row.foreignServerCatalog, (row.foreignServerName, (row.srvowner))))
      )
    }

}
trait PgUserMappings {
  import PgUserMappings._

  def create(authorizationIdentifier: Option[String] = None, foreignServerCatalog: Option[String] = None, foreignServerName: Option[String] = None, srvowner: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(authorizationIdentifier, foreignServerCatalog, foreignServerName, srvowner))
  }

  def createVoid(authorizationIdentifier: Option[String] = None, foreignServerCatalog: Option[String] = None, foreignServerName: Option[String] = None, srvowner: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(authorizationIdentifier, foreignServerCatalog, foreignServerName, srvowner))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.PgUserMappings.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema._pg_user_mappings (authorization_identifier, foreign_server_catalog, foreign_server_name, srvowner) VALUES (?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.PgUserMappings.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("authorization_identifier", "foreign_server_catalog", "foreign_server_name", "srvowner")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.PgUserMappings.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.PgUserMappings.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.PgUserMappings.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.PgUserMappings.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema._pg_user_mappings
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
      FROM information_schema._pg_user_mappings
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
