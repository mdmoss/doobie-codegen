package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TableConstraints extends TableConstraints {

  case class Row(
    constraintCatalog: Option[String],
    constraintSchema: Option[String],
    constraintName: Option[String],
    tableCatalog: Option[String],
    tableSchema: Option[String],
    tableName: Option[String],
    constraintType: Option[String],
    isDeferrable: Option[String],
    initiallyDeferred: Option[String],
    enforced: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(constraintCatalog, constraintSchema, constraintName, tableCatalog, tableSchema, tableName, constraintType, isDeferrable, initiallyDeferred, enforced)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"constraint_catalog, constraint_schema, constraint_name, table_catalog, table_schema, table_name, constraint_type, is_deferrable, initially_deferred, enforced"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".constraint_catalog, " ++ Fragment.const0(a) ++ fr".constraint_schema, " ++ Fragment.const0(a) ++ fr".constraint_name, " ++ Fragment.const0(a) ++ fr".table_catalog, " ++ Fragment.const0(a) ++ fr".table_schema, " ++ Fragment.const0(a) ++ fr".table_name, " ++ Fragment.const0(a) ++ fr".constraint_type, " ++ Fragment.const0(a) ++ fr".is_deferrable, " ++ Fragment.const0(a) ++ fr".initially_deferred, " ++ Fragment.const0(a) ++ fr".enforced"
  }

  case class Shape(constraintCatalog: Option[String] = None, constraintSchema: Option[String] = None, constraintName: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, constraintType: Option[String] = None, isDeferrable: Option[String] = None, initiallyDeferred: Option[String] = None, enforced: Option[String] = None)

  object Shape {
    def NoDefaults(constraintCatalog: Option[String], constraintSchema: Option[String], constraintName: Option[String], tableCatalog: Option[String], tableSchema: Option[String], tableName: Option[String], constraintType: Option[String], isDeferrable: Option[String], initiallyDeferred: Option[String], enforced: Option[String]): Shape = Shape(constraintCatalog, constraintSchema, constraintName, tableCatalog, tableSchema, tableName, constraintType, isDeferrable, initiallyDeferred, enforced)
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
        (row) => (row.constraintCatalog, (row.constraintSchema, (row.constraintName, (row.tableCatalog, (row.tableSchema, (row.tableName, (row.constraintType, (row.isDeferrable, (row.initiallyDeferred, (row.enforced))))))))))
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
        (row) => (row.constraintCatalog, (row.constraintSchema, (row.constraintName, (row.tableCatalog, (row.tableSchema, (row.tableName, (row.constraintType, (row.isDeferrable, (row.initiallyDeferred, (row.enforced))))))))))
      )
    }

}
trait TableConstraints {
  import TableConstraints._

  def create(constraintCatalog: Option[String] = None, constraintSchema: Option[String] = None, constraintName: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, constraintType: Option[String] = None, isDeferrable: Option[String] = None, initiallyDeferred: Option[String] = None, enforced: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(constraintCatalog, constraintSchema, constraintName, tableCatalog, tableSchema, tableName, constraintType, isDeferrable, initiallyDeferred, enforced))
  }

  def createVoid(constraintCatalog: Option[String] = None, constraintSchema: Option[String] = None, constraintName: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, constraintType: Option[String] = None, isDeferrable: Option[String] = None, initiallyDeferred: Option[String] = None, enforced: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(constraintCatalog, constraintSchema, constraintName, tableCatalog, tableSchema, tableName, constraintType, isDeferrable, initiallyDeferred, enforced))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.TableConstraints.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.table_constraints (constraint_catalog, constraint_schema, constraint_name, table_catalog, table_schema, table_name, constraint_type, is_deferrable, initially_deferred, enforced) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.TableConstraints.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("constraint_catalog", "constraint_schema", "constraint_name", "table_catalog", "table_schema", "table_name", "constraint_type", "is_deferrable", "initially_deferred", "enforced")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.TableConstraints.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.TableConstraints.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.TableConstraints.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.TableConstraints.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.table_constraints
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
      FROM information_schema.table_constraints
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
