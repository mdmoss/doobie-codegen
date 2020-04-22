package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object DataTypePrivileges extends DataTypePrivileges {

  case class Row(
    objectCatalog: Option[String],
    objectSchema: Option[String],
    objectName: Option[String],
    objectType: Option[String],
    dtdIdentifier: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(objectCatalog, objectSchema, objectName, objectType, dtdIdentifier)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"object_catalog, object_schema, object_name, object_type, dtd_identifier"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".object_catalog, " ++ Fragment.const0(a) ++ fr".object_schema, " ++ Fragment.const0(a) ++ fr".object_name, " ++ Fragment.const0(a) ++ fr".object_type, " ++ Fragment.const0(a) ++ fr".dtd_identifier"
  }

  case class Shape(objectCatalog: Option[String] = None, objectSchema: Option[String] = None, objectName: Option[String] = None, objectType: Option[String] = None, dtdIdentifier: Option[String] = None)

  object Shape {
    def NoDefaults(objectCatalog: Option[String], objectSchema: Option[String], objectName: Option[String], objectType: Option[String], dtdIdentifier: Option[String]): Shape = Shape(objectCatalog, objectSchema, objectName, objectType, dtdIdentifier)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.objectCatalog, (row.objectSchema, (row.objectName, (row.objectType, (row.dtdIdentifier)))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2),
        (row) => (row.objectCatalog, (row.objectSchema, (row.objectName, (row.objectType, (row.dtdIdentifier)))))
      )
    }

}
trait DataTypePrivileges {
  import DataTypePrivileges._

  def create(objectCatalog: Option[String] = None, objectSchema: Option[String] = None, objectName: Option[String] = None, objectType: Option[String] = None, dtdIdentifier: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(objectCatalog, objectSchema, objectName, objectType, dtdIdentifier))
  }

  def createVoid(objectCatalog: Option[String] = None, objectSchema: Option[String] = None, objectName: Option[String] = None, objectType: Option[String] = None, dtdIdentifier: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(objectCatalog, objectSchema, objectName, objectType, dtdIdentifier))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.DataTypePrivileges.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.data_type_privileges (object_catalog, object_schema, object_name, object_type, dtd_identifier) VALUES (?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.DataTypePrivileges.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("object_catalog", "object_schema", "object_name", "object_type", "dtd_identifier")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.DataTypePrivileges.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.DataTypePrivileges.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.DataTypePrivileges.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.DataTypePrivileges.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.data_type_privileges
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
      FROM information_schema.data_type_privileges
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
