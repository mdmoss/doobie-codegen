package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgConstraint extends PgConstraint {

  case class Row(
    condeferrable: Boolean,
    condeferred: Boolean,
    convalidated: Boolean,
    conislocal: Boolean,
    coninhcount: Int,
    connoinherit: Boolean,
    consrc: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(condeferrable, condeferred, convalidated, conislocal, coninhcount, connoinherit, consrc)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"condeferrable, condeferred, convalidated, conislocal, coninhcount, connoinherit, consrc"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".condeferrable, " ++ Fragment.const0(a) ++ fr".condeferred, " ++ Fragment.const0(a) ++ fr".convalidated, " ++ Fragment.const0(a) ++ fr".conislocal, " ++ Fragment.const0(a) ++ fr".coninhcount, " ++ Fragment.const0(a) ++ fr".connoinherit, " ++ Fragment.const0(a) ++ fr".consrc"
  }

  case class Shape(condeferrable: Boolean, condeferred: Boolean, convalidated: Boolean, conislocal: Boolean, coninhcount: Int, connoinherit: Boolean, consrc: Option[String] = None)

  object Shape {
    def NoDefaults(condeferrable: Boolean, condeferred: Boolean, convalidated: Boolean, conislocal: Boolean, coninhcount: Int, connoinherit: Boolean, consrc: Option[String]): Shape = Shape(condeferrable, condeferred, convalidated, conislocal, coninhcount, connoinherit, consrc)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
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
        (row) => (row.condeferrable, (row.condeferred, (row.convalidated, (row.conislocal, (row.coninhcount, (row.connoinherit, (row.consrc)))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
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
        (row) => (row.condeferrable, (row.condeferred, (row.convalidated, (row.conislocal, (row.coninhcount, (row.connoinherit, (row.consrc)))))))
      )
    }

}
trait PgConstraint {
  import PgConstraint._

  def create(condeferrable: Boolean, condeferred: Boolean, convalidated: Boolean, conislocal: Boolean, coninhcount: Int, connoinherit: Boolean, consrc: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(condeferrable, condeferred, convalidated, conislocal, coninhcount, connoinherit, consrc))
  }

  def createVoid(condeferrable: Boolean, condeferred: Boolean, convalidated: Boolean, conislocal: Boolean, coninhcount: Int, connoinherit: Boolean, consrc: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(condeferrable, condeferred, convalidated, conislocal, coninhcount, connoinherit, consrc))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgConstraint.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_constraint (condeferrable, condeferred, convalidated, conislocal, coninhcount, connoinherit, consrc) VALUES (?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgConstraint.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("condeferrable", "condeferred", "convalidated", "conislocal", "coninhcount", "connoinherit", "consrc")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgConstraint.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgConstraint.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgConstraint.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgConstraint.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_constraint
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
      FROM pg_catalog.pg_constraint
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
