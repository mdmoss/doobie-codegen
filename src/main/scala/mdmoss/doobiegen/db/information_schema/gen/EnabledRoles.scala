package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object EnabledRoles extends EnabledRoles {

  case class Row(
    roleName: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(roleName)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"role_name"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".role_name"
  }

  case class Shape(roleName: Option[String] = None)

  object Shape {
    def NoDefaults(roleName: Option[String]): Shape = Shape(roleName)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t),
        (row) => (row.roleName)
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t),
        (row) => (row.roleName)
      )
    }

}
trait EnabledRoles {
  import EnabledRoles._

  def create(roleName: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(roleName))
  }

  def createVoid(roleName: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(roleName))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.EnabledRoles.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.enabled_roles (role_name) VALUES (?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.EnabledRoles.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("role_name")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.EnabledRoles.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.EnabledRoles.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.EnabledRoles.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.EnabledRoles.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.enabled_roles
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
      FROM information_schema.enabled_roles
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
