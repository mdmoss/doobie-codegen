package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object ViewTableUsage extends ViewTableUsage {

  case class Row(
    viewCatalog: Option[String],
    viewSchema: Option[String],
    viewName: Option[String],
    tableCatalog: Option[String],
    tableSchema: Option[String],
    tableName: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(viewCatalog, viewSchema, viewName, tableCatalog, tableSchema, tableName)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"view_catalog, view_schema, view_name, table_catalog, table_schema, table_name"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".view_catalog, " ++ Fragment.const0(a) ++ fr".view_schema, " ++ Fragment.const0(a) ++ fr".view_name, " ++ Fragment.const0(a) ++ fr".table_catalog, " ++ Fragment.const0(a) ++ fr".table_schema, " ++ Fragment.const0(a) ++ fr".table_name"
  }

  case class Shape(viewCatalog: Option[String] = None, viewSchema: Option[String] = None, viewName: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None)

  object Shape {
    def NoDefaults(viewCatalog: Option[String], viewSchema: Option[String], viewName: Option[String], tableCatalog: Option[String], tableSchema: Option[String], tableName: Option[String]): Shape = Shape(viewCatalog, viewSchema, viewName, tableCatalog, tableSchema, tableName)
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
        (row) => (row.viewCatalog, (row.viewSchema, (row.viewName, (row.tableCatalog, (row.tableSchema, (row.tableName))))))
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
        (row) => (row.viewCatalog, (row.viewSchema, (row.viewName, (row.tableCatalog, (row.tableSchema, (row.tableName))))))
      )
    }

}
trait ViewTableUsage {
  import ViewTableUsage._

  def create(viewCatalog: Option[String] = None, viewSchema: Option[String] = None, viewName: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(viewCatalog, viewSchema, viewName, tableCatalog, tableSchema, tableName))
  }

  def createVoid(viewCatalog: Option[String] = None, viewSchema: Option[String] = None, viewName: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(viewCatalog, viewSchema, viewName, tableCatalog, tableSchema, tableName))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.ViewTableUsage.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.view_table_usage (view_catalog, view_schema, view_name, table_catalog, table_schema, table_name) VALUES (?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.ViewTableUsage.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("view_catalog", "view_schema", "view_name", "table_catalog", "table_schema", "table_name")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.ViewTableUsage.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.ViewTableUsage.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.ViewTableUsage.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.ViewTableUsage.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.view_table_usage
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
      FROM information_schema.view_table_usage
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
