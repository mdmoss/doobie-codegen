package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object ViewRoutineUsage extends ViewRoutineUsage {

  case class Row(
    tableCatalog: Option[String],
    tableSchema: Option[String],
    tableName: Option[String],
    specificCatalog: Option[String],
    specificSchema: Option[String],
    specificName: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(tableCatalog, tableSchema, tableName, specificCatalog, specificSchema, specificName)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"table_catalog, table_schema, table_name, specific_catalog, specific_schema, specific_name"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".table_catalog, " ++ Fragment.const0(a) ++ fr".table_schema, " ++ Fragment.const0(a) ++ fr".table_name, " ++ Fragment.const0(a) ++ fr".specific_catalog, " ++ Fragment.const0(a) ++ fr".specific_schema, " ++ Fragment.const0(a) ++ fr".specific_name"
  }

  case class Shape(tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, specificCatalog: Option[String] = None, specificSchema: Option[String] = None, specificName: Option[String] = None)

  object Shape {
    def NoDefaults(tableCatalog: Option[String], tableSchema: Option[String], tableName: Option[String], specificCatalog: Option[String], specificSchema: Option[String], specificName: Option[String]): Shape = Shape(tableCatalog, tableSchema, tableName, specificCatalog, specificSchema, specificName)
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
        (row) => (row.tableCatalog, (row.tableSchema, (row.tableName, (row.specificCatalog, (row.specificSchema, (row.specificName))))))
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
        (row) => (row.tableCatalog, (row.tableSchema, (row.tableName, (row.specificCatalog, (row.specificSchema, (row.specificName))))))
      )
    }

}
trait ViewRoutineUsage {
  import ViewRoutineUsage._

  def create(tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, specificCatalog: Option[String] = None, specificSchema: Option[String] = None, specificName: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(tableCatalog, tableSchema, tableName, specificCatalog, specificSchema, specificName))
  }

  def createVoid(tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, specificCatalog: Option[String] = None, specificSchema: Option[String] = None, specificName: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(tableCatalog, tableSchema, tableName, specificCatalog, specificSchema, specificName))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.ViewRoutineUsage.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.view_routine_usage (table_catalog, table_schema, table_name, specific_catalog, specific_schema, specific_name) VALUES (?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.ViewRoutineUsage.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("table_catalog", "table_schema", "table_name", "specific_catalog", "specific_schema", "specific_name")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.ViewRoutineUsage.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.ViewRoutineUsage.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.ViewRoutineUsage.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.ViewRoutineUsage.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.view_routine_usage
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
      FROM information_schema.view_routine_usage
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
