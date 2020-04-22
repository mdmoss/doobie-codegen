package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgInherits extends PgInherits {

  case class Row(
    inhseqno: Int
  ) {
    def toShape: Shape = Shape.NoDefaults(inhseqno)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"inhseqno"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".inhseqno"
  }

  case class Shape(inhseqno: Int)

  object Shape {
    def NoDefaults(inhseqno: Int): Shape = Shape(inhseqno)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t),
        (row) => (row.inhseqno)
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t),
        (row) => (row.inhseqno)
      )
    }

}
trait PgInherits {
  import PgInherits._

  def create(inhseqno: Int): ConnectionIO[Row] = {
    create(Shape(inhseqno))
  }

  def createVoid(inhseqno: Int): ConnectionIO[Unit] = {
    createVoid(Shape(inhseqno))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgInherits.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_inherits (inhseqno) VALUES (?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgInherits.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("inhseqno")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgInherits.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgInherits.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgInherits.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgInherits.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_inherits
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
      FROM pg_catalog.pg_inherits
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
