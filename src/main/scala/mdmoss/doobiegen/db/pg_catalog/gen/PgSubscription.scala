package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgSubscription extends PgSubscription {

  case class Row(
    subenabled: Boolean,
    subconninfo: String,
    subsynccommit: String
  ) {
    def toShape: Shape = Shape.NoDefaults(subenabled, subconninfo, subsynccommit)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"subenabled, subconninfo, subsynccommit"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".subenabled, " ++ Fragment.const0(a) ++ fr".subconninfo, " ++ Fragment.const0(a) ++ fr".subsynccommit"
  }

  case class Shape(subenabled: Boolean, subconninfo: String, subsynccommit: String)

  object Shape {
    def NoDefaults(subenabled: Boolean, subconninfo: String, subsynccommit: String): Shape = Shape(subenabled, subconninfo, subsynccommit)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta)))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.subenabled, (row.subconninfo, (row.subsynccommit)))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta)))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.subenabled, (row.subconninfo, (row.subsynccommit)))
      )
    }

}
trait PgSubscription {
  import PgSubscription._

  def create(subenabled: Boolean, subconninfo: String, subsynccommit: String): ConnectionIO[Row] = {
    create(Shape(subenabled, subconninfo, subsynccommit))
  }

  def createVoid(subenabled: Boolean, subconninfo: String, subsynccommit: String): ConnectionIO[Unit] = {
    createVoid(Shape(subenabled, subconninfo, subsynccommit))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSubscription.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_subscription (subenabled, subconninfo, subsynccommit) VALUES (?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSubscription.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("subenabled", "subconninfo", "subsynccommit")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSubscription.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSubscription.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgSubscription.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgSubscription.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_subscription
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
      FROM pg_catalog.pg_subscription
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
