package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgTables extends PgTables {

  case class Row(
    hasindexes: Option[Boolean],
    hasrules: Option[Boolean],
    hastriggers: Option[Boolean],
    rowsecurity: Option[Boolean]
  ) {
    def toShape: Shape = Shape.NoDefaults(hasindexes, hasrules, hastriggers, rowsecurity)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"hasindexes, hasrules, hastriggers, rowsecurity"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".hasindexes, " ++ Fragment.const0(a) ++ fr".hasrules, " ++ Fragment.const0(a) ++ fr".hastriggers, " ++ Fragment.const0(a) ++ fr".rowsecurity"
  }

  case class Shape(hasindexes: Option[Boolean] = None, hasrules: Option[Boolean] = None, hastriggers: Option[Boolean] = None, rowsecurity: Option[Boolean] = None)

  object Shape {
    def NoDefaults(hasindexes: Option[Boolean], hasrules: Option[Boolean], hastriggers: Option[Boolean], rowsecurity: Option[Boolean]): Shape = Shape(hasindexes, hasrules, hastriggers, rowsecurity)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.hasindexes, (row.hasrules, (row.hastriggers, (row.rowsecurity))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.hasindexes, (row.hasrules, (row.hastriggers, (row.rowsecurity))))
      )
    }

}
trait PgTables {
  import PgTables._

  def create(hasindexes: Option[Boolean] = None, hasrules: Option[Boolean] = None, hastriggers: Option[Boolean] = None, rowsecurity: Option[Boolean] = None): ConnectionIO[Row] = {
    create(Shape(hasindexes, hasrules, hastriggers, rowsecurity))
  }

  def createVoid(hasindexes: Option[Boolean] = None, hasrules: Option[Boolean] = None, hastriggers: Option[Boolean] = None, rowsecurity: Option[Boolean] = None): ConnectionIO[Unit] = {
    createVoid(Shape(hasindexes, hasrules, hastriggers, rowsecurity))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTables.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_tables (hasindexes, hasrules, hastriggers, rowsecurity) VALUES (?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTables.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("hasindexes", "hasrules", "hastriggers", "rowsecurity")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTables.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTables.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgTables.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgTables.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_tables
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
      FROM pg_catalog.pg_tables
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
