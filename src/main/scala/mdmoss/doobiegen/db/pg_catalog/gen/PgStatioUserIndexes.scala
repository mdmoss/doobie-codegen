package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgStatioUserIndexes extends PgStatioUserIndexes {

  case class Row(
    idxBlksRead: Option[Long],
    idxBlksHit: Option[Long]
  ) {
    def toShape: Shape = Shape.NoDefaults(idxBlksRead, idxBlksHit)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"idx_blks_read, idx_blks_hit"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".idx_blks_read, " ++ Fragment.const0(a) ++ fr".idx_blks_hit"
  }

  case class Shape(idxBlksRead: Option[Long] = None, idxBlksHit: Option[Long] = None)

  object Shape {
    def NoDefaults(idxBlksRead: Option[Long], idxBlksHit: Option[Long]): Shape = Shape(idxBlksRead, idxBlksHit)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.idxBlksRead, (row.idxBlksHit))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.idxBlksRead, (row.idxBlksHit))
      )
    }

}
trait PgStatioUserIndexes {
  import PgStatioUserIndexes._

  def create(idxBlksRead: Option[Long] = None, idxBlksHit: Option[Long] = None): ConnectionIO[Row] = {
    create(Shape(idxBlksRead, idxBlksHit))
  }

  def createVoid(idxBlksRead: Option[Long] = None, idxBlksHit: Option[Long] = None): ConnectionIO[Unit] = {
    createVoid(Shape(idxBlksRead, idxBlksHit))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatioUserIndexes.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_statio_user_indexes (idx_blks_read, idx_blks_hit) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatioUserIndexes.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("idx_blks_read", "idx_blks_hit")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatioUserIndexes.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgStatioUserIndexes.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatioUserIndexes.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgStatioUserIndexes.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_statio_user_indexes
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
      FROM pg_catalog.pg_statio_user_indexes
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
