package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgSeclabel extends PgSeclabel {

  case class Row(
    objsubid: Int,
    provider: String,
    label: String
  ) {
    def toShape: Shape = Shape.NoDefaults(objsubid, provider, label)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"objsubid, provider, label"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".objsubid, " ++ Fragment.const0(a) ++ fr".provider, " ++ Fragment.const0(a) ++ fr".label"
  }

  case class Shape(objsubid: Int, provider: String, label: String)

  object Shape {
    def NoDefaults(objsubid: Int, provider: String, label: String): Shape = Shape(objsubid, provider, label)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta)))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.objsubid, (row.provider, (row.label)))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta)))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.objsubid, (row.provider, (row.label)))
      )
    }

}
trait PgSeclabel {
  import PgSeclabel._

  def create(objsubid: Int, provider: String, label: String): ConnectionIO[Row] = {
    create(Shape(objsubid, provider, label))
  }

  def createVoid(objsubid: Int, provider: String, label: String): ConnectionIO[Unit] = {
    createVoid(Shape(objsubid, provider, label))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSeclabel.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_seclabel (objsubid, provider, label) VALUES (?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSeclabel.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("objsubid", "provider", "label")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSeclabel.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSeclabel.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgSeclabel.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgSeclabel.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_seclabel
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
      FROM pg_catalog.pg_seclabel
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
