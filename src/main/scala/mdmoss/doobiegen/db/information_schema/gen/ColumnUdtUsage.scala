package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object ColumnUdtUsage extends ColumnUdtUsage {

  case class Row(
    udtCatalog: Option[String],
    udtSchema: Option[String],
    udtName: Option[String],
    tableCatalog: Option[String],
    tableSchema: Option[String],
    tableName: Option[String],
    columnName: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(udtCatalog, udtSchema, udtName, tableCatalog, tableSchema, tableName, columnName)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"udt_catalog, udt_schema, udt_name, table_catalog, table_schema, table_name, column_name"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".udt_catalog, " ++ Fragment.const0(a) ++ fr".udt_schema, " ++ Fragment.const0(a) ++ fr".udt_name, " ++ Fragment.const0(a) ++ fr".table_catalog, " ++ Fragment.const0(a) ++ fr".table_schema, " ++ Fragment.const0(a) ++ fr".table_name, " ++ Fragment.const0(a) ++ fr".column_name"
  }

  case class Shape(udtCatalog: Option[String] = None, udtSchema: Option[String] = None, udtName: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, columnName: Option[String] = None)

  object Shape {
    def NoDefaults(udtCatalog: Option[String], udtSchema: Option[String], udtName: Option[String], tableCatalog: Option[String], tableSchema: Option[String], tableName: Option[String], columnName: Option[String]): Shape = Shape(udtCatalog, udtSchema, udtName, tableCatalog, tableSchema, tableName, columnName)
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
        (row) => (row.udtCatalog, (row.udtSchema, (row.udtName, (row.tableCatalog, (row.tableSchema, (row.tableName, (row.columnName)))))))
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
        (row) => (row.udtCatalog, (row.udtSchema, (row.udtName, (row.tableCatalog, (row.tableSchema, (row.tableName, (row.columnName)))))))
      )
    }

}
trait ColumnUdtUsage {
  import ColumnUdtUsage._

  def create(udtCatalog: Option[String] = None, udtSchema: Option[String] = None, udtName: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, columnName: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(udtCatalog, udtSchema, udtName, tableCatalog, tableSchema, tableName, columnName))
  }

  def createVoid(udtCatalog: Option[String] = None, udtSchema: Option[String] = None, udtName: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, columnName: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(udtCatalog, udtSchema, udtName, tableCatalog, tableSchema, tableName, columnName))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.ColumnUdtUsage.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.column_udt_usage (udt_catalog, udt_schema, udt_name, table_catalog, table_schema, table_name, column_name) VALUES (?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.ColumnUdtUsage.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("udt_catalog", "udt_schema", "udt_name", "table_catalog", "table_schema", "table_name", "column_name")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.ColumnUdtUsage.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.ColumnUdtUsage.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.ColumnUdtUsage.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.ColumnUdtUsage.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.column_udt_usage
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
      FROM information_schema.column_udt_usage
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
