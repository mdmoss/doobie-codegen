package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TriggeredUpdateColumns extends TriggeredUpdateColumns {

  case class Row(
    triggerCatalog: Option[String],
    triggerSchema: Option[String],
    triggerName: Option[String],
    eventObjectCatalog: Option[String],
    eventObjectSchema: Option[String],
    eventObjectTable: Option[String],
    eventObjectColumn: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(triggerCatalog, triggerSchema, triggerName, eventObjectCatalog, eventObjectSchema, eventObjectTable, eventObjectColumn)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"trigger_catalog, trigger_schema, trigger_name, event_object_catalog, event_object_schema, event_object_table, event_object_column"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".trigger_catalog, " ++ Fragment.const0(a) ++ fr".trigger_schema, " ++ Fragment.const0(a) ++ fr".trigger_name, " ++ Fragment.const0(a) ++ fr".event_object_catalog, " ++ Fragment.const0(a) ++ fr".event_object_schema, " ++ Fragment.const0(a) ++ fr".event_object_table, " ++ Fragment.const0(a) ++ fr".event_object_column"
  }

  case class Shape(triggerCatalog: Option[String] = None, triggerSchema: Option[String] = None, triggerName: Option[String] = None, eventObjectCatalog: Option[String] = None, eventObjectSchema: Option[String] = None, eventObjectTable: Option[String] = None, eventObjectColumn: Option[String] = None)

  object Shape {
    def NoDefaults(triggerCatalog: Option[String], triggerSchema: Option[String], triggerName: Option[String], eventObjectCatalog: Option[String], eventObjectSchema: Option[String], eventObjectTable: Option[String], eventObjectColumn: Option[String]): Shape = Shape(triggerCatalog, triggerSchema, triggerName, eventObjectCatalog, eventObjectSchema, eventObjectTable, eventObjectColumn)
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
        (row) => (row.triggerCatalog, (row.triggerSchema, (row.triggerName, (row.eventObjectCatalog, (row.eventObjectSchema, (row.eventObjectTable, (row.eventObjectColumn)))))))
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
        (row) => (row.triggerCatalog, (row.triggerSchema, (row.triggerName, (row.eventObjectCatalog, (row.eventObjectSchema, (row.eventObjectTable, (row.eventObjectColumn)))))))
      )
    }

}
trait TriggeredUpdateColumns {
  import TriggeredUpdateColumns._

  def create(triggerCatalog: Option[String] = None, triggerSchema: Option[String] = None, triggerName: Option[String] = None, eventObjectCatalog: Option[String] = None, eventObjectSchema: Option[String] = None, eventObjectTable: Option[String] = None, eventObjectColumn: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(triggerCatalog, triggerSchema, triggerName, eventObjectCatalog, eventObjectSchema, eventObjectTable, eventObjectColumn))
  }

  def createVoid(triggerCatalog: Option[String] = None, triggerSchema: Option[String] = None, triggerName: Option[String] = None, eventObjectCatalog: Option[String] = None, eventObjectSchema: Option[String] = None, eventObjectTable: Option[String] = None, eventObjectColumn: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(triggerCatalog, triggerSchema, triggerName, eventObjectCatalog, eventObjectSchema, eventObjectTable, eventObjectColumn))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.TriggeredUpdateColumns.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.triggered_update_columns (trigger_catalog, trigger_schema, trigger_name, event_object_catalog, event_object_schema, event_object_table, event_object_column) VALUES (?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.TriggeredUpdateColumns.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("trigger_catalog", "trigger_schema", "trigger_name", "event_object_catalog", "event_object_schema", "event_object_table", "event_object_column")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.TriggeredUpdateColumns.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.TriggeredUpdateColumns.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.TriggeredUpdateColumns.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.TriggeredUpdateColumns.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.triggered_update_columns
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
      FROM information_schema.triggered_update_columns
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
