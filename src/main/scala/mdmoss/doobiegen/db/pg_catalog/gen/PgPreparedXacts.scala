package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgPreparedXacts extends PgPreparedXacts {

  case class Row(
    gid: Option[String],
    prepared: Option[Timestamp]
  ) {
    def toShape: Shape = Shape.NoDefaults(gid, prepared)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"gid, prepared"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".gid, " ++ Fragment.const0(a) ++ fr".prepared"
  }

  case class Shape(gid: Option[String] = None, prepared: Option[Timestamp] = None)

  object Shape {
    def NoDefaults(gid: Option[String], prepared: Option[Timestamp]): Shape = Shape(gid, prepared)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.gid, (row.prepared))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.gid, (row.prepared))
      )
    }

}
trait PgPreparedXacts {
  import PgPreparedXacts._

  def create(gid: Option[String] = None, prepared: Option[Timestamp] = None): ConnectionIO[Row] = {
    create(Shape(gid, prepared))
  }

  def createVoid(gid: Option[String] = None, prepared: Option[Timestamp] = None): ConnectionIO[Unit] = {
    createVoid(Shape(gid, prepared))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPreparedXacts.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_prepared_xacts (gid, prepared) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPreparedXacts.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("gid", "prepared")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPreparedXacts.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPreparedXacts.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgPreparedXacts.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgPreparedXacts.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_prepared_xacts
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
      FROM pg_catalog.pg_prepared_xacts
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
