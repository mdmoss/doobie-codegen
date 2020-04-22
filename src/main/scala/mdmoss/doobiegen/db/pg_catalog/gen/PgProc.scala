package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgProc extends PgProc {

  case class Row(
    prosecdef: Boolean,
    proleakproof: Boolean,
    proisstrict: Boolean,
    proretset: Boolean,
    pronargs: Short,
    pronargdefaults: Short,
    prosrc: String,
    probin: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(prosecdef, proleakproof, proisstrict, proretset, pronargs, pronargdefaults, prosrc, probin)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"prosecdef, proleakproof, proisstrict, proretset, pronargs, pronargdefaults, prosrc, probin"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".prosecdef, " ++ Fragment.const0(a) ++ fr".proleakproof, " ++ Fragment.const0(a) ++ fr".proisstrict, " ++ Fragment.const0(a) ++ fr".proretset, " ++ Fragment.const0(a) ++ fr".pronargs, " ++ Fragment.const0(a) ++ fr".pronargdefaults, " ++ Fragment.const0(a) ++ fr".prosrc, " ++ Fragment.const0(a) ++ fr".probin"
  }

  case class Shape(prosecdef: Boolean, proleakproof: Boolean, proisstrict: Boolean, proretset: Boolean, pronargs: Short, pronargdefaults: Short, prosrc: String, probin: Option[String] = None)

  object Shape {
    def NoDefaults(prosecdef: Boolean, proleakproof: Boolean, proisstrict: Boolean, proretset: Boolean, pronargs: Short, pronargdefaults: Short, prosrc: String, probin: Option[String]): Shape = Shape(prosecdef, proleakproof, proisstrict, proretset, pronargs, pronargdefaults, prosrc, probin)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2),
        (row) => (row.prosecdef, (row.proleakproof, (row.proisstrict, (row.proretset, (row.pronargs, (row.pronargdefaults, (row.prosrc, (row.probin))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2),
        (row) => (row.prosecdef, (row.proleakproof, (row.proisstrict, (row.proretset, (row.pronargs, (row.pronargdefaults, (row.prosrc, (row.probin))))))))
      )
    }

}
trait PgProc {
  import PgProc._

  def create(prosecdef: Boolean, proleakproof: Boolean, proisstrict: Boolean, proretset: Boolean, pronargs: Short, pronargdefaults: Short, prosrc: String, probin: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(prosecdef, proleakproof, proisstrict, proretset, pronargs, pronargdefaults, prosrc, probin))
  }

  def createVoid(prosecdef: Boolean, proleakproof: Boolean, proisstrict: Boolean, proretset: Boolean, pronargs: Short, pronargdefaults: Short, prosrc: String, probin: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(prosecdef, proleakproof, proisstrict, proretset, pronargs, pronargdefaults, prosrc, probin))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgProc.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_proc (prosecdef, proleakproof, proisstrict, proretset, pronargs, pronargdefaults, prosrc, probin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgProc.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("prosecdef", "proleakproof", "proisstrict", "proretset", "pronargs", "pronargdefaults", "prosrc", "probin")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgProc.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgProc.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgProc.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgProc.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_proc
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
      FROM pg_catalog.pg_proc
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
