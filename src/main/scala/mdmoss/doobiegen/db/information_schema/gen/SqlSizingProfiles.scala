package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object SqlSizingProfiles extends SqlSizingProfiles {

  case class Row(
    sizingId: Option[Int],
    sizingName: Option[String],
    profileId: Option[String],
    requiredValue: Option[Int],
    comments: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(sizingId, sizingName, profileId, requiredValue, comments)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"sizing_id, sizing_name, profile_id, required_value, comments"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".sizing_id, " ++ Fragment.const0(a) ++ fr".sizing_name, " ++ Fragment.const0(a) ++ fr".profile_id, " ++ Fragment.const0(a) ++ fr".required_value, " ++ Fragment.const0(a) ++ fr".comments"
  }

  case class Shape(sizingId: Option[Int] = None, sizingName: Option[String] = None, profileId: Option[String] = None, requiredValue: Option[Int] = None, comments: Option[String] = None)

  object Shape {
    def NoDefaults(sizingId: Option[Int], sizingName: Option[String], profileId: Option[String], requiredValue: Option[Int], comments: Option[String]): Shape = Shape(sizingId, sizingName, profileId, requiredValue, comments)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.sizingId, (row.sizingName, (row.profileId, (row.requiredValue, (row.comments)))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.sizingId, (row.sizingName, (row.profileId, (row.requiredValue, (row.comments)))))
      )
    }

}
trait SqlSizingProfiles {
  import SqlSizingProfiles._

  def create(sizingId: Option[Int] = None, sizingName: Option[String] = None, profileId: Option[String] = None, requiredValue: Option[Int] = None, comments: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(sizingId, sizingName, profileId, requiredValue, comments))
  }

  def createVoid(sizingId: Option[Int] = None, sizingName: Option[String] = None, profileId: Option[String] = None, requiredValue: Option[Int] = None, comments: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(sizingId, sizingName, profileId, requiredValue, comments))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlSizingProfiles.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.sql_sizing_profiles (sizing_id, sizing_name, profile_id, required_value, comments) VALUES (?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlSizingProfiles.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("sizing_id", "sizing_name", "profile_id", "required_value", "comments")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlSizingProfiles.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlSizingProfiles.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.SqlSizingProfiles.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.SqlSizingProfiles.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.sql_sizing_profiles
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
      FROM information_schema.sql_sizing_profiles
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
