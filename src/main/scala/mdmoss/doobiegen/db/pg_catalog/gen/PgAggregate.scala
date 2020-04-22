package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgAggregate extends PgAggregate {

  case class Row(
    aggnumdirectargs: Short,
    aggfinalextra: Boolean,
    aggmfinalextra: Boolean,
    aggtransspace: Int,
    aggmtransspace: Int,
    agginitval: Option[String],
    aggminitval: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(aggnumdirectargs, aggfinalextra, aggmfinalextra, aggtransspace, aggmtransspace, agginitval, aggminitval)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"aggnumdirectargs, aggfinalextra, aggmfinalextra, aggtransspace, aggmtransspace, agginitval, aggminitval"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".aggnumdirectargs, " ++ Fragment.const0(a) ++ fr".aggfinalextra, " ++ Fragment.const0(a) ++ fr".aggmfinalextra, " ++ Fragment.const0(a) ++ fr".aggtransspace, " ++ Fragment.const0(a) ++ fr".aggmtransspace, " ++ Fragment.const0(a) ++ fr".agginitval, " ++ Fragment.const0(a) ++ fr".aggminitval"
  }

  case class Shape(aggnumdirectargs: Short, aggfinalextra: Boolean, aggmfinalextra: Boolean, aggtransspace: Int, aggmtransspace: Int, agginitval: Option[String] = None, aggminitval: Option[String] = None)

  object Shape {
    def NoDefaults(aggnumdirectargs: Short, aggfinalextra: Boolean, aggmfinalextra: Boolean, aggtransspace: Int, aggmtransspace: Int, agginitval: Option[String], aggminitval: Option[String]): Shape = Shape(aggnumdirectargs, aggfinalextra, aggmfinalextra, aggtransspace, aggmtransspace, agginitval, aggminitval)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2),
        (row) => (row.aggnumdirectargs, (row.aggfinalextra, (row.aggmfinalextra, (row.aggtransspace, (row.aggmtransspace, (row.agginitval, (row.aggminitval)))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2),
        (row) => (row.aggnumdirectargs, (row.aggfinalextra, (row.aggmfinalextra, (row.aggtransspace, (row.aggmtransspace, (row.agginitval, (row.aggminitval)))))))
      )
    }

}
trait PgAggregate {
  import PgAggregate._

  def create(aggnumdirectargs: Short, aggfinalextra: Boolean, aggmfinalextra: Boolean, aggtransspace: Int, aggmtransspace: Int, agginitval: Option[String] = None, aggminitval: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(aggnumdirectargs, aggfinalextra, aggmfinalextra, aggtransspace, aggmtransspace, agginitval, aggminitval))
  }

  def createVoid(aggnumdirectargs: Short, aggfinalextra: Boolean, aggmfinalextra: Boolean, aggtransspace: Int, aggmtransspace: Int, agginitval: Option[String] = None, aggminitval: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(aggnumdirectargs, aggfinalextra, aggmfinalextra, aggtransspace, aggmtransspace, agginitval, aggminitval))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAggregate.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_aggregate (aggnumdirectargs, aggfinalextra, aggmfinalextra, aggtransspace, aggmtransspace, agginitval, aggminitval) VALUES (?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAggregate.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("aggnumdirectargs", "aggfinalextra", "aggmfinalextra", "aggtransspace", "aggmtransspace", "agginitval", "aggminitval")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAggregate.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgAggregate.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgAggregate.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgAggregate.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_aggregate
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
      FROM pg_catalog.pg_aggregate
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
