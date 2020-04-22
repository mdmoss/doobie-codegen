package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgPreparedStatements extends PgPreparedStatements {

  case class Row(
    name: Option[String],
    statement: Option[String],
    prepareTime: Option[Timestamp],
    fromSql: Option[Boolean]
  ) {
    def toShape: Shape = Shape.NoDefaults(name, statement, prepareTime, fromSql)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"name, statement, prepare_time, from_sql"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".name, " ++ Fragment.const0(a) ++ fr".statement, " ++ Fragment.const0(a) ++ fr".prepare_time, " ++ Fragment.const0(a) ++ fr".from_sql"
  }

  case class Shape(name: Option[String] = None, statement: Option[String] = None, prepareTime: Option[Timestamp] = None, fromSql: Option[Boolean] = None)

  object Shape {
    def NoDefaults(name: Option[String], statement: Option[String], prepareTime: Option[Timestamp], fromSql: Option[Boolean]): Shape = Shape(name, statement, prepareTime, fromSql)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.name, (row.statement, (row.prepareTime, (row.fromSql))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.name, (row.statement, (row.prepareTime, (row.fromSql))))
      )
    }

}
trait PgPreparedStatements {
  import PgPreparedStatements._

  def create(name: Option[String] = None, statement: Option[String] = None, prepareTime: Option[Timestamp] = None, fromSql: Option[Boolean] = None): ConnectionIO[Row] = {
    create(Shape(name, statement, prepareTime, fromSql))
  }

  def createVoid(name: Option[String] = None, statement: Option[String] = None, prepareTime: Option[Timestamp] = None, fromSql: Option[Boolean] = None): ConnectionIO[Unit] = {
    createVoid(Shape(name, statement, prepareTime, fromSql))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPreparedStatements.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_prepared_statements (name, statement, prepare_time, from_sql) VALUES (?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPreparedStatements.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("name", "statement", "prepare_time", "from_sql")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPreparedStatements.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPreparedStatements.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgPreparedStatements.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgPreparedStatements.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_prepared_statements
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
      FROM pg_catalog.pg_prepared_statements
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
