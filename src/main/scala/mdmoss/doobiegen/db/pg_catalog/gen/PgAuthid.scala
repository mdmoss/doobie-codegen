package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgAuthid extends PgAuthid {

  case class Row(
    rolsuper: Boolean,
    rolinherit: Boolean,
    rolcreaterole: Boolean,
    rolcreatedb: Boolean,
    rolcanlogin: Boolean,
    rolreplication: Boolean,
    rolbypassrls: Boolean,
    rolconnlimit: Int,
    rolpassword: Option[String],
    rolvaliduntil: Option[Timestamp]
  ) {
    def toShape: Shape = Shape.NoDefaults(rolsuper, rolinherit, rolcreaterole, rolcreatedb, rolcanlogin, rolreplication, rolbypassrls, rolconnlimit, rolpassword, rolvaliduntil)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"rolsuper, rolinherit, rolcreaterole, rolcreatedb, rolcanlogin, rolreplication, rolbypassrls, rolconnlimit, rolpassword, rolvaliduntil"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".rolsuper, " ++ Fragment.const0(a) ++ fr".rolinherit, " ++ Fragment.const0(a) ++ fr".rolcreaterole, " ++ Fragment.const0(a) ++ fr".rolcreatedb, " ++ Fragment.const0(a) ++ fr".rolcanlogin, " ++ Fragment.const0(a) ++ fr".rolreplication, " ++ Fragment.const0(a) ++ fr".rolbypassrls, " ++ Fragment.const0(a) ++ fr".rolconnlimit, " ++ Fragment.const0(a) ++ fr".rolpassword, " ++ Fragment.const0(a) ++ fr".rolvaliduntil"
  }

  case class Shape(rolsuper: Boolean, rolinherit: Boolean, rolcreaterole: Boolean, rolcreatedb: Boolean, rolcanlogin: Boolean, rolreplication: Boolean, rolbypassrls: Boolean, rolconnlimit: Int, rolpassword: Option[String] = None, rolvaliduntil: Option[Timestamp] = None)

  object Shape {
    def NoDefaults(rolsuper: Boolean, rolinherit: Boolean, rolcreaterole: Boolean, rolcreatedb: Boolean, rolcanlogin: Boolean, rolreplication: Boolean, rolbypassrls: Boolean, rolconnlimit: Int, rolpassword: Option[String], rolvaliduntil: Option[Timestamp]): Shape = Shape(rolsuper, rolinherit, rolcreaterole, rolcreatedb, rolcanlogin, rolreplication, rolbypassrls, rolconnlimit, rolpassword, rolvaliduntil)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta))))))))))

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
        (row) => (row.rolsuper, (row.rolinherit, (row.rolcreaterole, (row.rolcreatedb, (row.rolcanlogin, (row.rolreplication, (row.rolbypassrls, (row.rolconnlimit, (row.rolpassword, (row.rolvaliduntil))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta))))))))))

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
        (row) => (row.rolsuper, (row.rolinherit, (row.rolcreaterole, (row.rolcreatedb, (row.rolcanlogin, (row.rolreplication, (row.rolbypassrls, (row.rolconnlimit, (row.rolpassword, (row.rolvaliduntil))))))))))
      )
    }

}
trait PgAuthid {
  import PgAuthid._

  def create(rolsuper: Boolean, rolinherit: Boolean, rolcreaterole: Boolean, rolcreatedb: Boolean, rolcanlogin: Boolean, rolreplication: Boolean, rolbypassrls: Boolean, rolconnlimit: Int, rolpassword: Option[String] = None, rolvaliduntil: Option[Timestamp] = None): ConnectionIO[Row] = {
    create(Shape(rolsuper, rolinherit, rolcreaterole, rolcreatedb, rolcanlogin, rolreplication, rolbypassrls, rolconnlimit, rolpassword, rolvaliduntil))
  }

  def createVoid(rolsuper: Boolean, rolinherit: Boolean, rolcreaterole: Boolean, rolcreatedb: Boolean, rolcanlogin: Boolean, rolreplication: Boolean, rolbypassrls: Boolean, rolconnlimit: Int, rolpassword: Option[String] = None, rolvaliduntil: Option[Timestamp] = None): ConnectionIO[Unit] = {
    createVoid(Shape(rolsuper, rolinherit, rolcreaterole, rolcreatedb, rolcanlogin, rolreplication, rolbypassrls, rolconnlimit, rolpassword, rolvaliduntil))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAuthid.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_authid (rolsuper, rolinherit, rolcreaterole, rolcreatedb, rolcanlogin, rolreplication, rolbypassrls, rolconnlimit, rolpassword, rolvaliduntil) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAuthid.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("rolsuper", "rolinherit", "rolcreaterole", "rolcreatedb", "rolcanlogin", "rolreplication", "rolbypassrls", "rolconnlimit", "rolpassword", "rolvaliduntil")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAuthid.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAuthid.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgAuthid.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgAuthid.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_authid
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
      FROM pg_catalog.pg_authid
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
