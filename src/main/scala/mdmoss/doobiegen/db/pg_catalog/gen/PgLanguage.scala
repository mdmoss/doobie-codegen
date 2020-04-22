package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgLanguage extends PgLanguage {

  case class Row(
    lanispl: Boolean,
    lanpltrusted: Boolean
  ) {
    def toShape: Shape = Shape.NoDefaults(lanispl, lanpltrusted)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"lanispl, lanpltrusted"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".lanispl, " ++ Fragment.const0(a) ++ fr".lanpltrusted"
  }

  case class Shape(lanispl: Boolean, lanpltrusted: Boolean)

  object Shape {
    def NoDefaults(lanispl: Boolean, lanpltrusted: Boolean): Shape = Shape(lanispl, lanpltrusted)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.lanispl, (row.lanpltrusted))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.lanispl, (row.lanpltrusted))
      )
    }

}
trait PgLanguage {
  import PgLanguage._

  def create(lanispl: Boolean, lanpltrusted: Boolean): ConnectionIO[Row] = {
    create(Shape(lanispl, lanpltrusted))
  }

  def createVoid(lanispl: Boolean, lanpltrusted: Boolean): ConnectionIO[Unit] = {
    createVoid(Shape(lanispl, lanpltrusted))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgLanguage.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_language (lanispl, lanpltrusted) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgLanguage.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("lanispl", "lanpltrusted")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgLanguage.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgLanguage.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgLanguage.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgLanguage.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_language
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
      FROM pg_catalog.pg_language
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
