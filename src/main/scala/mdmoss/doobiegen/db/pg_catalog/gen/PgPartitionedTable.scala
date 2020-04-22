package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgPartitionedTable extends PgPartitionedTable {

  case class Row(
    partnatts: Short
  ) {
    def toShape: Shape = Shape.NoDefaults(partnatts)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"partnatts"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".partnatts"
  }

  case class Shape(partnatts: Short)

  object Shape {
    def NoDefaults(partnatts: Short): Shape = Shape(partnatts)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t),
        (row) => (row.partnatts)
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.ShortMeta)

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t),
        (row) => (row.partnatts)
      )
    }

}
trait PgPartitionedTable {
  import PgPartitionedTable._

  def create(partnatts: Short): ConnectionIO[Row] = {
    create(Shape(partnatts))
  }

  def createVoid(partnatts: Short): ConnectionIO[Unit] = {
    createVoid(Shape(partnatts))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPartitionedTable.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_partitioned_table (partnatts) VALUES (?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPartitionedTable.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("partnatts")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPartitionedTable.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgPartitionedTable.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgPartitionedTable.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgPartitionedTable.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_partitioned_table
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
      FROM pg_catalog.pg_partitioned_table
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}