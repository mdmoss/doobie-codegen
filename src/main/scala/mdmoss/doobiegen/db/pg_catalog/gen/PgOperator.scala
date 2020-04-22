package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgOperator extends PgOperator {

  case class Row(
    oprcanmerge: Boolean,
    oprcanhash: Boolean
  ) {
    def toShape: Shape = Shape.NoDefaults(oprcanmerge, oprcanhash)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"oprcanmerge, oprcanhash"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".oprcanmerge, " ++ Fragment.const0(a) ++ fr".oprcanhash"
  }

  case class Shape(oprcanmerge: Boolean, oprcanhash: Boolean)

  object Shape {
    def NoDefaults(oprcanmerge: Boolean, oprcanhash: Boolean): Shape = Shape(oprcanmerge, oprcanhash)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.oprcanmerge, (row.oprcanhash))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.oprcanmerge, (row.oprcanhash))
      )
    }

}
trait PgOperator {
  import PgOperator._

  def create(oprcanmerge: Boolean, oprcanhash: Boolean): ConnectionIO[Row] = {
    create(Shape(oprcanmerge, oprcanhash))
  }

  def createVoid(oprcanmerge: Boolean, oprcanhash: Boolean): ConnectionIO[Unit] = {
    createVoid(Shape(oprcanmerge, oprcanhash))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgOperator.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_operator (oprcanmerge, oprcanhash) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgOperator.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("oprcanmerge", "oprcanhash")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgOperator.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgOperator.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgOperator.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgOperator.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_operator
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
      FROM pg_catalog.pg_operator
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
