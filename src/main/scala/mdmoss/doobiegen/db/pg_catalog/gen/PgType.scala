package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgType extends PgType {

  case class Row(
    typlen: Short,
    typbyval: Boolean,
    typispreferred: Boolean,
    typisdefined: Boolean,
    typnotnull: Boolean,
    typtypmod: Int,
    typndims: Int,
    typdefault: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(typlen, typbyval, typispreferred, typisdefined, typnotnull, typtypmod, typndims, typdefault)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"typlen, typbyval, typispreferred, typisdefined, typnotnull, typtypmod, typndims, typdefault"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".typlen, " ++ Fragment.const0(a) ++ fr".typbyval, " ++ Fragment.const0(a) ++ fr".typispreferred, " ++ Fragment.const0(a) ++ fr".typisdefined, " ++ Fragment.const0(a) ++ fr".typnotnull, " ++ Fragment.const0(a) ++ fr".typtypmod, " ++ Fragment.const0(a) ++ fr".typndims, " ++ Fragment.const0(a) ++ fr".typdefault"
  }

  case class Shape(typlen: Short, typbyval: Boolean, typispreferred: Boolean, typisdefined: Boolean, typnotnull: Boolean, typtypmod: Int, typndims: Int, typdefault: Option[String] = None)

  object Shape {
    def NoDefaults(typlen: Short, typbyval: Boolean, typispreferred: Boolean, typisdefined: Boolean, typnotnull: Boolean, typtypmod: Int, typndims: Int, typdefault: Option[String]): Shape = Shape(typlen, typbyval, typispreferred, typisdefined, typnotnull, typtypmod, typndims, typdefault)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
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
        (row) => (row.typlen, (row.typbyval, (row.typispreferred, (row.typisdefined, (row.typnotnull, (row.typtypmod, (row.typndims, (row.typdefault))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
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
        (row) => (row.typlen, (row.typbyval, (row.typispreferred, (row.typisdefined, (row.typnotnull, (row.typtypmod, (row.typndims, (row.typdefault))))))))
      )
    }

}
trait PgType {
  import PgType._

  def create(typlen: Short, typbyval: Boolean, typispreferred: Boolean, typisdefined: Boolean, typnotnull: Boolean, typtypmod: Int, typndims: Int, typdefault: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(typlen, typbyval, typispreferred, typisdefined, typnotnull, typtypmod, typndims, typdefault))
  }

  def createVoid(typlen: Short, typbyval: Boolean, typispreferred: Boolean, typisdefined: Boolean, typnotnull: Boolean, typtypmod: Int, typndims: Int, typdefault: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(typlen, typbyval, typispreferred, typisdefined, typnotnull, typtypmod, typndims, typdefault))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgType.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_type (typlen, typbyval, typispreferred, typisdefined, typnotnull, typtypmod, typndims, typdefault) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgType.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("typlen", "typbyval", "typispreferred", "typisdefined", "typnotnull", "typtypmod", "typndims", "typdefault")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgType.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgType.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgType.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgType.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_type
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
      FROM pg_catalog.pg_type
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
