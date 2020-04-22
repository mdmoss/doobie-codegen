package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgConversion extends PgConversion {

  case class Row(
    conforencoding: Int,
    contoencoding: Int,
    condefault: Boolean
  ) {
    def toShape: Shape = Shape.NoDefaults(conforencoding, contoencoding, condefault)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"conforencoding, contoencoding, condefault"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".conforencoding, " ++ Fragment.const0(a) ++ fr".contoencoding, " ++ Fragment.const0(a) ++ fr".condefault"
  }

  case class Shape(conforencoding: Int, contoencoding: Int, condefault: Boolean)

  object Shape {
    def NoDefaults(conforencoding: Int, contoencoding: Int, condefault: Boolean): Shape = Shape(conforencoding, contoencoding, condefault)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.conforencoding, (row.contoencoding, (row.condefault)))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.conforencoding, (row.contoencoding, (row.condefault)))
      )
    }

}
trait PgConversion {
  import PgConversion._

  def create(conforencoding: Int, contoencoding: Int, condefault: Boolean): ConnectionIO[Row] = {
    create(Shape(conforencoding, contoencoding, condefault))
  }

  def createVoid(conforencoding: Int, contoencoding: Int, condefault: Boolean): ConnectionIO[Unit] = {
    createVoid(Shape(conforencoding, contoencoding, condefault))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgConversion.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_conversion (conforencoding, contoencoding, condefault) VALUES (?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgConversion.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("conforencoding", "contoencoding", "condefault")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgConversion.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgConversion.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgConversion.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgConversion.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_conversion
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
      FROM pg_catalog.pg_conversion
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
