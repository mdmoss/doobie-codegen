package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TestDrop_1 extends TestDrop_1 {

  case class Row(
    thing: Int
  ) {
    def toShape: Shape = Shape.NoDefaults(thing)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"thing"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".thing"
  }

  case class Shape(thing: Int)

  object Shape {
    def NoDefaults(thing: Int): Shape = Shape(thing)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t),
        (row) => (row.thing)
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t),
        (row) => (row.thing)
      )
    }

}
trait TestDrop_1 {
  import TestDrop_1._

  def create(thing: Int): ConnectionIO[Row] = {
    create(Shape(thing))
  }

  def createVoid(thing: Int): ConnectionIO[Unit] = {
    createVoid(Shape(thing))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestDrop_1.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_drop_1 (thing) VALUES (?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestDrop_1.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("thing")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestDrop_1.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestDrop_1.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestDrop_1.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestDrop_1.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_drop_1
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
      FROM test_drop_1
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
