package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgAttribute extends PgAttribute {

  case class Row(
    attstattarget: Int,
    attlen: Short,
    attnum: Short,
    attndims: Int,
    attcacheoff: Int,
    atttypmod: Int,
    attbyval: Boolean,
    attnotnull: Boolean,
    atthasdef: Boolean,
    atthasmissing: Boolean,
    attisdropped: Boolean,
    attislocal: Boolean,
    attinhcount: Int
  ) {
    def toShape: Shape = Shape.NoDefaults(attstattarget, attlen, attnum, attndims, attcacheoff, atttypmod, attbyval, attnotnull, atthasdef, atthasmissing, attisdropped, attislocal, attinhcount)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"attstattarget, attlen, attnum, attndims, attcacheoff, atttypmod, attbyval, attnotnull, atthasdef, atthasmissing, attisdropped, attislocal, attinhcount"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".attstattarget, " ++ Fragment.const0(a) ++ fr".attlen, " ++ Fragment.const0(a) ++ fr".attnum, " ++ Fragment.const0(a) ++ fr".attndims, " ++ Fragment.const0(a) ++ fr".attcacheoff, " ++ Fragment.const0(a) ++ fr".atttypmod, " ++ Fragment.const0(a) ++ fr".attbyval, " ++ Fragment.const0(a) ++ fr".attnotnull, " ++ Fragment.const0(a) ++ fr".atthasdef, " ++ Fragment.const0(a) ++ fr".atthasmissing, " ++ Fragment.const0(a) ++ fr".attisdropped, " ++ Fragment.const0(a) ++ fr".attislocal, " ++ Fragment.const0(a) ++ fr".attinhcount"
  }

  case class Shape(attstattarget: Int, attlen: Short, attnum: Short, attndims: Int, attcacheoff: Int, atttypmod: Int, attbyval: Boolean, attnotnull: Boolean, atthasdef: Boolean, atthasmissing: Boolean, attisdropped: Boolean, attislocal: Boolean, attinhcount: Int)

  object Shape {
    def NoDefaults(attstattarget: Int, attlen: Short, attnum: Short, attndims: Int, attcacheoff: Int, atttypmod: Int, attbyval: Boolean, attnotnull: Boolean, atthasdef: Boolean, atthasmissing: Boolean, attisdropped: Boolean, attislocal: Boolean, attinhcount: Int): Shape = Shape(attstattarget, attlen, attnum, attndims, attcacheoff, atttypmod, attbyval, attnotnull, atthasdef, atthasmissing, attisdropped, attislocal, attinhcount)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.attstattarget, (row.attlen, (row.attnum, (row.attndims, (row.attcacheoff, (row.atttypmod, (row.attbyval, (row.attnotnull, (row.atthasdef, (row.atthasmissing, (row.attisdropped, (row.attislocal, (row.attinhcount)))))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.attstattarget, (row.attlen, (row.attnum, (row.attndims, (row.attcacheoff, (row.atttypmod, (row.attbyval, (row.attnotnull, (row.atthasdef, (row.atthasmissing, (row.attisdropped, (row.attislocal, (row.attinhcount)))))))))))))
      )
    }

}
trait PgAttribute {
  import PgAttribute._

  def create(attstattarget: Int, attlen: Short, attnum: Short, attndims: Int, attcacheoff: Int, atttypmod: Int, attbyval: Boolean, attnotnull: Boolean, atthasdef: Boolean, atthasmissing: Boolean, attisdropped: Boolean, attislocal: Boolean, attinhcount: Int): ConnectionIO[Row] = {
    create(Shape(attstattarget, attlen, attnum, attndims, attcacheoff, atttypmod, attbyval, attnotnull, atthasdef, atthasmissing, attisdropped, attislocal, attinhcount))
  }

  def createVoid(attstattarget: Int, attlen: Short, attnum: Short, attndims: Int, attcacheoff: Int, atttypmod: Int, attbyval: Boolean, attnotnull: Boolean, atthasdef: Boolean, atthasmissing: Boolean, attisdropped: Boolean, attislocal: Boolean, attinhcount: Int): ConnectionIO[Unit] = {
    createVoid(Shape(attstattarget, attlen, attnum, attndims, attcacheoff, atttypmod, attbyval, attnotnull, atthasdef, atthasmissing, attisdropped, attislocal, attinhcount))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAttribute.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_attribute (attstattarget, attlen, attnum, attndims, attcacheoff, atttypmod, attbyval, attnotnull, atthasdef, atthasmissing, attisdropped, attislocal, attinhcount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAttribute.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("attstattarget", "attlen", "attnum", "attndims", "attcacheoff", "atttypmod", "attbyval", "attnotnull", "atthasdef", "atthasmissing", "attisdropped", "attislocal", "attinhcount")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAttribute.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAttribute.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgAttribute.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgAttribute.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_attribute
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
      FROM pg_catalog.pg_attribute
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
