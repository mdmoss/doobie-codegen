package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object GeometryColumns extends GeometryColumns {

  case class Row(
    fTableCatalog: Option[String],
    coordDimension: Option[Int],
    srid: Option[Int],
    `type`: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(fTableCatalog, coordDimension, srid, `type`)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"f_table_catalog, coord_dimension, srid, type"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".f_table_catalog, " ++ Fragment.const0(a) ++ fr".coord_dimension, " ++ Fragment.const0(a) ++ fr".srid, " ++ Fragment.const0(a) ++ fr".type"
  }

  case class Shape(fTableCatalog: Option[String] = None, coordDimension: Option[Int] = None, srid: Option[Int] = None, `type`: Option[String] = None)

  object Shape {
    def NoDefaults(fTableCatalog: Option[String], coordDimension: Option[Int], srid: Option[Int], `type`: Option[String]): Shape = Shape(fTableCatalog, coordDimension, srid, `type`)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.fTableCatalog, (row.coordDimension, (row.srid, (row.`type`))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.fTableCatalog, (row.coordDimension, (row.srid, (row.`type`))))
      )
    }

}
trait GeometryColumns {
  import GeometryColumns._

  def create(fTableCatalog: Option[String] = None, coordDimension: Option[Int] = None, srid: Option[Int] = None, `type`: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(fTableCatalog, coordDimension, srid, `type`))
  }

  def createVoid(fTableCatalog: Option[String] = None, coordDimension: Option[Int] = None, srid: Option[Int] = None, `type`: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(fTableCatalog, coordDimension, srid, `type`))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.GeometryColumns.Shape]): Update[Shape] = {
    val sql = "INSERT INTO geometry_columns (f_table_catalog, coord_dimension, srid, type) VALUES (?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.GeometryColumns.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("f_table_catalog", "coord_dimension", "srid", "type")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.GeometryColumns.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.GeometryColumns.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.GeometryColumns.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.GeometryColumns.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM geometry_columns
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
      FROM geometry_columns
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
