package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object ReferentialConstraints extends ReferentialConstraints {

  case class Row(
    constraintCatalog: Option[String],
    constraintSchema: Option[String],
    constraintName: Option[String],
    uniqueConstraintCatalog: Option[String],
    uniqueConstraintSchema: Option[String],
    uniqueConstraintName: Option[String],
    matchOption: Option[String],
    updateRule: Option[String],
    deleteRule: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(constraintCatalog, constraintSchema, constraintName, uniqueConstraintCatalog, uniqueConstraintSchema, uniqueConstraintName, matchOption, updateRule, deleteRule)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"constraint_catalog, constraint_schema, constraint_name, unique_constraint_catalog, unique_constraint_schema, unique_constraint_name, match_option, update_rule, delete_rule"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".constraint_catalog, " ++ Fragment.const0(a) ++ fr".constraint_schema, " ++ Fragment.const0(a) ++ fr".constraint_name, " ++ Fragment.const0(a) ++ fr".unique_constraint_catalog, " ++ Fragment.const0(a) ++ fr".unique_constraint_schema, " ++ Fragment.const0(a) ++ fr".unique_constraint_name, " ++ Fragment.const0(a) ++ fr".match_option, " ++ Fragment.const0(a) ++ fr".update_rule, " ++ Fragment.const0(a) ++ fr".delete_rule"
  }

  case class Shape(constraintCatalog: Option[String] = None, constraintSchema: Option[String] = None, constraintName: Option[String] = None, uniqueConstraintCatalog: Option[String] = None, uniqueConstraintSchema: Option[String] = None, uniqueConstraintName: Option[String] = None, matchOption: Option[String] = None, updateRule: Option[String] = None, deleteRule: Option[String] = None)

  object Shape {
    def NoDefaults(constraintCatalog: Option[String], constraintSchema: Option[String], constraintName: Option[String], uniqueConstraintCatalog: Option[String], uniqueConstraintSchema: Option[String], uniqueConstraintName: Option[String], matchOption: Option[String], updateRule: Option[String], deleteRule: Option[String]): Shape = Shape(constraintCatalog, constraintSchema, constraintName, uniqueConstraintCatalog, uniqueConstraintSchema, uniqueConstraintName, matchOption, updateRule, deleteRule)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2),
        (row) => (row.constraintCatalog, (row.constraintSchema, (row.constraintName, (row.uniqueConstraintCatalog, (row.uniqueConstraintSchema, (row.uniqueConstraintName, (row.matchOption, (row.updateRule, (row.deleteRule)))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2),
        (row) => (row.constraintCatalog, (row.constraintSchema, (row.constraintName, (row.uniqueConstraintCatalog, (row.uniqueConstraintSchema, (row.uniqueConstraintName, (row.matchOption, (row.updateRule, (row.deleteRule)))))))))
      )
    }

}
trait ReferentialConstraints {
  import ReferentialConstraints._

  def create(constraintCatalog: Option[String] = None, constraintSchema: Option[String] = None, constraintName: Option[String] = None, uniqueConstraintCatalog: Option[String] = None, uniqueConstraintSchema: Option[String] = None, uniqueConstraintName: Option[String] = None, matchOption: Option[String] = None, updateRule: Option[String] = None, deleteRule: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(constraintCatalog, constraintSchema, constraintName, uniqueConstraintCatalog, uniqueConstraintSchema, uniqueConstraintName, matchOption, updateRule, deleteRule))
  }

  def createVoid(constraintCatalog: Option[String] = None, constraintSchema: Option[String] = None, constraintName: Option[String] = None, uniqueConstraintCatalog: Option[String] = None, uniqueConstraintSchema: Option[String] = None, uniqueConstraintName: Option[String] = None, matchOption: Option[String] = None, updateRule: Option[String] = None, deleteRule: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(constraintCatalog, constraintSchema, constraintName, uniqueConstraintCatalog, uniqueConstraintSchema, uniqueConstraintName, matchOption, updateRule, deleteRule))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.ReferentialConstraints.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.referential_constraints (constraint_catalog, constraint_schema, constraint_name, unique_constraint_catalog, unique_constraint_schema, unique_constraint_name, match_option, update_rule, delete_rule) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.ReferentialConstraints.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("constraint_catalog", "constraint_schema", "constraint_name", "unique_constraint_catalog", "unique_constraint_schema", "unique_constraint_name", "match_option", "update_rule", "delete_rule")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.ReferentialConstraints.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.ReferentialConstraints.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.ReferentialConstraints.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.ReferentialConstraints.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.referential_constraints
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
      FROM information_schema.referential_constraints
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
