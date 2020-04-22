package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgSequence extends PgSequence {

  case class Row(
    seqstart: Long,
    seqincrement: Long,
    seqmax: Long,
    seqmin: Long,
    seqcache: Long,
    seqcycle: Boolean
  ) {
    def toShape: Shape = Shape.NoDefaults(seqstart, seqincrement, seqmax, seqmin, seqcache, seqcycle)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"seqstart, seqincrement, seqmax, seqmin, seqcache, seqcycle"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".seqstart, " ++ Fragment.const0(a) ++ fr".seqincrement, " ++ Fragment.const0(a) ++ fr".seqmax, " ++ Fragment.const0(a) ++ fr".seqmin, " ++ Fragment.const0(a) ++ fr".seqcache, " ++ Fragment.const0(a) ++ fr".seqcycle"
  }

  case class Shape(seqstart: Long, seqincrement: Long, seqmax: Long, seqmin: Long, seqcache: Long, seqcycle: Boolean)

  object Shape {
    def NoDefaults(seqstart: Long, seqincrement: Long, seqmax: Long, seqmin: Long, seqcache: Long, seqcycle: Boolean): Shape = Shape(seqstart, seqincrement, seqmax, seqmin, seqcache, seqcycle)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2),
        (row) => (row.seqstart, (row.seqincrement, (row.seqmax, (row.seqmin, (row.seqcache, (row.seqcycle))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2),
        (row) => (row.seqstart, (row.seqincrement, (row.seqmax, (row.seqmin, (row.seqcache, (row.seqcycle))))))
      )
    }

}
trait PgSequence {
  import PgSequence._

  def create(seqstart: Long, seqincrement: Long, seqmax: Long, seqmin: Long, seqcache: Long, seqcycle: Boolean): ConnectionIO[Row] = {
    create(Shape(seqstart, seqincrement, seqmax, seqmin, seqcache, seqcycle))
  }

  def createVoid(seqstart: Long, seqincrement: Long, seqmax: Long, seqmin: Long, seqcache: Long, seqcycle: Boolean): ConnectionIO[Unit] = {
    createVoid(Shape(seqstart, seqincrement, seqmax, seqmin, seqcache, seqcycle))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSequence.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_sequence (seqstart, seqincrement, seqmax, seqmin, seqcache, seqcycle) VALUES (?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSequence.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("seqstart", "seqincrement", "seqmax", "seqmin", "seqcache", "seqcycle")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSequence.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSequence.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgSequence.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgSequence.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_sequence
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
      FROM pg_catalog.pg_sequence
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
