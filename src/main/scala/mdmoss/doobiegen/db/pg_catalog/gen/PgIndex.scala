package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgIndex extends PgIndex {

  case class Row(
    indnatts: Short,
    indnkeyatts: Short,
    indisunique: Boolean,
    indisprimary: Boolean,
    indisexclusion: Boolean,
    indimmediate: Boolean,
    indisclustered: Boolean,
    indisvalid: Boolean,
    indcheckxmin: Boolean,
    indisready: Boolean,
    indislive: Boolean,
    indisreplident: Boolean
  ) {
    def toShape: Shape = Shape.NoDefaults(indnatts, indnkeyatts, indisunique, indisprimary, indisexclusion, indimmediate, indisclustered, indisvalid, indcheckxmin, indisready, indislive, indisreplident)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"indnatts, indnkeyatts, indisunique, indisprimary, indisexclusion, indimmediate, indisclustered, indisvalid, indcheckxmin, indisready, indislive, indisreplident"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".indnatts, " ++ Fragment.const0(a) ++ fr".indnkeyatts, " ++ Fragment.const0(a) ++ fr".indisunique, " ++ Fragment.const0(a) ++ fr".indisprimary, " ++ Fragment.const0(a) ++ fr".indisexclusion, " ++ Fragment.const0(a) ++ fr".indimmediate, " ++ Fragment.const0(a) ++ fr".indisclustered, " ++ Fragment.const0(a) ++ fr".indisvalid, " ++ Fragment.const0(a) ++ fr".indcheckxmin, " ++ Fragment.const0(a) ++ fr".indisready, " ++ Fragment.const0(a) ++ fr".indislive, " ++ Fragment.const0(a) ++ fr".indisreplident"
  }

  case class Shape(indnatts: Short, indnkeyatts: Short, indisunique: Boolean, indisprimary: Boolean, indisexclusion: Boolean, indimmediate: Boolean, indisclustered: Boolean, indisvalid: Boolean, indcheckxmin: Boolean, indisready: Boolean, indislive: Boolean, indisreplident: Boolean)

  object Shape {
    def NoDefaults(indnatts: Short, indnkeyatts: Short, indisunique: Boolean, indisprimary: Boolean, indisexclusion: Boolean, indimmediate: Boolean, indisclustered: Boolean, indisvalid: Boolean, indcheckxmin: Boolean, indisready: Boolean, indislive: Boolean, indisreplident: Boolean): Shape = Shape(indnatts, indnkeyatts, indisunique, indisprimary, indisexclusion, indimmediate, indisclustered, indisvalid, indcheckxmin, indisready, indislive, indisreplident)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta))))))))))))

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
        (row) => (row.indnatts, (row.indnkeyatts, (row.indisunique, (row.indisprimary, (row.indisexclusion, (row.indimmediate, (row.indisclustered, (row.indisvalid, (row.indcheckxmin, (row.indisready, (row.indislive, (row.indisreplident))))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta))))))))))))

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
        (row) => (row.indnatts, (row.indnkeyatts, (row.indisunique, (row.indisprimary, (row.indisexclusion, (row.indimmediate, (row.indisclustered, (row.indisvalid, (row.indcheckxmin, (row.indisready, (row.indislive, (row.indisreplident))))))))))))
      )
    }

}
trait PgIndex {
  import PgIndex._

  def create(indnatts: Short, indnkeyatts: Short, indisunique: Boolean, indisprimary: Boolean, indisexclusion: Boolean, indimmediate: Boolean, indisclustered: Boolean, indisvalid: Boolean, indcheckxmin: Boolean, indisready: Boolean, indislive: Boolean, indisreplident: Boolean): ConnectionIO[Row] = {
    create(Shape(indnatts, indnkeyatts, indisunique, indisprimary, indisexclusion, indimmediate, indisclustered, indisvalid, indcheckxmin, indisready, indislive, indisreplident))
  }

  def createVoid(indnatts: Short, indnkeyatts: Short, indisunique: Boolean, indisprimary: Boolean, indisexclusion: Boolean, indimmediate: Boolean, indisclustered: Boolean, indisvalid: Boolean, indcheckxmin: Boolean, indisready: Boolean, indislive: Boolean, indisreplident: Boolean): ConnectionIO[Unit] = {
    createVoid(Shape(indnatts, indnkeyatts, indisunique, indisprimary, indisexclusion, indimmediate, indisclustered, indisvalid, indcheckxmin, indisready, indislive, indisreplident))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgIndex.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_index (indnatts, indnkeyatts, indisunique, indisprimary, indisexclusion, indimmediate, indisclustered, indisvalid, indcheckxmin, indisready, indislive, indisreplident) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgIndex.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("indnatts", "indnkeyatts", "indisunique", "indisprimary", "indisexclusion", "indimmediate", "indisclustered", "indisvalid", "indcheckxmin", "indisready", "indislive", "indisreplident")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgIndex.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgIndex.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgIndex.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgIndex.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_index
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
      FROM pg_catalog.pg_index
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
