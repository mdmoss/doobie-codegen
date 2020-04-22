package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgTrigger extends PgTrigger {

  case class Row(
    tgtype: Short,
    tgisinternal: Boolean,
    tgdeferrable: Boolean,
    tginitdeferred: Boolean,
    tgnargs: Short
  ) {
    def toShape: Shape = Shape.NoDefaults(tgtype, tgisinternal, tgdeferrable, tginitdeferred, tgnargs)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"tgtype, tgisinternal, tgdeferrable, tginitdeferred, tgnargs"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".tgtype, " ++ Fragment.const0(a) ++ fr".tgisinternal, " ++ Fragment.const0(a) ++ fr".tgdeferrable, " ++ Fragment.const0(a) ++ fr".tginitdeferred, " ++ Fragment.const0(a) ++ fr".tgnargs"
  }

  case class Shape(tgtype: Short, tgisinternal: Boolean, tgdeferrable: Boolean, tginitdeferred: Boolean, tgnargs: Short)

  object Shape {
    def NoDefaults(tgtype: Short, tgisinternal: Boolean, tgdeferrable: Boolean, tginitdeferred: Boolean, tgnargs: Short): Shape = Shape(tgtype, tgisinternal, tgdeferrable, tginitdeferred, tgnargs)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.tgtype, (row.tgisinternal, (row.tgdeferrable, (row.tginitdeferred, (row.tgnargs)))))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.tgtype, (row.tgisinternal, (row.tgdeferrable, (row.tginitdeferred, (row.tgnargs)))))
      )
    }

}
trait PgTrigger {
  import PgTrigger._

  def create(tgtype: Short, tgisinternal: Boolean, tgdeferrable: Boolean, tginitdeferred: Boolean, tgnargs: Short): ConnectionIO[Row] = {
    create(Shape(tgtype, tgisinternal, tgdeferrable, tginitdeferred, tgnargs))
  }

  def createVoid(tgtype: Short, tgisinternal: Boolean, tgdeferrable: Boolean, tginitdeferred: Boolean, tgnargs: Short): ConnectionIO[Unit] = {
    createVoid(Shape(tgtype, tgisinternal, tgdeferrable, tginitdeferred, tgnargs))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTrigger.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_trigger (tgtype, tgisinternal, tgdeferrable, tginitdeferred, tgnargs) VALUES (?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTrigger.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("tgtype", "tgisinternal", "tgdeferrable", "tginitdeferred", "tgnargs")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTrigger.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTrigger.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgTrigger.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgTrigger.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_trigger
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
      FROM pg_catalog.pg_trigger
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
