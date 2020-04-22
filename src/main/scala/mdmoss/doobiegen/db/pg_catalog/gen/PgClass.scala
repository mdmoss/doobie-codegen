package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgClass extends PgClass {

  case class Row(
    relpages: Int,
    relallvisible: Int,
    relhasindex: Boolean,
    relisshared: Boolean,
    relnatts: Short,
    relchecks: Short,
    relhasoids: Boolean,
    relhasrules: Boolean,
    relhastriggers: Boolean,
    relhassubclass: Boolean,
    relrowsecurity: Boolean,
    relforcerowsecurity: Boolean,
    relispopulated: Boolean,
    relispartition: Boolean
  ) {
    def toShape: Shape = Shape.NoDefaults(relpages, relallvisible, relhasindex, relisshared, relnatts, relchecks, relhasoids, relhasrules, relhastriggers, relhassubclass, relrowsecurity, relforcerowsecurity, relispopulated, relispartition)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"relpages, relallvisible, relhasindex, relisshared, relnatts, relchecks, relhasoids, relhasrules, relhastriggers, relhassubclass, relrowsecurity, relforcerowsecurity, relispopulated, relispartition"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".relpages, " ++ Fragment.const0(a) ++ fr".relallvisible, " ++ Fragment.const0(a) ++ fr".relhasindex, " ++ Fragment.const0(a) ++ fr".relisshared, " ++ Fragment.const0(a) ++ fr".relnatts, " ++ Fragment.const0(a) ++ fr".relchecks, " ++ Fragment.const0(a) ++ fr".relhasoids, " ++ Fragment.const0(a) ++ fr".relhasrules, " ++ Fragment.const0(a) ++ fr".relhastriggers, " ++ Fragment.const0(a) ++ fr".relhassubclass, " ++ Fragment.const0(a) ++ fr".relrowsecurity, " ++ Fragment.const0(a) ++ fr".relforcerowsecurity, " ++ Fragment.const0(a) ++ fr".relispopulated, " ++ Fragment.const0(a) ++ fr".relispartition"
  }

  case class Shape(relpages: Int, relallvisible: Int, relhasindex: Boolean, relisshared: Boolean, relnatts: Short, relchecks: Short, relhasoids: Boolean, relhasrules: Boolean, relhastriggers: Boolean, relhassubclass: Boolean, relrowsecurity: Boolean, relforcerowsecurity: Boolean, relispopulated: Boolean, relispartition: Boolean)

  object Shape {
    def NoDefaults(relpages: Int, relallvisible: Int, relhasindex: Boolean, relisshared: Boolean, relnatts: Short, relchecks: Short, relhasoids: Boolean, relhasrules: Boolean, relhastriggers: Boolean, relhassubclass: Boolean, relrowsecurity: Boolean, relforcerowsecurity: Boolean, relispopulated: Boolean, relispartition: Boolean): Shape = Shape(relpages, relallvisible, relhasindex, relisshared, relnatts, relchecks, relhasoids, relhasrules, relhastriggers, relhassubclass, relrowsecurity, relforcerowsecurity, relispopulated, relispartition)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta))))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.relpages, (row.relallvisible, (row.relhasindex, (row.relisshared, (row.relnatts, (row.relchecks, (row.relhasoids, (row.relhasrules, (row.relhastriggers, (row.relhassubclass, (row.relrowsecurity, (row.relforcerowsecurity, (row.relispopulated, (row.relispartition))))))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta))))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.relpages, (row.relallvisible, (row.relhasindex, (row.relisshared, (row.relnatts, (row.relchecks, (row.relhasoids, (row.relhasrules, (row.relhastriggers, (row.relhassubclass, (row.relrowsecurity, (row.relforcerowsecurity, (row.relispopulated, (row.relispartition))))))))))))))
      )
    }

}
trait PgClass {
  import PgClass._

  def create(relpages: Int, relallvisible: Int, relhasindex: Boolean, relisshared: Boolean, relnatts: Short, relchecks: Short, relhasoids: Boolean, relhasrules: Boolean, relhastriggers: Boolean, relhassubclass: Boolean, relrowsecurity: Boolean, relforcerowsecurity: Boolean, relispopulated: Boolean, relispartition: Boolean): ConnectionIO[Row] = {
    create(Shape(relpages, relallvisible, relhasindex, relisshared, relnatts, relchecks, relhasoids, relhasrules, relhastriggers, relhassubclass, relrowsecurity, relforcerowsecurity, relispopulated, relispartition))
  }

  def createVoid(relpages: Int, relallvisible: Int, relhasindex: Boolean, relisshared: Boolean, relnatts: Short, relchecks: Short, relhasoids: Boolean, relhasrules: Boolean, relhastriggers: Boolean, relhassubclass: Boolean, relrowsecurity: Boolean, relforcerowsecurity: Boolean, relispopulated: Boolean, relispartition: Boolean): ConnectionIO[Unit] = {
    createVoid(Shape(relpages, relallvisible, relhasindex, relisshared, relnatts, relchecks, relhasoids, relhasrules, relhastriggers, relhassubclass, relrowsecurity, relforcerowsecurity, relispopulated, relispartition))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgClass.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_class (relpages, relallvisible, relhasindex, relisshared, relnatts, relchecks, relhasoids, relhasrules, relhastriggers, relhassubclass, relrowsecurity, relforcerowsecurity, relispopulated, relispartition) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgClass.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("relpages", "relallvisible", "relhasindex", "relisshared", "relnatts", "relchecks", "relhasoids", "relhasrules", "relhastriggers", "relhassubclass", "relrowsecurity", "relforcerowsecurity", "relispopulated", "relispartition")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgClass.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgClass.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgClass.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgClass.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_class
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
      FROM pg_catalog.pg_class
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
