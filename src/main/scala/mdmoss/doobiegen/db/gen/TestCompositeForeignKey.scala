package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TestCompositeForeignKey extends TestCompositeForeignKey {

  case class Row(
    a: String,
    b: String
  ) {
    def toShape: Shape = Shape.NoDefaults(a, b)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"a, b"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".a, " ++ Fragment.const0(a) ++ fr".b"
  }

  case class Shape(a: String, b: String)

  object Shape {
    def NoDefaults(a: String, b: String): Shape = Shape(a, b)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.a, (row.b))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.a, (row.b))
      )
    }

}
trait TestCompositeForeignKey {
  import TestCompositeForeignKey._

  def create(a: String, b: String): ConnectionIO[Row] = {
    create(Shape(a, b))
  }

  def createVoid(a: String, b: String): ConnectionIO[Unit] = {
    createVoid(Shape(a, b))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestCompositeForeignKey.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_composite_foreign_key (a, b) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestCompositeForeignKey.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("a", "b")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestCompositeForeignKey.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestCompositeForeignKey.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestCompositeForeignKey.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestCompositeForeignKey.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_composite_foreign_key
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
      FROM test_composite_foreign_key
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
