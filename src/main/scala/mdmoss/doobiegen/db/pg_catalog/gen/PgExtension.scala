package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgExtension extends PgExtension {

  case class Row(
    extrelocatable: Boolean,
    extversion: String
  ) {
    def toShape: Shape = Shape.NoDefaults(extrelocatable, extversion)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"extrelocatable, extversion"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".extrelocatable, " ++ Fragment.const0(a) ++ fr".extversion"
  }

  case class Shape(extrelocatable: Boolean, extversion: String)

  object Shape {
    def NoDefaults(extrelocatable: Boolean, extversion: String): Shape = Shape(extrelocatable, extversion)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.extrelocatable, (row.extversion))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.extrelocatable, (row.extversion))
      )
    }

}
trait PgExtension {
  import PgExtension._

  def create(extrelocatable: Boolean, extversion: String): ConnectionIO[Row] = {
    create(Shape(extrelocatable, extversion))
  }

  def createVoid(extrelocatable: Boolean, extversion: String): ConnectionIO[Unit] = {
    createVoid(Shape(extrelocatable, extversion))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgExtension.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_extension (extrelocatable, extversion) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgExtension.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("extrelocatable", "extversion")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgExtension.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgExtension.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgExtension.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgExtension.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_extension
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
      FROM pg_catalog.pg_extension
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
