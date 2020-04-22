package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object ReferenceChange extends ReferenceChange {

  case class Row(
    referenceColumn: Long
  ) {
    def toShape: Shape = Shape.NoDefaults(referenceColumn)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"reference_column"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".reference_column"
  }

  case class Shape(referenceColumn: Long)

  object Shape {
    def NoDefaults(referenceColumn: Long): Shape = Shape(referenceColumn)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.LongMeta)

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t),
        (row) => (row.referenceColumn)
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.LongMeta)

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t),
        (row) => (row.referenceColumn)
      )
    }

}
trait ReferenceChange {
  import ReferenceChange._

  def create(referenceColumn: Long): ConnectionIO[Row] = {
    create(Shape(referenceColumn))
  }

  def createVoid(referenceColumn: Long): ConnectionIO[Unit] = {
    createVoid(Shape(referenceColumn))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.ReferenceChange.Shape]): Update[Shape] = {
    val sql = "INSERT INTO reference_change (reference_column) VALUES (?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.ReferenceChange.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("reference_column")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.ReferenceChange.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.ReferenceChange.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.ReferenceChange.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.ReferenceChange.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM reference_change
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
      FROM reference_change
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
