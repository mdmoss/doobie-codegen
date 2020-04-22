package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object Triggers extends Triggers {

  case class Row(
    triggerCatalog: Option[String],
    triggerSchema: Option[String],
    triggerName: Option[String],
    eventManipulation: Option[String],
    eventObjectCatalog: Option[String],
    eventObjectSchema: Option[String],
    eventObjectTable: Option[String],
    actionOrder: Option[Int],
    actionCondition: Option[String],
    actionStatement: Option[String],
    actionOrientation: Option[String],
    actionTiming: Option[String],
    actionReferenceOldTable: Option[String],
    actionReferenceNewTable: Option[String],
    actionReferenceOldRow: Option[String],
    actionReferenceNewRow: Option[String],
    created: Option[Timestamp]
  ) {
    def toShape: Shape = Shape.NoDefaults(triggerCatalog, triggerSchema, triggerName, eventManipulation, eventObjectCatalog, eventObjectSchema, eventObjectTable, actionOrder, actionCondition, actionStatement, actionOrientation, actionTiming, actionReferenceOldTable, actionReferenceNewTable, actionReferenceOldRow, actionReferenceNewRow, created)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"trigger_catalog, trigger_schema, trigger_name, event_manipulation, event_object_catalog, event_object_schema, event_object_table, action_order, action_condition, action_statement, action_orientation, action_timing, action_reference_old_table, action_reference_new_table, action_reference_old_row, action_reference_new_row, created"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".trigger_catalog, " ++ Fragment.const0(a) ++ fr".trigger_schema, " ++ Fragment.const0(a) ++ fr".trigger_name, " ++ Fragment.const0(a) ++ fr".event_manipulation, " ++ Fragment.const0(a) ++ fr".event_object_catalog, " ++ Fragment.const0(a) ++ fr".event_object_schema, " ++ Fragment.const0(a) ++ fr".event_object_table, " ++ Fragment.const0(a) ++ fr".action_order, " ++ Fragment.const0(a) ++ fr".action_condition, " ++ Fragment.const0(a) ++ fr".action_statement, " ++ Fragment.const0(a) ++ fr".action_orientation, " ++ Fragment.const0(a) ++ fr".action_timing, " ++ Fragment.const0(a) ++ fr".action_reference_old_table, " ++ Fragment.const0(a) ++ fr".action_reference_new_table, " ++ Fragment.const0(a) ++ fr".action_reference_old_row, " ++ Fragment.const0(a) ++ fr".action_reference_new_row, " ++ Fragment.const0(a) ++ fr".created"
  }

  case class Shape(triggerCatalog: Option[String] = None, triggerSchema: Option[String] = None, triggerName: Option[String] = None, eventManipulation: Option[String] = None, eventObjectCatalog: Option[String] = None, eventObjectSchema: Option[String] = None, eventObjectTable: Option[String] = None, actionOrder: Option[Int] = None, actionCondition: Option[String] = None, actionStatement: Option[String] = None, actionOrientation: Option[String] = None, actionTiming: Option[String] = None, actionReferenceOldTable: Option[String] = None, actionReferenceNewTable: Option[String] = None, actionReferenceOldRow: Option[String] = None, actionReferenceNewRow: Option[String] = None, created: Option[Timestamp] = None)

  object Shape {
    def NoDefaults(triggerCatalog: Option[String], triggerSchema: Option[String], triggerName: Option[String], eventManipulation: Option[String], eventObjectCatalog: Option[String], eventObjectSchema: Option[String], eventObjectTable: Option[String], actionOrder: Option[Int], actionCondition: Option[String], actionStatement: Option[String], actionOrientation: Option[String], actionTiming: Option[String], actionReferenceOldTable: Option[String], actionReferenceNewTable: Option[String], actionReferenceOldRow: Option[String], actionReferenceNewRow: Option[String], created: Option[Timestamp]): Shape = Shape(triggerCatalog, triggerSchema, triggerName, eventManipulation, eventObjectCatalog, eventObjectSchema, eventObjectTable, actionOrder, actionCondition, actionStatement, actionOrientation, actionTiming, actionReferenceOldTable, actionReferenceNewTable, actionReferenceOldRow, actionReferenceNewRow, created)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)))))))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.triggerCatalog, (row.triggerSchema, (row.triggerName, (row.eventManipulation, (row.eventObjectCatalog, (row.eventObjectSchema, (row.eventObjectTable, (row.actionOrder, (row.actionCondition, (row.actionStatement, (row.actionOrientation, (row.actionTiming, (row.actionReferenceOldTable, (row.actionReferenceNewTable, (row.actionReferenceOldRow, (row.actionReferenceNewRow, (row.created)))))))))))))))))
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
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)))))))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.triggerCatalog, (row.triggerSchema, (row.triggerName, (row.eventManipulation, (row.eventObjectCatalog, (row.eventObjectSchema, (row.eventObjectTable, (row.actionOrder, (row.actionCondition, (row.actionStatement, (row.actionOrientation, (row.actionTiming, (row.actionReferenceOldTable, (row.actionReferenceNewTable, (row.actionReferenceOldRow, (row.actionReferenceNewRow, (row.created)))))))))))))))))
      )
    }

}
trait Triggers {
  import Triggers._

  def create(triggerCatalog: Option[String] = None, triggerSchema: Option[String] = None, triggerName: Option[String] = None, eventManipulation: Option[String] = None, eventObjectCatalog: Option[String] = None, eventObjectSchema: Option[String] = None, eventObjectTable: Option[String] = None, actionOrder: Option[Int] = None, actionCondition: Option[String] = None, actionStatement: Option[String] = None, actionOrientation: Option[String] = None, actionTiming: Option[String] = None, actionReferenceOldTable: Option[String] = None, actionReferenceNewTable: Option[String] = None, actionReferenceOldRow: Option[String] = None, actionReferenceNewRow: Option[String] = None, created: Option[Timestamp] = None): ConnectionIO[Row] = {
    create(Shape(triggerCatalog, triggerSchema, triggerName, eventManipulation, eventObjectCatalog, eventObjectSchema, eventObjectTable, actionOrder, actionCondition, actionStatement, actionOrientation, actionTiming, actionReferenceOldTable, actionReferenceNewTable, actionReferenceOldRow, actionReferenceNewRow, created))
  }

  def createVoid(triggerCatalog: Option[String] = None, triggerSchema: Option[String] = None, triggerName: Option[String] = None, eventManipulation: Option[String] = None, eventObjectCatalog: Option[String] = None, eventObjectSchema: Option[String] = None, eventObjectTable: Option[String] = None, actionOrder: Option[Int] = None, actionCondition: Option[String] = None, actionStatement: Option[String] = None, actionOrientation: Option[String] = None, actionTiming: Option[String] = None, actionReferenceOldTable: Option[String] = None, actionReferenceNewTable: Option[String] = None, actionReferenceOldRow: Option[String] = None, actionReferenceNewRow: Option[String] = None, created: Option[Timestamp] = None): ConnectionIO[Unit] = {
    createVoid(Shape(triggerCatalog, triggerSchema, triggerName, eventManipulation, eventObjectCatalog, eventObjectSchema, eventObjectTable, actionOrder, actionCondition, actionStatement, actionOrientation, actionTiming, actionReferenceOldTable, actionReferenceNewTable, actionReferenceOldRow, actionReferenceNewRow, created))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.Triggers.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.triggers (trigger_catalog, trigger_schema, trigger_name, event_manipulation, event_object_catalog, event_object_schema, event_object_table, action_order, action_condition, action_statement, action_orientation, action_timing, action_reference_old_table, action_reference_new_table, action_reference_old_row, action_reference_new_row, created) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.Triggers.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("trigger_catalog", "trigger_schema", "trigger_name", "event_manipulation", "event_object_catalog", "event_object_schema", "event_object_table", "action_order", "action_condition", "action_statement", "action_orientation", "action_timing", "action_reference_old_table", "action_reference_new_table", "action_reference_old_row", "action_reference_new_row", "created")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.Triggers.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.Triggers.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.Triggers.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.Triggers.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.triggers
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
      FROM information_schema.triggers
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
