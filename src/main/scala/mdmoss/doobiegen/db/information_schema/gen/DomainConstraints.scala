package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object DomainConstraints extends DomainConstraints {

  case class Row(
    constraintCatalog: Option[String],
    constraintSchema: Option[String],
    constraintName: Option[String],
    domainCatalog: Option[String],
    domainSchema: Option[String],
    domainName: Option[String],
    isDeferrable: Option[String],
    initiallyDeferred: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(constraintCatalog, constraintSchema, constraintName, domainCatalog, domainSchema, domainName, isDeferrable, initiallyDeferred)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"constraint_catalog, constraint_schema, constraint_name, domain_catalog, domain_schema, domain_name, is_deferrable, initially_deferred"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".constraint_catalog, " ++ Fragment.const0(a) ++ fr".constraint_schema, " ++ Fragment.const0(a) ++ fr".constraint_name, " ++ Fragment.const0(a) ++ fr".domain_catalog, " ++ Fragment.const0(a) ++ fr".domain_schema, " ++ Fragment.const0(a) ++ fr".domain_name, " ++ Fragment.const0(a) ++ fr".is_deferrable, " ++ Fragment.const0(a) ++ fr".initially_deferred"
  }

  case class Shape(constraintCatalog: Option[String] = None, constraintSchema: Option[String] = None, constraintName: Option[String] = None, domainCatalog: Option[String] = None, domainSchema: Option[String] = None, domainName: Option[String] = None, isDeferrable: Option[String] = None, initiallyDeferred: Option[String] = None)

  object Shape {
    def NoDefaults(constraintCatalog: Option[String], constraintSchema: Option[String], constraintName: Option[String], domainCatalog: Option[String], domainSchema: Option[String], domainName: Option[String], isDeferrable: Option[String], initiallyDeferred: Option[String]): Shape = Shape(constraintCatalog, constraintSchema, constraintName, domainCatalog, domainSchema, domainName, isDeferrable, initiallyDeferred)
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
        (row) => (row.constraintCatalog, (row.constraintSchema, (row.constraintName, (row.domainCatalog, (row.domainSchema, (row.domainName, (row.isDeferrable, (row.initiallyDeferred))))))))
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
        (row) => (row.constraintCatalog, (row.constraintSchema, (row.constraintName, (row.domainCatalog, (row.domainSchema, (row.domainName, (row.isDeferrable, (row.initiallyDeferred))))))))
      )
    }

}
trait DomainConstraints {
  import DomainConstraints._

  def create(constraintCatalog: Option[String] = None, constraintSchema: Option[String] = None, constraintName: Option[String] = None, domainCatalog: Option[String] = None, domainSchema: Option[String] = None, domainName: Option[String] = None, isDeferrable: Option[String] = None, initiallyDeferred: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(constraintCatalog, constraintSchema, constraintName, domainCatalog, domainSchema, domainName, isDeferrable, initiallyDeferred))
  }

  def createVoid(constraintCatalog: Option[String] = None, constraintSchema: Option[String] = None, constraintName: Option[String] = None, domainCatalog: Option[String] = None, domainSchema: Option[String] = None, domainName: Option[String] = None, isDeferrable: Option[String] = None, initiallyDeferred: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(constraintCatalog, constraintSchema, constraintName, domainCatalog, domainSchema, domainName, isDeferrable, initiallyDeferred))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.DomainConstraints.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.domain_constraints (constraint_catalog, constraint_schema, constraint_name, domain_catalog, domain_schema, domain_name, is_deferrable, initially_deferred) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.DomainConstraints.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("constraint_catalog", "constraint_schema", "constraint_name", "domain_catalog", "domain_schema", "domain_name", "is_deferrable", "initially_deferred")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.DomainConstraints.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.DomainConstraints.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.DomainConstraints.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.DomainConstraints.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.domain_constraints
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
      FROM information_schema.domain_constraints
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
