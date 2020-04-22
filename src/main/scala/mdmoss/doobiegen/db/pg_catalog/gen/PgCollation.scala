package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgCollation extends PgCollation {

  case class Row(
    collencoding: Int,
    collversion: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(collencoding, collversion)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"collencoding, collversion"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".collencoding, " ++ Fragment.const0(a) ++ fr".collversion"
  }

  case class Shape(collencoding: Int, collversion: Option[String] = None)

  object Shape {
    def NoDefaults(collencoding: Int, collversion: Option[String]): Shape = Shape(collencoding, collversion)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.collencoding, (row.collversion))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.collencoding, (row.collversion))
      )
    }

}
trait PgCollation {
  import PgCollation._

  def create(collencoding: Int, collversion: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(collencoding, collversion))
  }

  def createVoid(collencoding: Int, collversion: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(collencoding, collversion))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgCollation.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_collation (collencoding, collversion) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgCollation.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("collencoding", "collversion")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgCollation.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgCollation.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgCollation.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgCollation.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_collation
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
      FROM pg_catalog.pg_collation
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
