package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TestColumnIgnore extends TestColumnIgnore {

  case class Row(
    includeMe: Option[Long],
    ignoreMe: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(includeMe, ignoreMe)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"include_me, ignore_me"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".include_me, " ++ Fragment.const0(a) ++ fr".ignore_me"
  }

  case class Shape(includeMe: Option[Long] = None, ignoreMe: Option[String] = None)

  object Shape {
    def NoDefaults(includeMe: Option[Long], ignoreMe: Option[String]): Shape = Shape(includeMe, ignoreMe)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.includeMe, (row.ignoreMe))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.includeMe, (row.ignoreMe))
      )
    }

}
trait TestColumnIgnore {
  import TestColumnIgnore._

  def create(includeMe: Option[Long] = None, ignoreMe: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(includeMe, ignoreMe))
  }

  def createVoid(includeMe: Option[Long] = None, ignoreMe: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(includeMe, ignoreMe))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestColumnIgnore.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_column_ignore (include_me, ignore_me) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestColumnIgnore.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("include_me", "ignore_me")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestColumnIgnore.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestColumnIgnore.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestColumnIgnore.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestColumnIgnore.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_column_ignore
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
      FROM test_column_ignore
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
