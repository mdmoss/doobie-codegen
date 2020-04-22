package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStats extends PgStats {

  case class Row(
    inherited: Option[Boolean],
    avgWidth: Option[Int]
  ) {
    def toShape: Shape = Shape.NoDefaults(inherited, avgWidth)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"inherited, avg_width"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".inherited, " ++ Fragment.const0(a) ++ fr".avg_width"
  }

  case class Shape(inherited: Option[Boolean] = None, avgWidth: Option[Int] = None)

  object Shape {
    def NoDefaults(inherited: Option[Boolean], avgWidth: Option[Int]): Shape = Shape(inherited, avgWidth)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.inherited, (row.avgWidth))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.inherited, (row.avgWidth))
      )
    }

}
trait PgStats {
  import PgStats._

  def create(inherited: Option[Boolean] = None, avgWidth: Option[Int] = None): ConnectionIO[Row] = {
    create(Shape(inherited, avgWidth))
  }

  def createVoid(inherited: Option[Boolean] = None, avgWidth: Option[Int] = None): ConnectionIO[Unit] = {
    createVoid(Shape(inherited, avgWidth))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStats.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_stats (inherited, avg_width) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStats.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("inherited", "avg_width")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStats.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStats.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStats.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStats.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_stats
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
      FROM pg_catalog.pg_stats
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
