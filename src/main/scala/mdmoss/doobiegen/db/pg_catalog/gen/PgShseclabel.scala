package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgShseclabel extends PgShseclabel {

  case class Row(
    provider: String,
    label: String
  ) {
    def toShape: Shape = Shape.NoDefaults(provider, label)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"provider, label"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".provider, " ++ Fragment.const0(a) ++ fr".label"
  }

  case class Shape(provider: String, label: String)

  object Shape {
    def NoDefaults(provider: String, label: String): Shape = Shape(provider, label)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.provider, (row.label))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.provider, (row.label))
      )
    }

}
trait PgShseclabel {
  import PgShseclabel._

  def create(provider: String, label: String): ConnectionIO[Row] = {
    create(Shape(provider, label))
  }

  def createVoid(provider: String, label: String): ConnectionIO[Unit] = {
    createVoid(Shape(provider, label))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgShseclabel.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_shseclabel (provider, label) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgShseclabel.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("provider", "label")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgShseclabel.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgShseclabel.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgShseclabel.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgShseclabel.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_shseclabel
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
      FROM pg_catalog.pg_shseclabel
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
