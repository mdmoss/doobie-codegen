package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object KeyColumnUsage extends KeyColumnUsage {

  case class Row(
    constraintCatalog: Option[String],
    constraintSchema: Option[String],
    constraintName: Option[String],
    tableCatalog: Option[String],
    tableSchema: Option[String],
    tableName: Option[String],
    columnName: Option[String],
    ordinalPosition: Option[Int],
    positionInUniqueConstraint: Option[Int]
  ) {
    def toShape: Shape = Shape.NoDefaults(constraintCatalog, constraintSchema, constraintName, tableCatalog, tableSchema, tableName, columnName, ordinalPosition, positionInUniqueConstraint)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"constraint_catalog, constraint_schema, constraint_name, table_catalog, table_schema, table_name, column_name, ordinal_position, position_in_unique_constraint"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".constraint_catalog, " ++ Fragment.const0(a) ++ fr".constraint_schema, " ++ Fragment.const0(a) ++ fr".constraint_name, " ++ Fragment.const0(a) ++ fr".table_catalog, " ++ Fragment.const0(a) ++ fr".table_schema, " ++ Fragment.const0(a) ++ fr".table_name, " ++ Fragment.const0(a) ++ fr".column_name, " ++ Fragment.const0(a) ++ fr".ordinal_position, " ++ Fragment.const0(a) ++ fr".position_in_unique_constraint"
  }

  case class Shape(constraintCatalog: Option[String] = None, constraintSchema: Option[String] = None, constraintName: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, columnName: Option[String] = None, ordinalPosition: Option[Int] = None, positionInUniqueConstraint: Option[Int] = None)

  object Shape {
    def NoDefaults(constraintCatalog: Option[String], constraintSchema: Option[String], constraintName: Option[String], tableCatalog: Option[String], tableSchema: Option[String], tableName: Option[String], columnName: Option[String], ordinalPosition: Option[Int], positionInUniqueConstraint: Option[Int]): Shape = Shape(constraintCatalog, constraintSchema, constraintName, tableCatalog, tableSchema, tableName, columnName, ordinalPosition, positionInUniqueConstraint)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)))))))))

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
    t._2._2._2._2._2._2._2._2),
        (row) => (row.constraintCatalog, (row.constraintSchema, (row.constraintName, (row.tableCatalog, (row.tableSchema, (row.tableName, (row.columnName, (row.ordinalPosition, (row.positionInUniqueConstraint)))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)))))))))

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
    t._2._2._2._2._2._2._2._2),
        (row) => (row.constraintCatalog, (row.constraintSchema, (row.constraintName, (row.tableCatalog, (row.tableSchema, (row.tableName, (row.columnName, (row.ordinalPosition, (row.positionInUniqueConstraint)))))))))
      )
    }

}
trait KeyColumnUsage {
  import KeyColumnUsage._

  def create(constraintCatalog: Option[String] = None, constraintSchema: Option[String] = None, constraintName: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, columnName: Option[String] = None, ordinalPosition: Option[Int] = None, positionInUniqueConstraint: Option[Int] = None): ConnectionIO[Row] = {
    create(Shape(constraintCatalog, constraintSchema, constraintName, tableCatalog, tableSchema, tableName, columnName, ordinalPosition, positionInUniqueConstraint))
  }

  def createVoid(constraintCatalog: Option[String] = None, constraintSchema: Option[String] = None, constraintName: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, columnName: Option[String] = None, ordinalPosition: Option[Int] = None, positionInUniqueConstraint: Option[Int] = None): ConnectionIO[Unit] = {
    createVoid(Shape(constraintCatalog, constraintSchema, constraintName, tableCatalog, tableSchema, tableName, columnName, ordinalPosition, positionInUniqueConstraint))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.KeyColumnUsage.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.key_column_usage (constraint_catalog, constraint_schema, constraint_name, table_catalog, table_schema, table_name, column_name, ordinal_position, position_in_unique_constraint) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.KeyColumnUsage.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("constraint_catalog", "constraint_schema", "constraint_name", "table_catalog", "table_schema", "table_name", "column_name", "ordinal_position", "position_in_unique_constraint")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.KeyColumnUsage.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.KeyColumnUsage.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.KeyColumnUsage.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.KeyColumnUsage.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.key_column_usage
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
      FROM information_schema.key_column_usage
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
