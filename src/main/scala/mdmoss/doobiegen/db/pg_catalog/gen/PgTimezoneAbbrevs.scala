package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgTimezoneAbbrevs extends PgTimezoneAbbrevs {

  case class Row(
    abbrev: Option[String],
    utcOffset: Option[Int],
    isDst: Option[Boolean]
  ) {
    def toShape: Shape = Shape.NoDefaults(abbrev, utcOffset, isDst)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"abbrev, utc_offset, is_dst"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".abbrev, " ++ Fragment.const0(a) ++ fr".utc_offset, " ++ Fragment.const0(a) ++ fr".is_dst"
  }

  case class Shape(abbrev: Option[String] = None, utcOffset: Option[Int] = None, isDst: Option[Boolean] = None)

  object Shape {
    def NoDefaults(abbrev: Option[String], utcOffset: Option[Int], isDst: Option[Boolean]): Shape = Shape(abbrev, utcOffset, isDst)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.abbrev, (row.utcOffset, (row.isDst)))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.abbrev, (row.utcOffset, (row.isDst)))
      )
    }

}
trait PgTimezoneAbbrevs {
  import PgTimezoneAbbrevs._

  def create(abbrev: Option[String] = None, utcOffset: Option[Int] = None, isDst: Option[Boolean] = None): ConnectionIO[Row] = {
    create(Shape(abbrev, utcOffset, isDst))
  }

  def createVoid(abbrev: Option[String] = None, utcOffset: Option[Int] = None, isDst: Option[Boolean] = None): ConnectionIO[Unit] = {
    createVoid(Shape(abbrev, utcOffset, isDst))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTimezoneAbbrevs.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_timezone_abbrevs (abbrev, utc_offset, is_dst) VALUES (?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTimezoneAbbrevs.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("abbrev", "utc_offset", "is_dst")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTimezoneAbbrevs.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTimezoneAbbrevs.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgTimezoneAbbrevs.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgTimezoneAbbrevs.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_timezone_abbrevs
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
      FROM pg_catalog.pg_timezone_abbrevs
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
