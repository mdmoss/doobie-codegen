package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgForeignTables extends PgForeignTables {

  case class Row(
    foreignTableCatalog: Option[String],
    foreignTableSchema: Option[String],
    foreignTableName: Option[String],
    foreignServerCatalog: Option[String],
    foreignServerName: Option[String],
    authorizationIdentifier: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(foreignTableCatalog, foreignTableSchema, foreignTableName, foreignServerCatalog, foreignServerName, authorizationIdentifier)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"foreign_table_catalog, foreign_table_schema, foreign_table_name, foreign_server_catalog, foreign_server_name, authorization_identifier"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".foreign_table_catalog, " ++ Fragment.const0(a) ++ fr".foreign_table_schema, " ++ Fragment.const0(a) ++ fr".foreign_table_name, " ++ Fragment.const0(a) ++ fr".foreign_server_catalog, " ++ Fragment.const0(a) ++ fr".foreign_server_name, " ++ Fragment.const0(a) ++ fr".authorization_identifier"
  }

  case class Shape(foreignTableCatalog: Option[String] = None, foreignTableSchema: Option[String] = None, foreignTableName: Option[String] = None, foreignServerCatalog: Option[String] = None, foreignServerName: Option[String] = None, authorizationIdentifier: Option[String] = None)

  object Shape {
    def NoDefaults(foreignTableCatalog: Option[String], foreignTableSchema: Option[String], foreignTableName: Option[String], foreignServerCatalog: Option[String], foreignServerName: Option[String], authorizationIdentifier: Option[String]): Shape = Shape(foreignTableCatalog, foreignTableSchema, foreignTableName, foreignServerCatalog, foreignServerName, authorizationIdentifier)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2),
        (row) => (row.foreignTableCatalog, (row.foreignTableSchema, (row.foreignTableName, (row.foreignServerCatalog, (row.foreignServerName, (row.authorizationIdentifier))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2),
        (row) => (row.foreignTableCatalog, (row.foreignTableSchema, (row.foreignTableName, (row.foreignServerCatalog, (row.foreignServerName, (row.authorizationIdentifier))))))
      )
    }

}
trait PgForeignTables {
  import PgForeignTables._

  def create(foreignTableCatalog: Option[String] = None, foreignTableSchema: Option[String] = None, foreignTableName: Option[String] = None, foreignServerCatalog: Option[String] = None, foreignServerName: Option[String] = None, authorizationIdentifier: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(foreignTableCatalog, foreignTableSchema, foreignTableName, foreignServerCatalog, foreignServerName, authorizationIdentifier))
  }

  def createVoid(foreignTableCatalog: Option[String] = None, foreignTableSchema: Option[String] = None, foreignTableName: Option[String] = None, foreignServerCatalog: Option[String] = None, foreignServerName: Option[String] = None, authorizationIdentifier: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(foreignTableCatalog, foreignTableSchema, foreignTableName, foreignServerCatalog, foreignServerName, authorizationIdentifier))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.PgForeignTables.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema._pg_foreign_tables (foreign_table_catalog, foreign_table_schema, foreign_table_name, foreign_server_catalog, foreign_server_name, authorization_identifier) VALUES (?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.PgForeignTables.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("foreign_table_catalog", "foreign_table_schema", "foreign_table_name", "foreign_server_catalog", "foreign_server_name", "authorization_identifier")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.PgForeignTables.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.PgForeignTables.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.PgForeignTables.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.PgForeignTables.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema._pg_foreign_tables
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
      FROM information_schema._pg_foreign_tables
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
