package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object DomainUdtUsage extends DomainUdtUsage {

  case class Row(
    udtCatalog: Option[String],
    udtSchema: Option[String],
    udtName: Option[String],
    domainCatalog: Option[String],
    domainSchema: Option[String],
    domainName: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(udtCatalog, udtSchema, udtName, domainCatalog, domainSchema, domainName)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"udt_catalog, udt_schema, udt_name, domain_catalog, domain_schema, domain_name"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".udt_catalog, " ++ Fragment.const0(a) ++ fr".udt_schema, " ++ Fragment.const0(a) ++ fr".udt_name, " ++ Fragment.const0(a) ++ fr".domain_catalog, " ++ Fragment.const0(a) ++ fr".domain_schema, " ++ Fragment.const0(a) ++ fr".domain_name"
  }

  case class Shape(udtCatalog: Option[String] = None, udtSchema: Option[String] = None, udtName: Option[String] = None, domainCatalog: Option[String] = None, domainSchema: Option[String] = None, domainName: Option[String] = None)

  object Shape {
    def NoDefaults(udtCatalog: Option[String], udtSchema: Option[String], udtName: Option[String], domainCatalog: Option[String], domainSchema: Option[String], domainName: Option[String]): Shape = Shape(udtCatalog, udtSchema, udtName, domainCatalog, domainSchema, domainName)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2),
        (row) => (row.udtCatalog, (row.udtSchema, (row.udtName, (row.domainCatalog, (row.domainSchema, (row.domainName))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2),
        (row) => (row.udtCatalog, (row.udtSchema, (row.udtName, (row.domainCatalog, (row.domainSchema, (row.domainName))))))
      )
    }

}
trait DomainUdtUsage {
  import DomainUdtUsage._

  def create(udtCatalog: Option[String] = None, udtSchema: Option[String] = None, udtName: Option[String] = None, domainCatalog: Option[String] = None, domainSchema: Option[String] = None, domainName: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(udtCatalog, udtSchema, udtName, domainCatalog, domainSchema, domainName))
  }

  def createVoid(udtCatalog: Option[String] = None, udtSchema: Option[String] = None, udtName: Option[String] = None, domainCatalog: Option[String] = None, domainSchema: Option[String] = None, domainName: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(udtCatalog, udtSchema, udtName, domainCatalog, domainSchema, domainName))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.DomainUdtUsage.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.domain_udt_usage (udt_catalog, udt_schema, udt_name, domain_catalog, domain_schema, domain_name) VALUES (?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.DomainUdtUsage.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("udt_catalog", "udt_schema", "udt_name", "domain_catalog", "domain_schema", "domain_name")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.DomainUdtUsage.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.DomainUdtUsage.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.DomainUdtUsage.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.DomainUdtUsage.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.domain_udt_usage
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
      FROM information_schema.domain_udt_usage
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
