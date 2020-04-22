package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgCursors extends PgCursors {

  case class Row(
    name: Option[String],
    statement: Option[String],
    isHoldable: Option[Boolean],
    isBinary: Option[Boolean],
    isScrollable: Option[Boolean],
    creationTime: Option[Timestamp]
  ) {
    def toShape: Shape = Shape.NoDefaults(name, statement, isHoldable, isBinary, isScrollable, creationTime)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"name, statement, is_holdable, is_binary, is_scrollable, creation_time"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".name, " ++ Fragment.const0(a) ++ fr".statement, " ++ Fragment.const0(a) ++ fr".is_holdable, " ++ Fragment.const0(a) ++ fr".is_binary, " ++ Fragment.const0(a) ++ fr".is_scrollable, " ++ Fragment.const0(a) ++ fr".creation_time"
  }

  case class Shape(name: Option[String] = None, statement: Option[String] = None, isHoldable: Option[Boolean] = None, isBinary: Option[Boolean] = None, isScrollable: Option[Boolean] = None, creationTime: Option[Timestamp] = None)

  object Shape {
    def NoDefaults(name: Option[String], statement: Option[String], isHoldable: Option[Boolean], isBinary: Option[Boolean], isScrollable: Option[Boolean], creationTime: Option[Timestamp]): Shape = Shape(name, statement, isHoldable, isBinary, isScrollable, creationTime)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2),
        (row) => (row.name, (row.statement, (row.isHoldable, (row.isBinary, (row.isScrollable, (row.creationTime))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2),
        (row) => (row.name, (row.statement, (row.isHoldable, (row.isBinary, (row.isScrollable, (row.creationTime))))))
      )
    }

}
trait PgCursors {
  import PgCursors._

  def create(name: Option[String] = None, statement: Option[String] = None, isHoldable: Option[Boolean] = None, isBinary: Option[Boolean] = None, isScrollable: Option[Boolean] = None, creationTime: Option[Timestamp] = None): ConnectionIO[Row] = {
    create(Shape(name, statement, isHoldable, isBinary, isScrollable, creationTime))
  }

  def createVoid(name: Option[String] = None, statement: Option[String] = None, isHoldable: Option[Boolean] = None, isBinary: Option[Boolean] = None, isScrollable: Option[Boolean] = None, creationTime: Option[Timestamp] = None): ConnectionIO[Unit] = {
    createVoid(Shape(name, statement, isHoldable, isBinary, isScrollable, creationTime))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgCursors.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_cursors (name, statement, is_holdable, is_binary, is_scrollable, creation_time) VALUES (?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgCursors.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("name", "statement", "is_holdable", "is_binary", "is_scrollable", "creation_time")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgCursors.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgCursors.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgCursors.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgCursors.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_cursors
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
      FROM pg_catalog.pg_cursors
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
