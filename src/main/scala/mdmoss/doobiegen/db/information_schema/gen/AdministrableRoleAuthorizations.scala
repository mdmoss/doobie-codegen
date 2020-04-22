package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object AdministrableRoleAuthorizations extends AdministrableRoleAuthorizations {

  case class Row(
    grantee: Option[String],
    roleName: Option[String],
    isGrantable: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(grantee, roleName, isGrantable)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"grantee, role_name, is_grantable"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".grantee, " ++ Fragment.const0(a) ++ fr".role_name, " ++ Fragment.const0(a) ++ fr".is_grantable"
  }

  case class Shape(grantee: Option[String] = None, roleName: Option[String] = None, isGrantable: Option[String] = None)

  object Shape {
    def NoDefaults(grantee: Option[String], roleName: Option[String], isGrantable: Option[String]): Shape = Shape(grantee, roleName, isGrantable)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.grantee, (row.roleName, (row.isGrantable)))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2),
        (row) => (row.grantee, (row.roleName, (row.isGrantable)))
      )
    }

}
trait AdministrableRoleAuthorizations {
  import AdministrableRoleAuthorizations._

  def create(grantee: Option[String] = None, roleName: Option[String] = None, isGrantable: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(grantee, roleName, isGrantable))
  }

  def createVoid(grantee: Option[String] = None, roleName: Option[String] = None, isGrantable: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(grantee, roleName, isGrantable))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.AdministrableRoleAuthorizations.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.administrable_role_authorizations (grantee, role_name, is_grantable) VALUES (?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.AdministrableRoleAuthorizations.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("grantee", "role_name", "is_grantable")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.AdministrableRoleAuthorizations.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.AdministrableRoleAuthorizations.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.AdministrableRoleAuthorizations.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.AdministrableRoleAuthorizations.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.administrable_role_authorizations
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
      FROM information_schema.administrable_role_authorizations
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
