package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TestNullible extends TestNullible {

  case class Row(
    alwaysString: String,
    sometimesString: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(alwaysString, sometimesString)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"always_string, sometimes_string"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".always_string, " ++ Fragment.const0(a) ++ fr".sometimes_string"
  }

  case class Shape(alwaysString: String, sometimesString: Option[String] = None)

  object Shape {
    def NoDefaults(alwaysString: String, sometimesString: Option[String]): Shape = Shape(alwaysString, sometimesString)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.alwaysString, (row.sometimesString))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.alwaysString, (row.sometimesString))
      )
    }

}
trait TestNullible {
  import TestNullible._

  def create(alwaysString: String, sometimesString: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(alwaysString, sometimesString))
  }

  def createVoid(alwaysString: String, sometimesString: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(alwaysString, sometimesString))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestNullible.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_nullible (always_string, sometimes_string) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestNullible.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("always_string", "sometimes_string")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestNullible.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestNullible.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestNullible.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestNullible.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_nullible
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
      FROM test_nullible
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
