package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgPublication extends PgPublication {

  case class Row(
    puballtables: Boolean,
    pubinsert: Boolean,
    pubupdate: Boolean,
    pubdelete: Boolean,
    pubtruncate: Boolean
  ) {
    def toShape: Shape = Shape.NoDefaults(puballtables, pubinsert, pubupdate, pubdelete, pubtruncate)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"puballtables, pubinsert, pubupdate, pubdelete, pubtruncate"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".puballtables, " ++ Fragment.const0(a) ++ fr".pubinsert, " ++ Fragment.const0(a) ++ fr".pubupdate, " ++ Fragment.const0(a) ++ fr".pubdelete, " ++ Fragment.const0(a) ++ fr".pubtruncate"
  }

  case class Shape(puballtables: Boolean, pubinsert: Boolean, pubupdate: Boolean, pubdelete: Boolean, pubtruncate: Boolean)

  object Shape {
    def NoDefaults(puballtables: Boolean, pubinsert: Boolean, pubupdate: Boolean, pubdelete: Boolean, pubtruncate: Boolean): Shape = Shape(puballtables, pubinsert, pubupdate, pubdelete, pubtruncate)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.puballtables, (row.pubinsert, (row.pubupdate, (row.pubdelete, (row.pubtruncate)))))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.puballtables, (row.pubinsert, (row.pubupdate, (row.pubdelete, (row.pubtruncate)))))
      )
    }

}
trait PgPublication {
  import PgPublication._

  def create(puballtables: Boolean, pubinsert: Boolean, pubupdate: Boolean, pubdelete: Boolean, pubtruncate: Boolean): ConnectionIO[Row] = {
    create(Shape(puballtables, pubinsert, pubupdate, pubdelete, pubtruncate))
  }

  def createVoid(puballtables: Boolean, pubinsert: Boolean, pubupdate: Boolean, pubdelete: Boolean, pubtruncate: Boolean): ConnectionIO[Unit] = {
    createVoid(Shape(puballtables, pubinsert, pubupdate, pubdelete, pubtruncate))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPublication.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_publication (puballtables, pubinsert, pubupdate, pubdelete, pubtruncate) VALUES (?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPublication.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("puballtables", "pubinsert", "pubupdate", "pubdelete", "pubtruncate")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPublication.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPublication.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgPublication.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgPublication.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_publication
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
      FROM pg_catalog.pg_publication
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
