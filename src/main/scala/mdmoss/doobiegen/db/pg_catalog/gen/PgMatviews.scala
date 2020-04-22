package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgMatviews extends PgMatviews {

  case class Row(
    hasindexes: Option[Boolean],
    ispopulated: Option[Boolean],
    definition: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(hasindexes, ispopulated, definition)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"hasindexes, ispopulated, definition"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".hasindexes, " ++ Fragment.const0(a) ++ fr".ispopulated, " ++ Fragment.const0(a) ++ fr".definition"
  }

  case class Shape(hasindexes: Option[Boolean] = None, ispopulated: Option[Boolean] = None, definition: Option[String] = None)

  object Shape {
    def NoDefaults(hasindexes: Option[Boolean], ispopulated: Option[Boolean], definition: Option[String]): Shape = Shape(hasindexes, ispopulated, definition)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.hasindexes, (row.ispopulated, (row.definition)))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.hasindexes, (row.ispopulated, (row.definition)))
      )
    }

}
trait PgMatviews {
  import PgMatviews._

  def create(hasindexes: Option[Boolean] = None, ispopulated: Option[Boolean] = None, definition: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(hasindexes, ispopulated, definition))
  }

  def createVoid(hasindexes: Option[Boolean] = None, ispopulated: Option[Boolean] = None, definition: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(hasindexes, ispopulated, definition))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgMatviews.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_matviews (hasindexes, ispopulated, definition) VALUES (?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgMatviews.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("hasindexes", "ispopulated", "definition")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgMatviews.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgMatviews.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgMatviews.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgMatviews.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_matviews
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
      FROM pg_catalog.pg_matviews
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
