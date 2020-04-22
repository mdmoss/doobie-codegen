package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgForeignServer extends PgForeignServer {

  case class Row(
    srvtype: Option[String],
    srvversion: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(srvtype, srvversion)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"srvtype, srvversion"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".srvtype, " ++ Fragment.const0(a) ++ fr".srvversion"
  }

  case class Shape(srvtype: Option[String] = None, srvversion: Option[String] = None)

  object Shape {
    def NoDefaults(srvtype: Option[String], srvversion: Option[String]): Shape = Shape(srvtype, srvversion)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.srvtype, (row.srvversion))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.srvtype, (row.srvversion))
      )
    }

}
trait PgForeignServer {
  import PgForeignServer._

  def create(srvtype: Option[String] = None, srvversion: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(srvtype, srvversion))
  }

  def createVoid(srvtype: Option[String] = None, srvversion: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(srvtype, srvversion))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgForeignServer.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_foreign_server (srvtype, srvversion) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgForeignServer.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("srvtype", "srvversion")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgForeignServer.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgForeignServer.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgForeignServer.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgForeignServer.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_foreign_server
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
      FROM pg_catalog.pg_foreign_server
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
