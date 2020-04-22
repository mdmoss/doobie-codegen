package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TestGenOptions extends TestGenOptions {

  case class Id(value: Long)

  case class Row(
    id: mdmoss.doobiegen.db.gen.TestGenOptions.Id,
    createdAt: Timestamp,
    thingWithDefault: String,
    otherThingWithDefault: String,
    nullibleThingWithDefault: Option[String],
    testIgnoreDefault: Option[Long]
  ) {
    def toShape: Shape = Shape.NoDefaults(id.value, createdAt, thingWithDefault, otherThingWithDefault, nullibleThingWithDefault, testIgnoreDefault)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"id, created_at, thing_with_default, other_thing_with_default, nullible_thing_with_default, test_ignore_default"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".id, " ++ Fragment.const0(a) ++ fr".created_at, " ++ Fragment.const0(a) ++ fr".thing_with_default, " ++ Fragment.const0(a) ++ fr".other_thing_with_default, " ++ Fragment.const0(a) ++ fr".nullible_thing_with_default, " ++ Fragment.const0(a) ++ fr".test_ignore_default"
  }

  case class Shape(id: Long, createdAt: Timestamp, thingWithDefault: String, otherThingWithDefault: String, nullibleThingWithDefault: Option[String] = None, testIgnoreDefault: Option[Long] = None)

  object Shape {
    def NoDefaults(id: Long, createdAt: Timestamp, thingWithDefault: String, otherThingWithDefault: String, nullibleThingWithDefault: Option[String], testIgnoreDefault: Option[Long]): Shape = Shape(id, createdAt, thingWithDefault, otherThingWithDefault, nullibleThingWithDefault, testIgnoreDefault)
  }

    implicit def TestGenOptionsIdComposite: Composite[Id] = {
      Composite.fromMeta(doobie.util.meta.Meta.LongMeta).xmap(
        (f1) => Id(f1),
        (a) => a.value
      )
    }

    private val zippedRowComposite = implicitly[Composite[mdmoss.doobiegen.db.gen.TestGenOptions.Id]]
    .zip(Composite.fromMeta(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2),
        (row) => (row.id, (row.createdAt, (row.thingWithDefault, (row.otherThingWithDefault, (row.nullibleThingWithDefault, (row.testIgnoreDefault))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.TimestampMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2),
        (row) => (row.id, (row.createdAt, (row.thingWithDefault, (row.otherThingWithDefault, (row.nullibleThingWithDefault, (row.testIgnoreDefault))))))
      )
    }

}
trait TestGenOptions {
  import TestGenOptions._

  def create(id: Long, createdAt: Timestamp, thingWithDefault: String, otherThingWithDefault: String, nullibleThingWithDefault: Option[String] = None, testIgnoreDefault: Option[Long] = None): ConnectionIO[Row] = {
    create(Shape(id, createdAt, thingWithDefault, otherThingWithDefault, nullibleThingWithDefault, testIgnoreDefault))
  }

  def createVoid(id: Long, createdAt: Timestamp, thingWithDefault: String, otherThingWithDefault: String, nullibleThingWithDefault: Option[String] = None, testIgnoreDefault: Option[Long] = None): ConnectionIO[Unit] = {
    createVoid(Shape(id, createdAt, thingWithDefault, otherThingWithDefault, nullibleThingWithDefault, testIgnoreDefault))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestGenOptions.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_gen_options (id, created_at, thing_with_default, other_thing_with_default, nullible_thing_with_default, test_ignore_default) VALUES (?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestGenOptions.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("id", "created_at", "thing_with_default", "other_thing_with_default", "nullible_thing_with_default", "test_ignore_default")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestGenOptions.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestGenOptions.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestGenOptions.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestGenOptions.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def getInner(id: mdmoss.doobiegen.db.gen.TestGenOptions.Id): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_gen_options
      WHERE test_gen_options.id = ${id}
    """).query[Row]
  }
  def get(id: mdmoss.doobiegen.db.gen.TestGenOptions.Id): ConnectionIO[Row] = {
    getInner(id).unique
  }

  private[gen] def findInner(id: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_gen_options
      WHERE test_gen_options.id = ${id}
    """).query[Row]
  }
  def find(id: Long): ConnectionIO[Option[Row]] = {
    findInner(id).option
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_gen_options
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
      FROM test_gen_options
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

  private[gen] def multigetInnerBase(id: Option[Seq[mdmoss.doobiegen.db.gen.TestGenOptions.Id]]): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_gen_options
      WHERE (${id.isEmpty} OR test_gen_options.id = ANY(${{id}.toSeq.flatten.map(_.value).toArray}))
    """).query[Row]
  }

  def multiget(id: Seq[mdmoss.doobiegen.db.gen.TestGenOptions.Id]): ConnectionIO[List[Row]] = {
    if (id.nonEmpty) {
      val distinctValues = id.distinct
      for {
        resultRaw    <- multigetInnerBase(Some(distinctValues)).list
        resultGrouped = resultRaw.groupBy(_.id)
      } yield id.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }

  private[gen] def updateInner(row: mdmoss.doobiegen.db.gen.TestGenOptions.Row): Update0 = {
    sql"""
      UPDATE test_gen_options
      SET id = ${row.id}, created_at = ${row.createdAt}, thing_with_default = ${row.thingWithDefault}, other_thing_with_default = ${row.otherThingWithDefault}, nullible_thing_with_default = ${row.nullibleThingWithDefault}, test_ignore_default = ${row.testIgnoreDefault}
      WHERE id = ${row.id}
    """.update
  }
  def update(row: mdmoss.doobiegen.db.gen.TestGenOptions.Row): ConnectionIO[Int] = {
    updateInner(row).run
  }

}
