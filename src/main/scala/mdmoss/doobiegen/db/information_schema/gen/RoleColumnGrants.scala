package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object RoleColumnGrants extends RoleColumnGrants {

  case class Row(
    grantor: Option[String],
    grantee: Option[String],
    tableCatalog: Option[String],
    tableSchema: Option[String],
    tableName: Option[String],
    columnName: Option[String],
    privilegeType: Option[String],
    isGrantable: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(grantor, grantee, tableCatalog, tableSchema, tableName, columnName, privilegeType, isGrantable)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"grantor, grantee, table_catalog, table_schema, table_name, column_name, privilege_type, is_grantable"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".grantor, " ++ Fragment.const0(a) ++ fr".grantee, " ++ Fragment.const0(a) ++ fr".table_catalog, " ++ Fragment.const0(a) ++ fr".table_schema, " ++ Fragment.const0(a) ++ fr".table_name, " ++ Fragment.const0(a) ++ fr".column_name, " ++ Fragment.const0(a) ++ fr".privilege_type, " ++ Fragment.const0(a) ++ fr".is_grantable"
  }

  case class Shape(grantor: Option[String] = None, grantee: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, columnName: Option[String] = None, privilegeType: Option[String] = None, isGrantable: Option[String] = None)

  object Shape {
    def NoDefaults(grantor: Option[String], grantee: Option[String], tableCatalog: Option[String], tableSchema: Option[String], tableName: Option[String], columnName: Option[String], privilegeType: Option[String], isGrantable: Option[String]): Shape = Shape(grantor, grantee, tableCatalog, tableSchema, tableName, columnName, privilegeType, isGrantable)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2),
        (row) => (row.grantor, (row.grantee, (row.tableCatalog, (row.tableSchema, (row.tableName, (row.columnName, (row.privilegeType, (row.isGrantable))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2),
        (row) => (row.grantor, (row.grantee, (row.tableCatalog, (row.tableSchema, (row.tableName, (row.columnName, (row.privilegeType, (row.isGrantable))))))))
      )
    }

}
trait RoleColumnGrants {
  import RoleColumnGrants._

  def create(grantor: Option[String] = None, grantee: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, columnName: Option[String] = None, privilegeType: Option[String] = None, isGrantable: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(grantor, grantee, tableCatalog, tableSchema, tableName, columnName, privilegeType, isGrantable))
  }

  def createVoid(grantor: Option[String] = None, grantee: Option[String] = None, tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, columnName: Option[String] = None, privilegeType: Option[String] = None, isGrantable: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(grantor, grantee, tableCatalog, tableSchema, tableName, columnName, privilegeType, isGrantable))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.RoleColumnGrants.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.role_column_grants (grantor, grantee, table_catalog, table_schema, table_name, column_name, privilege_type, is_grantable) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.RoleColumnGrants.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("grantor", "grantee", "table_catalog", "table_schema", "table_name", "column_name", "privilege_type", "is_grantable")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.RoleColumnGrants.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.RoleColumnGrants.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.RoleColumnGrants.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.RoleColumnGrants.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.role_column_grants
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
      FROM information_schema.role_column_grants
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
