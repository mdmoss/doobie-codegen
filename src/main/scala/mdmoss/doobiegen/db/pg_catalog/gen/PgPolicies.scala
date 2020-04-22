package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgPolicies extends PgPolicies {

  case class Row(
    permissive: Option[String],
    cmd: Option[String],
    qual: Option[String],
    withCheck: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(permissive, cmd, qual, withCheck)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"permissive, cmd, qual, with_check"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".permissive, " ++ Fragment.const0(a) ++ fr".cmd, " ++ Fragment.const0(a) ++ fr".qual, " ++ Fragment.const0(a) ++ fr".with_check"
  }

  case class Shape(permissive: Option[String] = None, cmd: Option[String] = None, qual: Option[String] = None, withCheck: Option[String] = None)

  object Shape {
    def NoDefaults(permissive: Option[String], cmd: Option[String], qual: Option[String], withCheck: Option[String]): Shape = Shape(permissive, cmd, qual, withCheck)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.permissive, (row.cmd, (row.qual, (row.withCheck))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.permissive, (row.cmd, (row.qual, (row.withCheck))))
      )
    }

}
trait PgPolicies {
  import PgPolicies._

  def create(permissive: Option[String] = None, cmd: Option[String] = None, qual: Option[String] = None, withCheck: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(permissive, cmd, qual, withCheck))
  }

  def createVoid(permissive: Option[String] = None, cmd: Option[String] = None, qual: Option[String] = None, withCheck: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(permissive, cmd, qual, withCheck))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPolicies.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_policies (permissive, cmd, qual, with_check) VALUES (?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPolicies.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("permissive", "cmd", "qual", "with_check")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPolicies.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPolicies.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgPolicies.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgPolicies.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_policies
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
      FROM pg_catalog.pg_policies
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
