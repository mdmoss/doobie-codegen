package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgTsDict extends PgTsDict {

  case class Row(
    dictinitoption: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(dictinitoption)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"dictinitoption"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".dictinitoption"
  }

  case class Shape(dictinitoption: Option[String] = None)

  object Shape {
    def NoDefaults(dictinitoption: Option[String]): Shape = Shape(dictinitoption)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t),
        (row) => (row.dictinitoption)
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t),
        (row) => (row.dictinitoption)
      )
    }

}
trait PgTsDict {
  import PgTsDict._

  def create(dictinitoption: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(dictinitoption))
  }

  def createVoid(dictinitoption: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(dictinitoption))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTsDict.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_ts_dict (dictinitoption) VALUES (?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTsDict.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("dictinitoption")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTsDict.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgTsDict.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgTsDict.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgTsDict.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_ts_dict
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
      FROM pg_catalog.pg_ts_dict
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
