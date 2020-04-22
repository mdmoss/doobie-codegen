package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import argonaut.{Json, Parse}
import org.postgresql.util.PGobject
import doobie.postgres.imports._

object TestJsonType extends TestJsonType {

  case class Row(
    data: Option[argonaut.Json]
  ) {
    def toShape: Shape = Shape.NoDefaults(data)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"data"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".data"
  }

  case class Shape(data: Option[argonaut.Json] = None)

  object Shape {
    def NoDefaults(data: Option[argonaut.Json]): Shape = Shape(data)
  }

    private val zippedRowComposite = implicitly[Composite[Option[argonaut.Json]]]

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t),
        (row) => (row.data)
      )
    }

    private val zippedShapeComposite = implicitly[Composite[Option[argonaut.Json]]]

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t),
        (row) => (row.data)
      )
    }

}
trait TestJsonType {
  import TestJsonType._

  implicit val JsonMeta: doobie.imports.Meta[Json] =
    doobie.imports.Meta.other[PGobject]("json").xmap[Json](
      a => Parse.parse(a.getValue).fold(sys.error, identity), // failure raises an exception
      a => {
        val p = new PGobject
        p.setType("json")
        p.setValue(a.nospaces)
        p
      }
    )

  def create(data: Option[argonaut.Json] = None): ConnectionIO[Row] = {
    create(Shape(data))
  }

  def createVoid(data: Option[argonaut.Json] = None): ConnectionIO[Unit] = {
    createVoid(Shape(data))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestJsonType.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_json_type (data) VALUES (?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestJsonType.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("data")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestJsonType.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestJsonType.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestJsonType.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestJsonType.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_json_type
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
      FROM test_json_type
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
