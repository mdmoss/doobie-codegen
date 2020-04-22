package mdmoss.doobiegen.db.pg_catalog.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object PgFileSettings extends PgFileSettings {

  case class Row(
    sourcefile: Option[String],
    sourceline: Option[Int],
    seqno: Option[Int],
    name: Option[String],
    setting: Option[String],
    applied: Option[Boolean],
    error: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(sourcefile, sourceline, seqno, name, setting, applied, error)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"sourcefile, sourceline, seqno, name, setting, applied, error"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".sourcefile, " ++ Fragment.const0(a) ++ fr".sourceline, " ++ Fragment.const0(a) ++ fr".seqno, " ++ Fragment.const0(a) ++ fr".name, " ++ Fragment.const0(a) ++ fr".setting, " ++ Fragment.const0(a) ++ fr".applied, " ++ Fragment.const0(a) ++ fr".error"
  }

  case class Shape(sourcefile: Option[String] = None, sourceline: Option[Int] = None, seqno: Option[Int] = None, name: Option[String] = None, setting: Option[String] = None, applied: Option[Boolean] = None, error: Option[String] = None)

  object Shape {
    def NoDefaults(sourcefile: Option[String], sourceline: Option[Int], seqno: Option[Int], name: Option[String], setting: Option[String], applied: Option[Boolean], error: Option[String]): Shape = Shape(sourcefile, sourceline, seqno, name, setting, applied, error)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
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
        (row) => (row.sourcefile, (row.sourceline, (row.seqno, (row.name, (row.setting, (row.applied, (row.error)))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.BooleanMeta)
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
        (row) => (row.sourcefile, (row.sourceline, (row.seqno, (row.name, (row.setting, (row.applied, (row.error)))))))
      )
    }

}
trait PgFileSettings {
  import PgFileSettings._

  def create(sourcefile: Option[String] = None, sourceline: Option[Int] = None, seqno: Option[Int] = None, name: Option[String] = None, setting: Option[String] = None, applied: Option[Boolean] = None, error: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(sourcefile, sourceline, seqno, name, setting, applied, error))
  }

  def createVoid(sourcefile: Option[String] = None, sourceline: Option[Int] = None, seqno: Option[Int] = None, name: Option[String] = None, setting: Option[String] = None, applied: Option[Boolean] = None, error: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(sourcefile, sourceline, seqno, name, setting, applied, error))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgFileSettings.Shape]): Update[Shape] = {
    val sql = "INSERT INTO pg_catalog.pg_file_settings (sourcefile, sourceline, seqno, name, setting, applied, error) VALUES (?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgFileSettings.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("sourcefile", "sourceline", "seqno", "name", "setting", "applied", "error")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgFileSettings.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.pg_catalog.gen.PgFileSettings.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgFileSettings.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.pg_catalog.gen.PgFileSettings.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM pg_catalog.pg_file_settings
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
      FROM pg_catalog.pg_file_settings
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
