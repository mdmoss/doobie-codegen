package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object SqlSizing extends SqlSizing {

  case class Row(
    sizingId: Option[Int],
    sizingName: Option[String],
    supportedValue: Option[Int],
    comments: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(sizingId, sizingName, supportedValue, comments)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"sizing_id, sizing_name, supported_value, comments"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".sizing_id, " ++ Fragment.const0(a) ++ fr".sizing_name, " ++ Fragment.const0(a) ++ fr".supported_value, " ++ Fragment.const0(a) ++ fr".comments"
  }

  case class Shape(sizingId: Option[Int] = None, sizingName: Option[String] = None, supportedValue: Option[Int] = None, comments: Option[String] = None)

  object Shape {
    def NoDefaults(sizingId: Option[Int], sizingName: Option[String], supportedValue: Option[Int], comments: Option[String]): Shape = Shape(sizingId, sizingName, supportedValue, comments)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.sizingId, (row.sizingName, (row.supportedValue, (row.comments))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.sizingId, (row.sizingName, (row.supportedValue, (row.comments))))
      )
    }

}
trait SqlSizing {
  import SqlSizing._

  def create(sizingId: Option[Int] = None, sizingName: Option[String] = None, supportedValue: Option[Int] = None, comments: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(sizingId, sizingName, supportedValue, comments))
  }

  def createVoid(sizingId: Option[Int] = None, sizingName: Option[String] = None, supportedValue: Option[Int] = None, comments: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(sizingId, sizingName, supportedValue, comments))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlSizing.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.sql_sizing (sizing_id, sizing_name, supported_value, comments) VALUES (?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlSizing.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("sizing_id", "sizing_name", "supported_value", "comments")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlSizing.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlSizing.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.SqlSizing.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.SqlSizing.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.sql_sizing
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
      FROM information_schema.sql_sizing
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
