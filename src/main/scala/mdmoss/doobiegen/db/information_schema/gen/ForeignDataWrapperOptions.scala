package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object ForeignDataWrapperOptions extends ForeignDataWrapperOptions {

  case class Row(
    foreignDataWrapperCatalog: Option[String],
    foreignDataWrapperName: Option[String],
    optionName: Option[String],
    optionValue: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(foreignDataWrapperCatalog, foreignDataWrapperName, optionName, optionValue)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"foreign_data_wrapper_catalog, foreign_data_wrapper_name, option_name, option_value"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".foreign_data_wrapper_catalog, " ++ Fragment.const0(a) ++ fr".foreign_data_wrapper_name, " ++ Fragment.const0(a) ++ fr".option_name, " ++ Fragment.const0(a) ++ fr".option_value"
  }

  case class Shape(foreignDataWrapperCatalog: Option[String] = None, foreignDataWrapperName: Option[String] = None, optionName: Option[String] = None, optionValue: Option[String] = None)

  object Shape {
    def NoDefaults(foreignDataWrapperCatalog: Option[String], foreignDataWrapperName: Option[String], optionName: Option[String], optionValue: Option[String]): Shape = Shape(foreignDataWrapperCatalog, foreignDataWrapperName, optionName, optionValue)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.foreignDataWrapperCatalog, (row.foreignDataWrapperName, (row.optionName, (row.optionValue))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.foreignDataWrapperCatalog, (row.foreignDataWrapperName, (row.optionName, (row.optionValue))))
      )
    }

}
trait ForeignDataWrapperOptions {
  import ForeignDataWrapperOptions._

  def create(foreignDataWrapperCatalog: Option[String] = None, foreignDataWrapperName: Option[String] = None, optionName: Option[String] = None, optionValue: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(foreignDataWrapperCatalog, foreignDataWrapperName, optionName, optionValue))
  }

  def createVoid(foreignDataWrapperCatalog: Option[String] = None, foreignDataWrapperName: Option[String] = None, optionName: Option[String] = None, optionValue: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(foreignDataWrapperCatalog, foreignDataWrapperName, optionName, optionValue))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.ForeignDataWrapperOptions.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.foreign_data_wrapper_options (foreign_data_wrapper_catalog, foreign_data_wrapper_name, option_name, option_value) VALUES (?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.ForeignDataWrapperOptions.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("foreign_data_wrapper_catalog", "foreign_data_wrapper_name", "option_name", "option_value")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.ForeignDataWrapperOptions.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.ForeignDataWrapperOptions.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.ForeignDataWrapperOptions.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.ForeignDataWrapperOptions.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.foreign_data_wrapper_options
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
      FROM information_schema.foreign_data_wrapper_options
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
