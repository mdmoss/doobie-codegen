package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import argonaut.{Json, Parse}
import org.postgresql.util.PGobject
import doobie.postgres.imports._

object TestTypes extends TestTypes {

  case class Row(
    bigintT: Option[Long],
    booleanT: Option[Boolean],
    doubleT: Option[Double],
    integerT: Option[Int],
    textT: Option[String],
    timestampT: Option[LocalDateTime],
    timestampzT: Option[Timestamp],
    jsonbT: Option[argonaut.Json],
    dateT: Option[LocalDate],
    timeT: Option[Time],
    shortT: Option[Short],
    uuidT: Option[UUID]
  ) {
    def toShape: Shape = Shape.NoDefaults(bigintT, booleanT, doubleT, integerT, textT, timestampT, timestampzT, jsonbT, dateT, timeT, shortT, uuidT)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"bigint_t, boolean_t, double_t, integer_t, text_t, timestamp_t, timestampz_t, jsonb_t, date_t, time_t, short_t, uuid_t"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".bigint_t, " ++ Fragment.const0(a) ++ fr".boolean_t, " ++ Fragment.const0(a) ++ fr".double_t, " ++ Fragment.const0(a) ++ fr".integer_t, " ++ Fragment.const0(a) ++ fr".text_t, " ++ Fragment.const0(a) ++ fr".timestamp_t, " ++ Fragment.const0(a) ++ fr".timestampz_t, " ++ Fragment.const0(a) ++ fr".jsonb_t, " ++ Fragment.const0(a) ++ fr".date_t, " ++ Fragment.const0(a) ++ fr".time_t, " ++ Fragment.const0(a) ++ fr".short_t, " ++ Fragment.const0(a) ++ fr".uuid_t"
  }

  case class Shape(bigintT: Option[Long] = None, booleanT: Option[Boolean] = None, doubleT: Option[Double] = None, integerT: Option[Int] = None, textT: Option[String] = None, timestampT: Option[LocalDateTime] = None, timestampzT: Option[Timestamp] = None, jsonbT: Option[argonaut.Json] = None, dateT: Option[LocalDate] = None, timeT: Option[Time] = None, shortT: Option[Short] = None, uuidT: Option[UUID] = None)

  object Shape {
    def NoDefaults(bigintT: Option[Long], booleanT: Option[Boolean], doubleT: Option[Double], integerT: Option[Int], textT: Option[String], timestampT: Option[LocalDateTime], timestampzT: Option[Timestamp], jsonbT: Option[argonaut.Json], dateT: Option[LocalDate], timeT: Option[Time], shortT: Option[Short], uuidT: Option[UUID]): Shape = Shape(bigintT, booleanT, doubleT, integerT, textT, timestampT, timestampzT, jsonbT, dateT, timeT, shortT, uuidT)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.DoubleMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(implicitly[Composite[Option[LocalDateTime]]]
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(implicitly[Composite[Option[argonaut.Json]]]
    .zip(implicitly[Composite[Option[LocalDate]]]
    .zip(implicitly[Composite[Option[Time]]]
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.ShortMeta)
    .zip(implicitly[Composite[Option[UUID]]])))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.bigintT, (row.booleanT, (row.doubleT, (row.integerT, (row.textT, (row.timestampT, (row.timestampzT, (row.jsonbT, (row.dateT, (row.timeT, (row.shortT, (row.uuidT))))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.DoubleMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(implicitly[Composite[Option[LocalDateTime]]]
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.TimestampMeta)
    .zip(implicitly[Composite[Option[argonaut.Json]]]
    .zip(implicitly[Composite[Option[LocalDate]]]
    .zip(implicitly[Composite[Option[Time]]]
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.ShortMeta)
    .zip(implicitly[Composite[Option[UUID]]])))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.bigintT, (row.booleanT, (row.doubleT, (row.integerT, (row.textT, (row.timestampT, (row.timestampzT, (row.jsonbT, (row.dateT, (row.timeT, (row.shortT, (row.uuidT))))))))))))
      )
    }

}
trait TestTypes {
  import TestTypes._

  implicit val JsonMeta: doobie.imports.Meta[Json] =
    doobie.imports.Meta.other[PGobject]("jsonb").xmap[Json](
      a => Parse.parse(a.getValue).fold(sys.error, identity), // failure raises an exception
      a => {
        val p = new PGobject
        p.setType("jsonb")
        p.setValue(a.nospaces)
        p
      }
    )

  implicit val LocalDateTimeMeta: doobie.imports.Meta[LocalDateTime] =
        doobie.imports.Meta[Timestamp].xmap(_.toLocalDateTime, Timestamp.valueOf)

  implicit val LocalDateMeta: doobie.imports.Meta[LocalDate] =
        doobie.imports.Meta[Date].xmap(_.toLocalDate, Date.valueOf)

  def create(bigintT: Option[Long] = None, booleanT: Option[Boolean] = None, doubleT: Option[Double] = None, integerT: Option[Int] = None, textT: Option[String] = None, timestampT: Option[LocalDateTime] = None, timestampzT: Option[Timestamp] = None, jsonbT: Option[argonaut.Json] = None, dateT: Option[LocalDate] = None, timeT: Option[Time] = None, shortT: Option[Short] = None, uuidT: Option[UUID] = None): ConnectionIO[Row] = {
    create(Shape(bigintT, booleanT, doubleT, integerT, textT, timestampT, timestampzT, jsonbT, dateT, timeT, shortT, uuidT))
  }

  def createVoid(bigintT: Option[Long] = None, booleanT: Option[Boolean] = None, doubleT: Option[Double] = None, integerT: Option[Int] = None, textT: Option[String] = None, timestampT: Option[LocalDateTime] = None, timestampzT: Option[Timestamp] = None, jsonbT: Option[argonaut.Json] = None, dateT: Option[LocalDate] = None, timeT: Option[Time] = None, shortT: Option[Short] = None, uuidT: Option[UUID] = None): ConnectionIO[Unit] = {
    createVoid(Shape(bigintT, booleanT, doubleT, integerT, textT, timestampT, timestampzT, jsonbT, dateT, timeT, shortT, uuidT))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestTypes.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_types (bigint_t, boolean_t, double_t, integer_t, text_t, timestamp_t, timestampz_t, jsonb_t, date_t, time_t, short_t, uuid_t) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestTypes.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("bigint_t", "boolean_t", "double_t", "integer_t", "text_t", "timestamp_t", "timestampz_t", "jsonb_t", "date_t", "time_t", "short_t", "uuid_t")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestTypes.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestTypes.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestTypes.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestTypes.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_types
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
      FROM test_types
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
