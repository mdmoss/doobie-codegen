package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object SqlFeatures extends SqlFeatures {

  case class Row(
    featureId: Option[String],
    featureName: Option[String],
    subFeatureId: Option[String],
    subFeatureName: Option[String],
    isSupported: Option[String],
    isVerifiedBy: Option[String],
    comments: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(featureId, featureName, subFeatureId, subFeatureName, isSupported, isVerifiedBy, comments)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"feature_id, feature_name, sub_feature_id, sub_feature_name, is_supported, is_verified_by, comments"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".feature_id, " ++ Fragment.const0(a) ++ fr".feature_name, " ++ Fragment.const0(a) ++ fr".sub_feature_id, " ++ Fragment.const0(a) ++ fr".sub_feature_name, " ++ Fragment.const0(a) ++ fr".is_supported, " ++ Fragment.const0(a) ++ fr".is_verified_by, " ++ Fragment.const0(a) ++ fr".comments"
  }

  case class Shape(featureId: Option[String] = None, featureName: Option[String] = None, subFeatureId: Option[String] = None, subFeatureName: Option[String] = None, isSupported: Option[String] = None, isVerifiedBy: Option[String] = None, comments: Option[String] = None)

  object Shape {
    def NoDefaults(featureId: Option[String], featureName: Option[String], subFeatureId: Option[String], subFeatureName: Option[String], isSupported: Option[String], isVerifiedBy: Option[String], comments: Option[String]): Shape = Shape(featureId, featureName, subFeatureId, subFeatureName, isSupported, isVerifiedBy, comments)
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
        (row) => (row.featureId, (row.featureName, (row.subFeatureId, (row.subFeatureName, (row.isSupported, (row.isVerifiedBy, (row.comments)))))))
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
        (row) => (row.featureId, (row.featureName, (row.subFeatureId, (row.subFeatureName, (row.isSupported, (row.isVerifiedBy, (row.comments)))))))
      )
    }

}
trait SqlFeatures {
  import SqlFeatures._

  def create(featureId: Option[String] = None, featureName: Option[String] = None, subFeatureId: Option[String] = None, subFeatureName: Option[String] = None, isSupported: Option[String] = None, isVerifiedBy: Option[String] = None, comments: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(featureId, featureName, subFeatureId, subFeatureName, isSupported, isVerifiedBy, comments))
  }

  def createVoid(featureId: Option[String] = None, featureName: Option[String] = None, subFeatureId: Option[String] = None, subFeatureName: Option[String] = None, isSupported: Option[String] = None, isVerifiedBy: Option[String] = None, comments: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(featureId, featureName, subFeatureId, subFeatureName, isSupported, isVerifiedBy, comments))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlFeatures.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.sql_features (feature_id, feature_name, sub_feature_id, sub_feature_name, is_supported, is_verified_by, comments) VALUES (?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlFeatures.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("feature_id", "feature_name", "sub_feature_id", "sub_feature_name", "is_supported", "is_verified_by", "comments")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlFeatures.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlFeatures.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.SqlFeatures.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.SqlFeatures.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.sql_features
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
      FROM information_schema.sql_features
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
