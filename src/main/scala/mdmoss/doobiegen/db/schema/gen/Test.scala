package mdmoss.doobiegen.db.schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object Test extends Test {

  case class Row(
    id: Option[Long]
  ) {
    def toShape: Shape = Shape.NoDefaults(id)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"id"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".id"
  }

  case class Shape(id: Option[Long] = None)

  object Shape {
    def NoDefaults(id: Option[Long]): Shape = Shape(id)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t),
        (row) => (row.id)
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t),
        (row) => (row.id)
      )
    }

}
trait Test {
  import Test._

  def create(id: Option[Long] = None): ConnectionIO[Row] = {
    create(Shape(id))
  }

  def createVoid(id: Option[Long] = None): ConnectionIO[Unit] = {
    createVoid(Shape(id))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.schema.gen.Test.Shape]): Update[Shape] = {
    val sql = "INSERT INTO schema.test (id) VALUES (?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.schema.gen.Test.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("id")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.schema.gen.Test.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.schema.gen.Test.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.schema.gen.Test.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.schema.gen.Test.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM schema.test
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
      FROM schema.test
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
