package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object Tables extends Tables {

  case class Row(
    tableCatalog: Option[String],
    tableSchema: Option[String],
    tableName: Option[String],
    tableType: Option[String],
    selfReferencingColumnName: Option[String],
    referenceGeneration: Option[String],
    userDefinedTypeCatalog: Option[String],
    userDefinedTypeSchema: Option[String],
    userDefinedTypeName: Option[String],
    isInsertableInto: Option[String],
    isTyped: Option[String],
    commitAction: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(tableCatalog, tableSchema, tableName, tableType, selfReferencingColumnName, referenceGeneration, userDefinedTypeCatalog, userDefinedTypeSchema, userDefinedTypeName, isInsertableInto, isTyped, commitAction)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"table_catalog, table_schema, table_name, table_type, self_referencing_column_name, reference_generation, user_defined_type_catalog, user_defined_type_schema, user_defined_type_name, is_insertable_into, is_typed, commit_action"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".table_catalog, " ++ Fragment.const0(a) ++ fr".table_schema, " ++ Fragment.const0(a) ++ fr".table_name, " ++ Fragment.const0(a) ++ fr".table_type, " ++ Fragment.const0(a) ++ fr".self_referencing_column_name, " ++ Fragment.const0(a) ++ fr".reference_generation, " ++ Fragment.const0(a) ++ fr".user_defined_type_catalog, " ++ Fragment.const0(a) ++ fr".user_defined_type_schema, " ++ Fragment.const0(a) ++ fr".user_defined_type_name, " ++ Fragment.const0(a) ++ fr".is_insertable_into, " ++ Fragment.const0(a) ++ fr".is_typed, " ++ Fragment.const0(a) ++ fr".commit_action"
  }

  case class Shape(tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, tableType: Option[String] = None, selfReferencingColumnName: Option[String] = None, referenceGeneration: Option[String] = None, userDefinedTypeCatalog: Option[String] = None, userDefinedTypeSchema: Option[String] = None, userDefinedTypeName: Option[String] = None, isInsertableInto: Option[String] = None, isTyped: Option[String] = None, commitAction: Option[String] = None)

  object Shape {
    def NoDefaults(tableCatalog: Option[String], tableSchema: Option[String], tableName: Option[String], tableType: Option[String], selfReferencingColumnName: Option[String], referenceGeneration: Option[String], userDefinedTypeCatalog: Option[String], userDefinedTypeSchema: Option[String], userDefinedTypeName: Option[String], isInsertableInto: Option[String], isTyped: Option[String], commitAction: Option[String]): Shape = Shape(tableCatalog, tableSchema, tableName, tableType, selfReferencingColumnName, referenceGeneration, userDefinedTypeCatalog, userDefinedTypeSchema, userDefinedTypeName, isInsertableInto, isTyped, commitAction)
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
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.tableCatalog, (row.tableSchema, (row.tableName, (row.tableType, (row.selfReferencingColumnName, (row.referenceGeneration, (row.userDefinedTypeCatalog, (row.userDefinedTypeSchema, (row.userDefinedTypeName, (row.isInsertableInto, (row.isTyped, (row.commitAction))))))))))))
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
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.tableCatalog, (row.tableSchema, (row.tableName, (row.tableType, (row.selfReferencingColumnName, (row.referenceGeneration, (row.userDefinedTypeCatalog, (row.userDefinedTypeSchema, (row.userDefinedTypeName, (row.isInsertableInto, (row.isTyped, (row.commitAction))))))))))))
      )
    }

}
trait Tables {
  import Tables._

  def create(tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, tableType: Option[String] = None, selfReferencingColumnName: Option[String] = None, referenceGeneration: Option[String] = None, userDefinedTypeCatalog: Option[String] = None, userDefinedTypeSchema: Option[String] = None, userDefinedTypeName: Option[String] = None, isInsertableInto: Option[String] = None, isTyped: Option[String] = None, commitAction: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(tableCatalog, tableSchema, tableName, tableType, selfReferencingColumnName, referenceGeneration, userDefinedTypeCatalog, userDefinedTypeSchema, userDefinedTypeName, isInsertableInto, isTyped, commitAction))
  }

  def createVoid(tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, tableType: Option[String] = None, selfReferencingColumnName: Option[String] = None, referenceGeneration: Option[String] = None, userDefinedTypeCatalog: Option[String] = None, userDefinedTypeSchema: Option[String] = None, userDefinedTypeName: Option[String] = None, isInsertableInto: Option[String] = None, isTyped: Option[String] = None, commitAction: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(tableCatalog, tableSchema, tableName, tableType, selfReferencingColumnName, referenceGeneration, userDefinedTypeCatalog, userDefinedTypeSchema, userDefinedTypeName, isInsertableInto, isTyped, commitAction))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.Tables.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.tables (table_catalog, table_schema, table_name, table_type, self_referencing_column_name, reference_generation, user_defined_type_catalog, user_defined_type_schema, user_defined_type_name, is_insertable_into, is_typed, commit_action) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.Tables.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("table_catalog", "table_schema", "table_name", "table_type", "self_referencing_column_name", "reference_generation", "user_defined_type_catalog", "user_defined_type_schema", "user_defined_type_name", "is_insertable_into", "is_typed", "commit_action")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.Tables.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.Tables.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.Tables.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.Tables.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.tables
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
      FROM information_schema.tables
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
