package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgSettings extends PgSettings {

  case class Row(
    name: Option[String],
    setting: Option[String],
    unit: Option[String],
    category: Option[String],
    shortDesc: Option[String],
    extraDesc: Option[String],
    context: Option[String],
    vartype: Option[String],
    source: Option[String],
    minVal: Option[String],
    maxVal: Option[String],
    bootVal: Option[String],
    resetVal: Option[String],
    sourcefile: Option[String],
    sourceline: Option[Int],
    pendingRestart: Option[Boolean]
  ) {
    def toShape: Shape = Shape.NoDefaults(name, setting, unit, category, shortDesc, extraDesc, context, vartype, source, minVal, maxVal, bootVal, resetVal, sourcefile, sourceline, pendingRestart)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"name, setting, unit, category, short_desc, extra_desc, context, vartype, source, min_val, max_val, boot_val, reset_val, sourcefile, sourceline, pending_restart"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".name, " ++ Fragment.const0(a) ++ fr".setting, " ++ Fragment.const0(a) ++ fr".unit, " ++ Fragment.const0(a) ++ fr".category, " ++ Fragment.const0(a) ++ fr".short_desc, " ++ Fragment.const0(a) ++ fr".extra_desc, " ++ Fragment.const0(a) ++ fr".context, " ++ Fragment.const0(a) ++ fr".vartype, " ++ Fragment.const0(a) ++ fr".source, " ++ Fragment.const0(a) ++ fr".min_val, " ++ Fragment.const0(a) ++ fr".max_val, " ++ Fragment.const0(a) ++ fr".boot_val, " ++ Fragment.const0(a) ++ fr".reset_val, " ++ Fragment.const0(a) ++ fr".sourcefile, " ++ Fragment.const0(a) ++ fr".sourceline, " ++ Fragment.const0(a) ++ fr".pending_restart"
  }

  case class Shape(name: Option[String] = None, setting: Option[String] = None, unit: Option[String] = None, category: Option[String] = None, shortDesc: Option[String] = None, extraDesc: Option[String] = None, context: Option[String] = None, vartype: Option[String] = None, source: Option[String] = None, minVal: Option[String] = None, maxVal: Option[String] = None, bootVal: Option[String] = None, resetVal: Option[String] = None, sourcefile: Option[String] = None, sourceline: Option[Int] = None, pendingRestart: Option[Boolean] = None)

  object Shape {
    def NoDefaults(name: Option[String], setting: Option[String], unit: Option[String], category: Option[String], shortDesc: Option[String], extraDesc: Option[String], context: Option[String], vartype: Option[String], source: Option[String], minVal: Option[String], maxVal: Option[String], bootVal: Option[String], resetVal: Option[String], sourcefile: Option[String], sourceline: Option[Int], pendingRestart: Option[Boolean]): Shape = Shape(name, setting, unit, category, shortDesc, extraDesc, context, vartype, source, minVal, maxVal, bootVal, resetVal, sourcefile, sourceline, pendingRestart)
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
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta))))))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.name, (row.setting, (row.unit, (row.category, (row.shortDesc, (row.extraDesc, (row.context, (row.vartype, (row.source, (row.minVal, (row.maxVal, (row.bootVal, (row.resetVal, (row.sourcefile, (row.sourceline, (row.pendingRestart))))))))))))))))
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
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta))))))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.name, (row.setting, (row.unit, (row.category, (row.shortDesc, (row.extraDesc, (row.context, (row.vartype, (row.source, (row.minVal, (row.maxVal, (row.bootVal, (row.resetVal, (row.sourcefile, (row.sourceline, (row.pendingRestart))))))))))))))))
      )
    }

}
trait PgSettings {
  import PgSettings._

  def create(name: Option[String] = None, setting: Option[String] = None, unit: Option[String] = None, category: Option[String] = None, shortDesc: Option[String] = None, extraDesc: Option[String] = None, context: Option[String] = None, vartype: Option[String] = None, source: Option[String] = None, minVal: Option[String] = None, maxVal: Option[String] = None, bootVal: Option[String] = None, resetVal: Option[String] = None, sourcefile: Option[String] = None, sourceline: Option[Int] = None, pendingRestart: Option[Boolean] = None): ConnectionIO[Row] = {
    create(Shape(name, setting, unit, category, shortDesc, extraDesc, context, vartype, source, minVal, maxVal, bootVal, resetVal, sourcefile, sourceline, pendingRestart))
  }

  def createVoid(name: Option[String] = None, setting: Option[String] = None, unit: Option[String] = None, category: Option[String] = None, shortDesc: Option[String] = None, extraDesc: Option[String] = None, context: Option[String] = None, vartype: Option[String] = None, source: Option[String] = None, minVal: Option[String] = None, maxVal: Option[String] = None, bootVal: Option[String] = None, resetVal: Option[String] = None, sourcefile: Option[String] = None, sourceline: Option[Int] = None, pendingRestart: Option[Boolean] = None): ConnectionIO[Unit] = {
    createVoid(Shape(name, setting, unit, category, shortDesc, extraDesc, context, vartype, source, minVal, maxVal, bootVal, resetVal, sourcefile, sourceline, pendingRestart))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSettings.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_settings (name, setting, unit, category, short_desc, extra_desc, context, vartype, source, min_val, max_val, boot_val, reset_val, sourcefile, sourceline, pending_restart) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSettings.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("name", "setting", "unit", "category", "short_desc", "extra_desc", "context", "vartype", "source", "min_val", "max_val", "boot_val", "reset_val", "sourcefile", "sourceline", "pending_restart")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSettings.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSettings.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgSettings.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgSettings.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_settings
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
      FROM pg_catalog.pg_settings
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
