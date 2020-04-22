package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatioUserSequences extends PgStatioUserSequences {

  case class Row(
    blksRead: Option[Long],
    blksHit: Option[Long]
  ) {
    def toShape: Shape = Shape.NoDefaults(blksRead, blksHit)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"blks_read, blks_hit"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".blks_read, " ++ Fragment.const0(a) ++ fr".blks_hit"
  }

  case class Shape(blksRead: Option[Long] = None, blksHit: Option[Long] = None)

  object Shape {
    def NoDefaults(blksRead: Option[Long], blksHit: Option[Long]): Shape = Shape(blksRead, blksHit)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.blksRead, (row.blksHit))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.blksRead, (row.blksHit))
      )
    }

}
trait PgStatioUserSequences {
  import PgStatioUserSequences._

  def create(blksRead: Option[Long] = None, blksHit: Option[Long] = None): ConnectionIO[Row] = {
    create(Shape(blksRead, blksHit))
  }

  def createVoid(blksRead: Option[Long] = None, blksHit: Option[Long] = None): ConnectionIO[Unit] = {
    createVoid(Shape(blksRead, blksHit))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatioUserSequences.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_statio_user_sequences (blks_read, blks_hit) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatioUserSequences.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("blks_read", "blks_hit")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatioUserSequences.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatioUserSequences.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatioUserSequences.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatioUserSequences.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_statio_user_sequences
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
      FROM pg_catalog.pg_statio_user_sequences
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
