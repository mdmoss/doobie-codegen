package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object SqlImplementationInfo extends SqlImplementationInfo {

  case class Row(
    implementationInfoId: Option[String],
    implementationInfoName: Option[String],
    integerValue: Option[Int],
    characterValue: Option[String],
    comments: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(implementationInfoId, implementationInfoName, integerValue, characterValue, comments)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"implementation_info_id, implementation_info_name, integer_value, character_value, comments"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".implementation_info_id, " ++ Fragment.const0(a) ++ fr".implementation_info_name, " ++ Fragment.const0(a) ++ fr".integer_value, " ++ Fragment.const0(a) ++ fr".character_value, " ++ Fragment.const0(a) ++ fr".comments"
  }

  case class Shape(implementationInfoId: Option[String] = None, implementationInfoName: Option[String] = None, integerValue: Option[Int] = None, characterValue: Option[String] = None, comments: Option[String] = None)

  object Shape {
    def NoDefaults(implementationInfoId: Option[String], implementationInfoName: Option[String], integerValue: Option[Int], characterValue: Option[String], comments: Option[String]): Shape = Shape(implementationInfoId, implementationInfoName, integerValue, characterValue, comments)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.implementationInfoId, (row.implementationInfoName, (row.integerValue, (row.characterValue, (row.comments)))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.implementationInfoId, (row.implementationInfoName, (row.integerValue, (row.characterValue, (row.comments)))))
      )
    }

}
trait SqlImplementationInfo {
  import SqlImplementationInfo._

  def create(implementationInfoId: Option[String] = None, implementationInfoName: Option[String] = None, integerValue: Option[Int] = None, characterValue: Option[String] = None, comments: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(implementationInfoId, implementationInfoName, integerValue, characterValue, comments))
  }

  def createVoid(implementationInfoId: Option[String] = None, implementationInfoName: Option[String] = None, integerValue: Option[Int] = None, characterValue: Option[String] = None, comments: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(implementationInfoId, implementationInfoName, integerValue, characterValue, comments))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlImplementationInfo.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.sql_implementation_info (implementation_info_id, implementation_info_name, integer_value, character_value, comments) VALUES (?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlImplementationInfo.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("implementation_info_id", "implementation_info_name", "integer_value", "character_value", "comments")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlImplementationInfo.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlImplementationInfo.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.SqlImplementationInfo.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.SqlImplementationInfo.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.sql_implementation_info
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
      FROM information_schema.sql_implementation_info
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
