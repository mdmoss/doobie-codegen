package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgRoles extends PgRoles {

  case class Row(
    rolsuper: Option[Boolean],
    rolinherit: Option[Boolean],
    rolcreaterole: Option[Boolean],
    rolcreatedb: Option[Boolean],
    rolcanlogin: Option[Boolean],
    rolreplication: Option[Boolean],
    rolconnlimit: Option[Int],
    rolpassword: Option[String],
    rolvaliduntil: Option[Timestamp],
    rolbypassrls: Option[Boolean]
  ) {
    def toShape: Shape = Shape.NoDefaults(rolsuper, rolinherit, rolcreaterole, rolcreatedb, rolcanlogin, rolreplication, rolconnlimit, rolpassword, rolvaliduntil, rolbypassrls)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"rolsuper, rolinherit, rolcreaterole, rolcreatedb, rolcanlogin, rolreplication, rolconnlimit, rolpassword, rolvaliduntil, rolbypassrls"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".rolsuper, " ++ Fragment.const0(a) ++ fr".rolinherit, " ++ Fragment.const0(a) ++ fr".rolcreaterole, " ++ Fragment.const0(a) ++ fr".rolcreatedb, " ++ Fragment.const0(a) ++ fr".rolcanlogin, " ++ Fragment.const0(a) ++ fr".rolreplication, " ++ Fragment.const0(a) ++ fr".rolconnlimit, " ++ Fragment.const0(a) ++ fr".rolpassword, " ++ Fragment.const0(a) ++ fr".rolvaliduntil, " ++ Fragment.const0(a) ++ fr".rolbypassrls"
  }

  case class Shape(rolsuper: Option[Boolean] = None, rolinherit: Option[Boolean] = None, rolcreaterole: Option[Boolean] = None, rolcreatedb: Option[Boolean] = None, rolcanlogin: Option[Boolean] = None, rolreplication: Option[Boolean] = None, rolconnlimit: Option[Int] = None, rolpassword: Option[String] = None, rolvaliduntil: Option[Timestamp] = None, rolbypassrls: Option[Boolean] = None)

  object Shape {
    def NoDefaults(rolsuper: Option[Boolean], rolinherit: Option[Boolean], rolcreaterole: Option[Boolean], rolcreatedb: Option[Boolean], rolcanlogin: Option[Boolean], rolreplication: Option[Boolean], rolconnlimit: Option[Int], rolpassword: Option[String], rolvaliduntil: Option[Timestamp], rolbypassrls: Option[Boolean]): Shape = Shape(rolsuper, rolinherit, rolcreaterole, rolcreatedb, rolcanlogin, rolreplication, rolconnlimit, rolpassword, rolvaliduntil, rolbypassrls)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta))))))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2),
        (row) => (row.rolsuper, (row.rolinherit, (row.rolcreaterole, (row.rolcreatedb, (row.rolcanlogin, (row.rolreplication, (row.rolconnlimit, (row.rolpassword, (row.rolvaliduntil, (row.rolbypassrls))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta))))))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2),
        (row) => (row.rolsuper, (row.rolinherit, (row.rolcreaterole, (row.rolcreatedb, (row.rolcanlogin, (row.rolreplication, (row.rolconnlimit, (row.rolpassword, (row.rolvaliduntil, (row.rolbypassrls))))))))))
      )
    }

}
trait PgRoles {
  import PgRoles._

  def create(rolsuper: Option[Boolean] = None, rolinherit: Option[Boolean] = None, rolcreaterole: Option[Boolean] = None, rolcreatedb: Option[Boolean] = None, rolcanlogin: Option[Boolean] = None, rolreplication: Option[Boolean] = None, rolconnlimit: Option[Int] = None, rolpassword: Option[String] = None, rolvaliduntil: Option[Timestamp] = None, rolbypassrls: Option[Boolean] = None): ConnectionIO[Row] = {
    create(Shape(rolsuper, rolinherit, rolcreaterole, rolcreatedb, rolcanlogin, rolreplication, rolconnlimit, rolpassword, rolvaliduntil, rolbypassrls))
  }

  def createVoid(rolsuper: Option[Boolean] = None, rolinherit: Option[Boolean] = None, rolcreaterole: Option[Boolean] = None, rolcreatedb: Option[Boolean] = None, rolcanlogin: Option[Boolean] = None, rolreplication: Option[Boolean] = None, rolconnlimit: Option[Int] = None, rolpassword: Option[String] = None, rolvaliduntil: Option[Timestamp] = None, rolbypassrls: Option[Boolean] = None): ConnectionIO[Unit] = {
    createVoid(Shape(rolsuper, rolinherit, rolcreaterole, rolcreatedb, rolcanlogin, rolreplication, rolconnlimit, rolpassword, rolvaliduntil, rolbypassrls))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgRoles.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_roles (rolsuper, rolinherit, rolcreaterole, rolcreatedb, rolcanlogin, rolreplication, rolconnlimit, rolpassword, rolvaliduntil, rolbypassrls) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgRoles.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("rolsuper", "rolinherit", "rolcreaterole", "rolcreatedb", "rolcanlogin", "rolreplication", "rolconnlimit", "rolpassword", "rolvaliduntil", "rolbypassrls")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgRoles.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgRoles.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgRoles.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgRoles.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_roles
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
      FROM pg_catalog.pg_roles
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
