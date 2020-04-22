package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object Sequences extends Sequences {

  case class Row(
    sequenceCatalog: Option[String],
    sequenceSchema: Option[String],
    sequenceName: Option[String],
    dataType: Option[String],
    numericPrecision: Option[Int],
    numericPrecisionRadix: Option[Int],
    numericScale: Option[Int],
    startValue: Option[String],
    minimumValue: Option[String],
    maximumValue: Option[String],
    increment: Option[String],
    cycleOption: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(sequenceCatalog, sequenceSchema, sequenceName, dataType, numericPrecision, numericPrecisionRadix, numericScale, startValue, minimumValue, maximumValue, increment, cycleOption)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"sequence_catalog, sequence_schema, sequence_name, data_type, numeric_precision, numeric_precision_radix, numeric_scale, start_value, minimum_value, maximum_value, increment, cycle_option"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".sequence_catalog, " ++ Fragment.const0(a) ++ fr".sequence_schema, " ++ Fragment.const0(a) ++ fr".sequence_name, " ++ Fragment.const0(a) ++ fr".data_type, " ++ Fragment.const0(a) ++ fr".numeric_precision, " ++ Fragment.const0(a) ++ fr".numeric_precision_radix, " ++ Fragment.const0(a) ++ fr".numeric_scale, " ++ Fragment.const0(a) ++ fr".start_value, " ++ Fragment.const0(a) ++ fr".minimum_value, " ++ Fragment.const0(a) ++ fr".maximum_value, " ++ Fragment.const0(a) ++ fr".increment, " ++ Fragment.const0(a) ++ fr".cycle_option"
  }

  case class Shape(sequenceCatalog: Option[String] = None, sequenceSchema: Option[String] = None, sequenceName: Option[String] = None, dataType: Option[String] = None, numericPrecision: Option[Int] = None, numericPrecisionRadix: Option[Int] = None, numericScale: Option[Int] = None, startValue: Option[String] = None, minimumValue: Option[String] = None, maximumValue: Option[String] = None, increment: Option[String] = None, cycleOption: Option[String] = None)

  object Shape {
    def NoDefaults(sequenceCatalog: Option[String], sequenceSchema: Option[String], sequenceName: Option[String], dataType: Option[String], numericPrecision: Option[Int], numericPrecisionRadix: Option[Int], numericScale: Option[Int], startValue: Option[String], minimumValue: Option[String], maximumValue: Option[String], increment: Option[String], cycleOption: Option[String]): Shape = Shape(sequenceCatalog, sequenceSchema, sequenceName, dataType, numericPrecision, numericPrecisionRadix, numericScale, startValue, minimumValue, maximumValue, increment, cycleOption)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
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
        (row) => (row.sequenceCatalog, (row.sequenceSchema, (row.sequenceName, (row.dataType, (row.numericPrecision, (row.numericPrecisionRadix, (row.numericScale, (row.startValue, (row.minimumValue, (row.maximumValue, (row.increment, (row.cycleOption))))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
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
        (row) => (row.sequenceCatalog, (row.sequenceSchema, (row.sequenceName, (row.dataType, (row.numericPrecision, (row.numericPrecisionRadix, (row.numericScale, (row.startValue, (row.minimumValue, (row.maximumValue, (row.increment, (row.cycleOption))))))))))))
      )
    }

}
trait Sequences {
  import Sequences._

  def create(sequenceCatalog: Option[String] = None, sequenceSchema: Option[String] = None, sequenceName: Option[String] = None, dataType: Option[String] = None, numericPrecision: Option[Int] = None, numericPrecisionRadix: Option[Int] = None, numericScale: Option[Int] = None, startValue: Option[String] = None, minimumValue: Option[String] = None, maximumValue: Option[String] = None, increment: Option[String] = None, cycleOption: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(sequenceCatalog, sequenceSchema, sequenceName, dataType, numericPrecision, numericPrecisionRadix, numericScale, startValue, minimumValue, maximumValue, increment, cycleOption))
  }

  def createVoid(sequenceCatalog: Option[String] = None, sequenceSchema: Option[String] = None, sequenceName: Option[String] = None, dataType: Option[String] = None, numericPrecision: Option[Int] = None, numericPrecisionRadix: Option[Int] = None, numericScale: Option[Int] = None, startValue: Option[String] = None, minimumValue: Option[String] = None, maximumValue: Option[String] = None, increment: Option[String] = None, cycleOption: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(sequenceCatalog, sequenceSchema, sequenceName, dataType, numericPrecision, numericPrecisionRadix, numericScale, startValue, minimumValue, maximumValue, increment, cycleOption))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.Sequences.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.sequences (sequence_catalog, sequence_schema, sequence_name, data_type, numeric_precision, numeric_precision_radix, numeric_scale, start_value, minimum_value, maximum_value, increment, cycle_option) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.Sequences.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("sequence_catalog", "sequence_schema", "sequence_name", "data_type", "numeric_precision", "numeric_precision_radix", "numeric_scale", "start_value", "minimum_value", "maximum_value", "increment", "cycle_option")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.Sequences.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.Sequences.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.Sequences.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.Sequences.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.sequences
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
      FROM information_schema.sequences
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
