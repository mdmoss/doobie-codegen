package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object Views extends Views {

  case class Row(
    tableCatalog: Option[String],
    tableSchema: Option[String],
    tableName: Option[String],
    viewDefinition: Option[String],
    checkOption: Option[String],
    isUpdatable: Option[String],
    isInsertableInto: Option[String],
    isTriggerUpdatable: Option[String],
    isTriggerDeletable: Option[String],
    isTriggerInsertableInto: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(tableCatalog, tableSchema, tableName, viewDefinition, checkOption, isUpdatable, isInsertableInto, isTriggerUpdatable, isTriggerDeletable, isTriggerInsertableInto)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"table_catalog, table_schema, table_name, view_definition, check_option, is_updatable, is_insertable_into, is_trigger_updatable, is_trigger_deletable, is_trigger_insertable_into"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".table_catalog, " ++ Fragment.const0(a) ++ fr".table_schema, " ++ Fragment.const0(a) ++ fr".table_name, " ++ Fragment.const0(a) ++ fr".view_definition, " ++ Fragment.const0(a) ++ fr".check_option, " ++ Fragment.const0(a) ++ fr".is_updatable, " ++ Fragment.const0(a) ++ fr".is_insertable_into, " ++ Fragment.const0(a) ++ fr".is_trigger_updatable, " ++ Fragment.const0(a) ++ fr".is_trigger_deletable, " ++ Fragment.const0(a) ++ fr".is_trigger_insertable_into"
  }

  case class Shape(tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, viewDefinition: Option[String] = None, checkOption: Option[String] = None, isUpdatable: Option[String] = None, isInsertableInto: Option[String] = None, isTriggerUpdatable: Option[String] = None, isTriggerDeletable: Option[String] = None, isTriggerInsertableInto: Option[String] = None)

  object Shape {
    def NoDefaults(tableCatalog: Option[String], tableSchema: Option[String], tableName: Option[String], viewDefinition: Option[String], checkOption: Option[String], isUpdatable: Option[String], isInsertableInto: Option[String], isTriggerUpdatable: Option[String], isTriggerDeletable: Option[String], isTriggerInsertableInto: Option[String]): Shape = Shape(tableCatalog, tableSchema, tableName, viewDefinition, checkOption, isUpdatable, isInsertableInto, isTriggerUpdatable, isTriggerDeletable, isTriggerInsertableInto)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))))))

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
        (row) => (row.tableCatalog, (row.tableSchema, (row.tableName, (row.viewDefinition, (row.checkOption, (row.isUpdatable, (row.isInsertableInto, (row.isTriggerUpdatable, (row.isTriggerDeletable, (row.isTriggerInsertableInto))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))))))

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
        (row) => (row.tableCatalog, (row.tableSchema, (row.tableName, (row.viewDefinition, (row.checkOption, (row.isUpdatable, (row.isInsertableInto, (row.isTriggerUpdatable, (row.isTriggerDeletable, (row.isTriggerInsertableInto))))))))))
      )
    }

}
trait Views {
  import Views._

  def create(tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, viewDefinition: Option[String] = None, checkOption: Option[String] = None, isUpdatable: Option[String] = None, isInsertableInto: Option[String] = None, isTriggerUpdatable: Option[String] = None, isTriggerDeletable: Option[String] = None, isTriggerInsertableInto: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(tableCatalog, tableSchema, tableName, viewDefinition, checkOption, isUpdatable, isInsertableInto, isTriggerUpdatable, isTriggerDeletable, isTriggerInsertableInto))
  }

  def createVoid(tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, viewDefinition: Option[String] = None, checkOption: Option[String] = None, isUpdatable: Option[String] = None, isInsertableInto: Option[String] = None, isTriggerUpdatable: Option[String] = None, isTriggerDeletable: Option[String] = None, isTriggerInsertableInto: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(tableCatalog, tableSchema, tableName, viewDefinition, checkOption, isUpdatable, isInsertableInto, isTriggerUpdatable, isTriggerDeletable, isTriggerInsertableInto))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.Views.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.views (table_catalog, table_schema, table_name, view_definition, check_option, is_updatable, is_insertable_into, is_trigger_updatable, is_trigger_deletable, is_trigger_insertable_into) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.Views.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("table_catalog", "table_schema", "table_name", "view_definition", "check_option", "is_updatable", "is_insertable_into", "is_trigger_updatable", "is_trigger_deletable", "is_trigger_insertable_into")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.Views.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.Views.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.Views.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.Views.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.views
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
      FROM information_schema.views
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
