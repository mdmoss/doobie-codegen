package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TestSetNotNull extends TestSetNotNull {

  case class Row(
    someNum: Int
  ) {
    def toShape: Shape = Shape.NoDefaults(someNum)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"some_num"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".some_num"
  }

  case class Shape(someNum: Int)

  object Shape {
    def NoDefaults(someNum: Int): Shape = Shape(someNum)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t),
        (row) => (row.someNum)
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t),
        (row) => (row.someNum)
      )
    }

}
trait TestSetNotNull {
  import TestSetNotNull._

  def create(someNum: Int): ConnectionIO[Row] = {
    create(Shape(someNum))
  }

  def createVoid(someNum: Int): ConnectionIO[Unit] = {
    createVoid(Shape(someNum))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestSetNotNull.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_set_not_null (some_num) VALUES (?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestSetNotNull.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("some_num")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestSetNotNull.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestSetNotNull.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestSetNotNull.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestSetNotNull.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_set_not_null
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
      FROM test_set_not_null
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
