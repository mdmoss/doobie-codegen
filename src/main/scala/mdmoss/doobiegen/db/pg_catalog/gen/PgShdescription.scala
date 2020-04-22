package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgShdescription extends PgShdescription {

  case class Row(
    description: String
  ) {
    def toShape: Shape = Shape.NoDefaults(description)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"description"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".description"
  }

  case class Shape(description: String)

  object Shape {
    def NoDefaults(description: String): Shape = Shape(description)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.StringMeta)

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t),
        (row) => (row.description)
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.StringMeta)

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t),
        (row) => (row.description)
      )
    }

}
trait PgShdescription {
  import PgShdescription._

  def create(description: String): ConnectionIO[Row] = {
    create(Shape(description))
  }

  def createVoid(description: String): ConnectionIO[Unit] = {
    createVoid(Shape(description))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgShdescription.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_shdescription (description) VALUES (?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgShdescription.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("description")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgShdescription.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgShdescription.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgShdescription.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgShdescription.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_shdescription
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
      FROM pg_catalog.pg_shdescription
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
