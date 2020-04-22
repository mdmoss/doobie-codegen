package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object CheckConstraints extends CheckConstraints {

  case class Row(
    constraintCatalog: Option[String],
    constraintSchema: Option[String],
    constraintName: Option[String],
    checkClause: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(constraintCatalog, constraintSchema, constraintName, checkClause)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"constraint_catalog, constraint_schema, constraint_name, check_clause"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".constraint_catalog, " ++ Fragment.const0(a) ++ fr".constraint_schema, " ++ Fragment.const0(a) ++ fr".constraint_name, " ++ Fragment.const0(a) ++ fr".check_clause"
  }

  case class Shape(constraintCatalog: Option[String] = None, constraintSchema: Option[String] = None, constraintName: Option[String] = None, checkClause: Option[String] = None)

  object Shape {
    def NoDefaults(constraintCatalog: Option[String], constraintSchema: Option[String], constraintName: Option[String], checkClause: Option[String]): Shape = Shape(constraintCatalog, constraintSchema, constraintName, checkClause)
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
        (row) => (row.constraintCatalog, (row.constraintSchema, (row.constraintName, (row.checkClause))))
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
        (row) => (row.constraintCatalog, (row.constraintSchema, (row.constraintName, (row.checkClause))))
      )
    }

}
trait CheckConstraints {
  import CheckConstraints._

  def create(constraintCatalog: Option[String] = None, constraintSchema: Option[String] = None, constraintName: Option[String] = None, checkClause: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(constraintCatalog, constraintSchema, constraintName, checkClause))
  }

  def createVoid(constraintCatalog: Option[String] = None, constraintSchema: Option[String] = None, constraintName: Option[String] = None, checkClause: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(constraintCatalog, constraintSchema, constraintName, checkClause))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.CheckConstraints.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.check_constraints (constraint_catalog, constraint_schema, constraint_name, check_clause) VALUES (?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.CheckConstraints.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("constraint_catalog", "constraint_schema", "constraint_name", "check_clause")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.CheckConstraints.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.CheckConstraints.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.CheckConstraints.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.CheckConstraints.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.check_constraints
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
      FROM information_schema.check_constraints
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
