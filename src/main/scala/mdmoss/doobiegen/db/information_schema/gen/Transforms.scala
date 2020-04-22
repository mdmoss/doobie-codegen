package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object Transforms extends Transforms {

  case class Row(
    udtCatalog: Option[String],
    udtSchema: Option[String],
    udtName: Option[String],
    specificCatalog: Option[String],
    specificSchema: Option[String],
    specificName: Option[String],
    groupName: Option[String],
    transformType: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(udtCatalog, udtSchema, udtName, specificCatalog, specificSchema, specificName, groupName, transformType)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"udt_catalog, udt_schema, udt_name, specific_catalog, specific_schema, specific_name, group_name, transform_type"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".udt_catalog, " ++ Fragment.const0(a) ++ fr".udt_schema, " ++ Fragment.const0(a) ++ fr".udt_name, " ++ Fragment.const0(a) ++ fr".specific_catalog, " ++ Fragment.const0(a) ++ fr".specific_schema, " ++ Fragment.const0(a) ++ fr".specific_name, " ++ Fragment.const0(a) ++ fr".group_name, " ++ Fragment.const0(a) ++ fr".transform_type"
  }

  case class Shape(udtCatalog: Option[String] = None, udtSchema: Option[String] = None, udtName: Option[String] = None, specificCatalog: Option[String] = None, specificSchema: Option[String] = None, specificName: Option[String] = None, groupName: Option[String] = None, transformType: Option[String] = None)

  object Shape {
    def NoDefaults(udtCatalog: Option[String], udtSchema: Option[String], udtName: Option[String], specificCatalog: Option[String], specificSchema: Option[String], specificName: Option[String], groupName: Option[String], transformType: Option[String]): Shape = Shape(udtCatalog, udtSchema, udtName, specificCatalog, specificSchema, specificName, groupName, transformType)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2),
        (row) => (row.udtCatalog, (row.udtSchema, (row.udtName, (row.specificCatalog, (row.specificSchema, (row.specificName, (row.groupName, (row.transformType))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2),
        (row) => (row.udtCatalog, (row.udtSchema, (row.udtName, (row.specificCatalog, (row.specificSchema, (row.specificName, (row.groupName, (row.transformType))))))))
      )
    }

}
trait Transforms {
  import Transforms._

  def create(udtCatalog: Option[String] = None, udtSchema: Option[String] = None, udtName: Option[String] = None, specificCatalog: Option[String] = None, specificSchema: Option[String] = None, specificName: Option[String] = None, groupName: Option[String] = None, transformType: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(udtCatalog, udtSchema, udtName, specificCatalog, specificSchema, specificName, groupName, transformType))
  }

  def createVoid(udtCatalog: Option[String] = None, udtSchema: Option[String] = None, udtName: Option[String] = None, specificCatalog: Option[String] = None, specificSchema: Option[String] = None, specificName: Option[String] = None, groupName: Option[String] = None, transformType: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(udtCatalog, udtSchema, udtName, specificCatalog, specificSchema, specificName, groupName, transformType))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.Transforms.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.transforms (udt_catalog, udt_schema, udt_name, specific_catalog, specific_schema, specific_name, group_name, transform_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.Transforms.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("udt_catalog", "udt_schema", "udt_name", "specific_catalog", "specific_schema", "specific_name", "group_name", "transform_type")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.Transforms.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.Transforms.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.Transforms.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.Transforms.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.transforms
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
      FROM information_schema.transforms
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
