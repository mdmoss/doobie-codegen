package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgPolicy extends PgPolicy {

  case class Row(
    polpermissive: Boolean
  ) {
    def toShape: Shape = Shape.NoDefaults(polpermissive)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"polpermissive"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".polpermissive"
  }

  case class Shape(polpermissive: Boolean)

  object Shape {
    def NoDefaults(polpermissive: Boolean): Shape = Shape(polpermissive)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t),
        (row) => (row.polpermissive)
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t),
        (row) => (row.polpermissive)
      )
    }

}
trait PgPolicy {
  import PgPolicy._

  def create(polpermissive: Boolean): ConnectionIO[Row] = {
    create(Shape(polpermissive))
  }

  def createVoid(polpermissive: Boolean): ConnectionIO[Unit] = {
    createVoid(Shape(polpermissive))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPolicy.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_policy (polpermissive) VALUES (?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPolicy.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("polpermissive")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPolicy.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPolicy.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgPolicy.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgPolicy.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_policy
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
      FROM pg_catalog.pg_policy
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
