package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgAttrdef extends PgAttrdef {

  case class Row(
    adnum: Short,
    adsrc: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(adnum, adsrc)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"adnum, adsrc"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".adnum, " ++ Fragment.const0(a) ++ fr".adsrc"
  }

  case class Shape(adnum: Short, adsrc: Option[String] = None)

  object Shape {
    def NoDefaults(adnum: Short, adsrc: Option[String]): Shape = Shape(adnum, adsrc)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.adnum, (row.adsrc))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.adnum, (row.adsrc))
      )
    }

}
trait PgAttrdef {
  import PgAttrdef._

  def create(adnum: Short, adsrc: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(adnum, adsrc))
  }

  def createVoid(adnum: Short, adsrc: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(adnum, adsrc))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAttrdef.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_attrdef (adnum, adsrc) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAttrdef.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("adnum", "adsrc")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAttrdef.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAttrdef.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgAttrdef.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgAttrdef.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_attrdef
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
      FROM pg_catalog.pg_attrdef
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
