package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgSequences extends PgSequences {

  case class Row(
    startValue: Option[Long],
    minValue: Option[Long],
    maxValue: Option[Long],
    incrementBy: Option[Long],
    cycle: Option[Boolean],
    cacheSize: Option[Long],
    lastValue: Option[Long]
  ) {
    def toShape: Shape = Shape.NoDefaults(startValue, minValue, maxValue, incrementBy, cycle, cacheSize, lastValue)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"start_value, min_value, max_value, increment_by, cycle, cache_size, last_value"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".start_value, " ++ Fragment.const0(a) ++ fr".min_value, " ++ Fragment.const0(a) ++ fr".max_value, " ++ Fragment.const0(a) ++ fr".increment_by, " ++ Fragment.const0(a) ++ fr".cycle, " ++ Fragment.const0(a) ++ fr".cache_size, " ++ Fragment.const0(a) ++ fr".last_value"
  }

  case class Shape(startValue: Option[Long] = None, minValue: Option[Long] = None, maxValue: Option[Long] = None, incrementBy: Option[Long] = None, cycle: Option[Boolean] = None, cacheSize: Option[Long] = None, lastValue: Option[Long] = None)

  object Shape {
    def NoDefaults(startValue: Option[Long], minValue: Option[Long], maxValue: Option[Long], incrementBy: Option[Long], cycle: Option[Boolean], cacheSize: Option[Long], lastValue: Option[Long]): Shape = Shape(startValue, minValue, maxValue, incrementBy, cycle, cacheSize, lastValue)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2),
        (row) => (row.startValue, (row.minValue, (row.maxValue, (row.incrementBy, (row.cycle, (row.cacheSize, (row.lastValue)))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2),
        (row) => (row.startValue, (row.minValue, (row.maxValue, (row.incrementBy, (row.cycle, (row.cacheSize, (row.lastValue)))))))
      )
    }

}
trait PgSequences {
  import PgSequences._

  def create(startValue: Option[Long] = None, minValue: Option[Long] = None, maxValue: Option[Long] = None, incrementBy: Option[Long] = None, cycle: Option[Boolean] = None, cacheSize: Option[Long] = None, lastValue: Option[Long] = None): ConnectionIO[Row] = {
    create(Shape(startValue, minValue, maxValue, incrementBy, cycle, cacheSize, lastValue))
  }

  def createVoid(startValue: Option[Long] = None, minValue: Option[Long] = None, maxValue: Option[Long] = None, incrementBy: Option[Long] = None, cycle: Option[Boolean] = None, cacheSize: Option[Long] = None, lastValue: Option[Long] = None): ConnectionIO[Unit] = {
    createVoid(Shape(startValue, minValue, maxValue, incrementBy, cycle, cacheSize, lastValue))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSequences.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_sequences (start_value, min_value, max_value, increment_by, cycle, cache_size, last_value) VALUES (?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSequences.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("start_value", "min_value", "max_value", "increment_by", "cycle", "cache_size", "last_value")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSequences.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgSequences.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgSequences.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgSequences.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_sequences
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
      FROM pg_catalog.pg_sequences
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
