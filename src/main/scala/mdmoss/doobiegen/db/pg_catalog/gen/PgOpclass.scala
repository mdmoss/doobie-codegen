package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgOpclass extends PgOpclass {

  case class Row(
    opcdefault: Boolean
  ) {
    def toShape: Shape = Shape.NoDefaults(opcdefault)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"opcdefault"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".opcdefault"
  }

  case class Shape(opcdefault: Boolean)

  object Shape {
    def NoDefaults(opcdefault: Boolean): Shape = Shape(opcdefault)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t),
        (row) => (row.opcdefault)
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t),
        (row) => (row.opcdefault)
      )
    }

}
trait PgOpclass {
  import PgOpclass._

  def create(opcdefault: Boolean): ConnectionIO[Row] = {
    create(Shape(opcdefault))
  }

  def createVoid(opcdefault: Boolean): ConnectionIO[Unit] = {
    createVoid(Shape(opcdefault))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgOpclass.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_opclass (opcdefault) VALUES (?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgOpclass.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("opcdefault")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgOpclass.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgOpclass.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgOpclass.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgOpclass.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_opclass
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
      FROM pg_catalog.pg_opclass
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
