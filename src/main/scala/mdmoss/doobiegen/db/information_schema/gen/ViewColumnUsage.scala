package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object ViewColumnUsage extends ViewColumnUsage {

  case class Row(
    viewCatalog: Option[String],
    viewSchema: Option[String],
    viewName: Option[String],
    tableCatalog: Option[String],
    tableSchema: Option[String],
    tableName: Option[String],
    columnName: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(viewCatalog, viewSchema, viewName, tableCatalog, tableSchema, tableName, columnName)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"view_catalog, view_schema, view_name, table_catalog, table_schema, table_name, column_name"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".view_catalog, " ++ Fragment.const0(a) ++ fr".view_schema, " ++ Fragment.const0(a) ++ fr".view_name, " ++ Fragment.const0(a) ++ fr".table_catalog, " ++ Fragment.const0(a) ++ fr".table_schema, " ++ Fragment.const0(a) ++ fr".table_name, " ++ Fragment.const0(a) ++ fr".column_name"
  }

  case class Shape(viewCatalog: Option[String] = None, viewSchema: Option[String] = None, viewName: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, columnName: Option[String] = None)

  object Shape {
    def NoDefaults(viewCatalog: Option[String], viewSchema: Option[String], viewName: Option[String], tableCatalog: Option[String], tableSchema: Option[String], tableName: Option[String], columnName: Option[String]): Shape = Shape(viewCatalog, viewSchema, viewName, tableCatalog, tableSchema, tableName, columnName)
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
        (row) => (row.viewCatalog, (row.viewSchema, (row.viewName, (row.tableCatalog, (row.tableSchema, (row.tableName, (row.columnName)))))))
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
        (row) => (row.viewCatalog, (row.viewSchema, (row.viewName, (row.tableCatalog, (row.tableSchema, (row.tableName, (row.columnName)))))))
      )
    }

}
trait ViewColumnUsage {
  import ViewColumnUsage._

  def create(viewCatalog: Option[String] = None, viewSchema: Option[String] = None, viewName: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, columnName: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(viewCatalog, viewSchema, viewName, tableCatalog, tableSchema, tableName, columnName))
  }

  def createVoid(viewCatalog: Option[String] = None, viewSchema: Option[String] = None, viewName: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, columnName: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(viewCatalog, viewSchema, viewName, tableCatalog, tableSchema, tableName, columnName))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.ViewColumnUsage.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.view_column_usage (view_catalog, view_schema, view_name, table_catalog, table_schema, table_name, column_name) VALUES (?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.ViewColumnUsage.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("view_catalog", "view_schema", "view_name", "table_catalog", "table_schema", "table_name", "column_name")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.ViewColumnUsage.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.ViewColumnUsage.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.ViewColumnUsage.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.ViewColumnUsage.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.view_column_usage
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
      FROM information_schema.view_column_usage
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
