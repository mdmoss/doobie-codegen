package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgTsConfigMap extends PgTsConfigMap {

  case class Row(
    maptokentype: Int,
    mapseqno: Int
  ) {
    def toShape: Shape = Shape.NoDefaults(maptokentype, mapseqno)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"maptokentype, mapseqno"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".maptokentype, " ++ Fragment.const0(a) ++ fr".mapseqno"
  }

  case class Shape(maptokentype: Int, mapseqno: Int)

  object Shape {
    def NoDefaults(maptokentype: Int, mapseqno: Int): Shape = Shape(maptokentype, mapseqno)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.maptokentype, (row.mapseqno))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.maptokentype, (row.mapseqno))
      )
    }

}
trait PgTsConfigMap {
  import PgTsConfigMap._

  def create(maptokentype: Int, mapseqno: Int): ConnectionIO[Row] = {
    create(Shape(maptokentype, mapseqno))
  }

  def createVoid(maptokentype: Int, mapseqno: Int): ConnectionIO[Unit] = {
    createVoid(Shape(maptokentype, mapseqno))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTsConfigMap.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_ts_config_map (maptokentype, mapseqno) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTsConfigMap.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("maptokentype", "mapseqno")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTsConfigMap.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTsConfigMap.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgTsConfigMap.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgTsConfigMap.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_ts_config_map
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
      FROM pg_catalog.pg_ts_config_map
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
