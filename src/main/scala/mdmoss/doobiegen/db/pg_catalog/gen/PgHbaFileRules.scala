package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgHbaFileRules extends PgHbaFileRules {

  case class Row(
    lineNumber: Option[Int],
    `type`: Option[String],
    address: Option[String],
    netmask: Option[String],
    authMethod: Option[String],
    error: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(lineNumber, `type`, address, netmask, authMethod, error)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"line_number, type, address, netmask, auth_method, error"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".line_number, " ++ Fragment.const0(a) ++ fr".type, " ++ Fragment.const0(a) ++ fr".address, " ++ Fragment.const0(a) ++ fr".netmask, " ++ Fragment.const0(a) ++ fr".auth_method, " ++ Fragment.const0(a) ++ fr".error"
  }

  case class Shape(lineNumber: Option[Int] = None, `type`: Option[String] = None, address: Option[String] = None, netmask: Option[String] = None, authMethod: Option[String] = None, error: Option[String] = None)

  object Shape {
    def NoDefaults(lineNumber: Option[Int], `type`: Option[String], address: Option[String], netmask: Option[String], authMethod: Option[String], error: Option[String]): Shape = Shape(lineNumber, `type`, address, netmask, authMethod, error)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2),
        (row) => (row.lineNumber, (row.`type`, (row.address, (row.netmask, (row.authMethod, (row.error))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2),
        (row) => (row.lineNumber, (row.`type`, (row.address, (row.netmask, (row.authMethod, (row.error))))))
      )
    }

}
trait PgHbaFileRules {
  import PgHbaFileRules._

  def create(lineNumber: Option[Int] = None, `type`: Option[String] = None, address: Option[String] = None, netmask: Option[String] = None, authMethod: Option[String] = None, error: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(lineNumber, `type`, address, netmask, authMethod, error))
  }

  def createVoid(lineNumber: Option[Int] = None, `type`: Option[String] = None, address: Option[String] = None, netmask: Option[String] = None, authMethod: Option[String] = None, error: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(lineNumber, `type`, address, netmask, authMethod, error))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgHbaFileRules.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_hba_file_rules (line_number, type, address, netmask, auth_method, error) VALUES (?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgHbaFileRules.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("line_number", "type", "address", "netmask", "auth_method", "error")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgHbaFileRules.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgHbaFileRules.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgHbaFileRules.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgHbaFileRules.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_hba_file_rules
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
      FROM pg_catalog.pg_hba_file_rules
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
