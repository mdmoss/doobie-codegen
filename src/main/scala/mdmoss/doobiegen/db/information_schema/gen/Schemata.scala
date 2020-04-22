package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object Schemata extends Schemata {

  case class Row(
    catalogName: Option[String],
    schemaName: Option[String],
    schemaOwner: Option[String],
    defaultCharacterSetCatalog: Option[String],
    defaultCharacterSetSchema: Option[String],
    defaultCharacterSetName: Option[String],
    sqlPath: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(catalogName, schemaName, schemaOwner, defaultCharacterSetCatalog, defaultCharacterSetSchema, defaultCharacterSetName, sqlPath)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"catalog_name, schema_name, schema_owner, default_character_set_catalog, default_character_set_schema, default_character_set_name, sql_path"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".catalog_name, " ++ Fragment.const0(a) ++ fr".schema_name, " ++ Fragment.const0(a) ++ fr".schema_owner, " ++ Fragment.const0(a) ++ fr".default_character_set_catalog, " ++ Fragment.const0(a) ++ fr".default_character_set_schema, " ++ Fragment.const0(a) ++ fr".default_character_set_name, " ++ Fragment.const0(a) ++ fr".sql_path"
  }

  case class Shape(catalogName: Option[String] = None, schemaName: Option[String] = None, schemaOwner: Option[String] = None, defaultCharacterSetCatalog: Option[String] = None, defaultCharacterSetSchema: Option[String] = None, defaultCharacterSetName: Option[String] = None, sqlPath: Option[String] = None)

  object Shape {
    def NoDefaults(catalogName: Option[String], schemaName: Option[String], schemaOwner: Option[String], defaultCharacterSetCatalog: Option[String], defaultCharacterSetSchema: Option[String], defaultCharacterSetName: Option[String], sqlPath: Option[String]): Shape = Shape(catalogName, schemaName, schemaOwner, defaultCharacterSetCatalog, defaultCharacterSetSchema, defaultCharacterSetName, sqlPath)
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
        (row) => (row.catalogName, (row.schemaName, (row.schemaOwner, (row.defaultCharacterSetCatalog, (row.defaultCharacterSetSchema, (row.defaultCharacterSetName, (row.sqlPath)))))))
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
        (row) => (row.catalogName, (row.schemaName, (row.schemaOwner, (row.defaultCharacterSetCatalog, (row.defaultCharacterSetSchema, (row.defaultCharacterSetName, (row.sqlPath)))))))
      )
    }

}
trait Schemata {
  import Schemata._

  def create(catalogName: Option[String] = None, schemaName: Option[String] = None, schemaOwner: Option[String] = None, defaultCharacterSetCatalog: Option[String] = None, defaultCharacterSetSchema: Option[String] = None, defaultCharacterSetName: Option[String] = None, sqlPath: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(catalogName, schemaName, schemaOwner, defaultCharacterSetCatalog, defaultCharacterSetSchema, defaultCharacterSetName, sqlPath))
  }

  def createVoid(catalogName: Option[String] = None, schemaName: Option[String] = None, schemaOwner: Option[String] = None, defaultCharacterSetCatalog: Option[String] = None, defaultCharacterSetSchema: Option[String] = None, defaultCharacterSetName: Option[String] = None, sqlPath: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(catalogName, schemaName, schemaOwner, defaultCharacterSetCatalog, defaultCharacterSetSchema, defaultCharacterSetName, sqlPath))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.Schemata.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.schemata (catalog_name, schema_name, schema_owner, default_character_set_catalog, default_character_set_schema, default_character_set_name, sql_path) VALUES (?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.Schemata.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("catalog_name", "schema_name", "schema_owner", "default_character_set_catalog", "default_character_set_schema", "default_character_set_name", "sql_path")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.Schemata.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.Schemata.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.Schemata.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.Schemata.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.schemata
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
      FROM information_schema.schemata
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
