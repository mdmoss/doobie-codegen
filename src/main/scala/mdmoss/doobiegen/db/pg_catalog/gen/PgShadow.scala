package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgShadow extends PgShadow {

  case class Row(
    usecreatedb: Option[Boolean],
    usesuper: Option[Boolean],
    userepl: Option[Boolean],
    usebypassrls: Option[Boolean],
    passwd: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(usecreatedb, usesuper, userepl, usebypassrls, passwd)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"usecreatedb, usesuper, userepl, usebypassrls, passwd"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".usecreatedb, " ++ Fragment.const0(a) ++ fr".usesuper, " ++ Fragment.const0(a) ++ fr".userepl, " ++ Fragment.const0(a) ++ fr".usebypassrls, " ++ Fragment.const0(a) ++ fr".passwd"
  }

  case class Shape(usecreatedb: Option[Boolean] = None, usesuper: Option[Boolean] = None, userepl: Option[Boolean] = None, usebypassrls: Option[Boolean] = None, passwd: Option[String] = None)

  object Shape {
    def NoDefaults(usecreatedb: Option[Boolean], usesuper: Option[Boolean], userepl: Option[Boolean], usebypassrls: Option[Boolean], passwd: Option[String]): Shape = Shape(usecreatedb, usesuper, userepl, usebypassrls, passwd)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.usecreatedb, (row.usesuper, (row.userepl, (row.usebypassrls, (row.passwd)))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.usecreatedb, (row.usesuper, (row.userepl, (row.usebypassrls, (row.passwd)))))
      )
    }

}
trait PgShadow {
  import PgShadow._

  def create(usecreatedb: Option[Boolean] = None, usesuper: Option[Boolean] = None, userepl: Option[Boolean] = None, usebypassrls: Option[Boolean] = None, passwd: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(usecreatedb, usesuper, userepl, usebypassrls, passwd))
  }

  def createVoid(usecreatedb: Option[Boolean] = None, usesuper: Option[Boolean] = None, userepl: Option[Boolean] = None, usebypassrls: Option[Boolean] = None, passwd: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(usecreatedb, usesuper, userepl, usebypassrls, passwd))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgShadow.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_shadow (usecreatedb, usesuper, userepl, usebypassrls, passwd) VALUES (?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgShadow.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("usecreatedb", "usesuper", "userepl", "usebypassrls", "passwd")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgShadow.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgShadow.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgShadow.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgShadow.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_shadow
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
      FROM pg_catalog.pg_shadow
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
