package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object InformationSchemaCatalogName extends InformationSchemaCatalogName {

  case class Row(
    catalogName: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(catalogName)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"catalog_name"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".catalog_name"
  }

  case class Shape(catalogName: Option[String] = None)

  object Shape {
    def NoDefaults(catalogName: Option[String]): Shape = Shape(catalogName)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t),
        (row) => (row.catalogName)
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t),
        (row) => (row.catalogName)
      )
    }

}
trait InformationSchemaCatalogName {
  import InformationSchemaCatalogName._

  def create(catalogName: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(catalogName))
  }

  def createVoid(catalogName: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(catalogName))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.InformationSchemaCatalogName.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.information_schema_catalog_name (catalog_name) VALUES (?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.InformationSchemaCatalogName.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("catalog_name")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.InformationSchemaCatalogName.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.InformationSchemaCatalogName.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.InformationSchemaCatalogName.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.InformationSchemaCatalogName.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.information_schema_catalog_name
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
      FROM information_schema.information_schema_catalog_name
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
