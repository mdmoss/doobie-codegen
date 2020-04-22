package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object SpatialRefSys extends SpatialRefSys {

  case class Srid(value: Int)

  case class Row(
    srid: mdmoss.doobiegen.db.gen.SpatialRefSys.Srid,
    authName: Option[String],
    authSrid: Option[Int],
    srtext: Option[String],
    proj4text: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(srid.value, authName, authSrid, srtext, proj4text)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"srid, auth_name, auth_srid, srtext, proj4text"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".srid, " ++ Fragment.const0(a) ++ fr".auth_name, " ++ Fragment.const0(a) ++ fr".auth_srid, " ++ Fragment.const0(a) ++ fr".srtext, " ++ Fragment.const0(a) ++ fr".proj4text"
  }

  case class Shape(srid: Int, authName: Option[String] = None, authSrid: Option[Int] = None, srtext: Option[String] = None, proj4text: Option[String] = None)

  object Shape {
    def NoDefaults(srid: Int, authName: Option[String], authSrid: Option[Int], srtext: Option[String], proj4text: Option[String]): Shape = Shape(srid, authName, authSrid, srtext, proj4text)
  }

    implicit def SpatialRefSysIdComposite: Composite[Srid] = {
      Composite.fromMeta(doobie.util.meta.Meta.IntMeta).xmap(
        (f1) => Srid(f1),
        (a) => a.value
      )
    }

    private val zippedRowComposite = implicitly[Composite[mdmoss.doobiegen.db.gen.SpatialRefSys.Srid]]
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.srid, (row.authName, (row.authSrid, (row.srtext, (row.proj4text)))))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.srid, (row.authName, (row.authSrid, (row.srtext, (row.proj4text)))))
      )
    }

}
trait SpatialRefSys {
  import SpatialRefSys._

  def create(srid: Int, authName: Option[String] = None, authSrid: Option[Int] = None, srtext: Option[String] = None, proj4text: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(srid, authName, authSrid, srtext, proj4text))
  }

  def createVoid(srid: Int, authName: Option[String] = None, authSrid: Option[Int] = None, srtext: Option[String] = None, proj4text: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(srid, authName, authSrid, srtext, proj4text))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.SpatialRefSys.Shape]): Update[Shape] = {
    val sql = "INSERT INTO spatial_ref_sys (srid, auth_name, auth_srid, srtext, proj4text) VALUES (?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.SpatialRefSys.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("srid", "auth_name", "auth_srid", "srtext", "proj4text")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.SpatialRefSys.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.SpatialRefSys.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.SpatialRefSys.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.SpatialRefSys.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def getInner(srid: mdmoss.doobiegen.db.gen.SpatialRefSys.Srid): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM spatial_ref_sys
      WHERE spatial_ref_sys.srid = ${srid}
    """).query[Row]
  }
  def get(srid: mdmoss.doobiegen.db.gen.SpatialRefSys.Srid): ConnectionIO[Row] = {
    getInner(srid).unique
  }

  private[gen] def findInner(srid: Int): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM spatial_ref_sys
      WHERE spatial_ref_sys.srid = ${srid}
    """).query[Row]
  }
  def find(srid: Int): ConnectionIO[Option[Row]] = {
    findInner(srid).option
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM spatial_ref_sys
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
      FROM spatial_ref_sys
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

  private[gen] def multigetInnerBase(srid: Option[Seq[mdmoss.doobiegen.db.gen.SpatialRefSys.Srid]]): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM spatial_ref_sys
      WHERE (${srid.isEmpty} OR spatial_ref_sys.srid = ANY(${{srid}.toSeq.flatten.map(_.value).toArray}))
    """).query[Row]
  }

  def multiget(srid: Seq[mdmoss.doobiegen.db.gen.SpatialRefSys.Srid]): ConnectionIO[List[Row]] = {
    if (srid.nonEmpty) {
      val distinctValues = srid.distinct
      for {
        resultRaw    <- multigetInnerBase(Some(distinctValues)).list
        resultGrouped = resultRaw.groupBy(_.srid)
      } yield srid.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }

  private[gen] def updateInner(row: mdmoss.doobiegen.db.gen.SpatialRefSys.Row): Update0 = {
    sql"""
      UPDATE spatial_ref_sys
      SET srid = ${row.srid}, auth_name = ${row.authName}, auth_srid = ${row.authSrid}, srtext = ${row.srtext}, proj4text = ${row.proj4text}
      WHERE srid = ${row.srid}
    """.update
  }
  def update(row: mdmoss.doobiegen.db.gen.SpatialRefSys.Row): ConnectionIO[Int] = {
    updateInner(row).run
  }

}
