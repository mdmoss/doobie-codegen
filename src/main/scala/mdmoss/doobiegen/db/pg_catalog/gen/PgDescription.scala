package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgDescription extends PgDescription {

  case class Row(
    objsubid: Int,
    description: String
  ) {
    def toShape: Shape = Shape.NoDefaults(objsubid, description)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"objsubid, description"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".objsubid, " ++ Fragment.const0(a) ++ fr".description"
  }

  case class Shape(objsubid: Int, description: String)

  object Shape {
    def NoDefaults(objsubid: Int, description: String): Shape = Shape(objsubid, description)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.objsubid, (row.description))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.objsubid, (row.description))
      )
    }

}
trait PgDescription {
  import PgDescription._

  def create(objsubid: Int, description: String): ConnectionIO[Row] = {
    create(Shape(objsubid, description))
  }

  def createVoid(objsubid: Int, description: String): ConnectionIO[Unit] = {
    createVoid(Shape(objsubid, description))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgDescription.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_description (objsubid, description) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgDescription.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("objsubid", "description")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgDescription.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgDescription.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgDescription.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgDescription.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_description
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
      FROM pg_catalog.pg_description
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
