package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgDatabase extends PgDatabase {

  case class Row(
    encoding: Int,
    datistemplate: Boolean,
    datallowconn: Boolean,
    datconnlimit: Int
  ) {
    def toShape: Shape = Shape.NoDefaults(encoding, datistemplate, datallowconn, datconnlimit)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"encoding, datistemplate, datallowconn, datconnlimit"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".encoding, " ++ Fragment.const0(a) ++ fr".datistemplate, " ++ Fragment.const0(a) ++ fr".datallowconn, " ++ Fragment.const0(a) ++ fr".datconnlimit"
  }

  case class Shape(encoding: Int, datistemplate: Boolean, datallowconn: Boolean, datconnlimit: Int)

  object Shape {
    def NoDefaults(encoding: Int, datistemplate: Boolean, datallowconn: Boolean, datconnlimit: Int): Shape = Shape(encoding, datistemplate, datallowconn, datconnlimit)
  }

    private val zippedRowComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.encoding, (row.datistemplate, (row.datallowconn, (row.datconnlimit))))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.BooleanMeta)
    .zip(Composite.fromMeta(doobie.util.meta.Meta.IntMeta))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2),
        (row) => (row.encoding, (row.datistemplate, (row.datallowconn, (row.datconnlimit))))
      )
    }

}
trait PgDatabase {
  import PgDatabase._

  def create(encoding: Int, datistemplate: Boolean, datallowconn: Boolean, datconnlimit: Int): ConnectionIO[Row] = {
    create(Shape(encoding, datistemplate, datallowconn, datconnlimit))
  }

  def createVoid(encoding: Int, datistemplate: Boolean, datallowconn: Boolean, datconnlimit: Int): ConnectionIO[Unit] = {
    createVoid(Shape(encoding, datistemplate, datallowconn, datconnlimit))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgDatabase.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_database (encoding, datistemplate, datallowconn, datconnlimit) VALUES (?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgDatabase.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("encoding", "datistemplate", "datallowconn", "datconnlimit")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgDatabase.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgDatabase.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgDatabase.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgDatabase.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_database
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
      FROM pg_catalog.pg_database
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
