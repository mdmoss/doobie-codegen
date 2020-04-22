package mdmoss.doobiegen

object sql {

  sealed abstract class Type(val underlyingType: String)
  case object BigInt          extends Type("bigint")
  case object BigSerial       extends Type("bigint")
  case object Boolean         extends Type("boolean")
  case object Date            extends Type("date")
  case object DoublePrecision extends Type("double precision")
  case object Integer         extends Type("int")
  case object Text            extends Type("text")
  case object Time            extends Type("time")
  case object Timestamp       extends Type("timestamp")
  case object TimestampTZ     extends Type("timestamp with timezone")
  case object JsonB           extends Type("jsonb")
  case object Json            extends Type("json")
  case object Geometry        extends Type("geometry")
  case object SmallInt        extends Type("smallint")
  case object Uuid            extends Type("uuid")
  case class  Decimal(precision: Long, scale: Long) extends Type("decimal")

  sealed trait TableProperty

  case class Column(sqlName: String, sqlType: Type, properties: Seq[ColumnProperty]) extends TableProperty {
    def isNullible = properties.contains(Null) || (!properties.contains(NotNull) && !properties.contains(SingularPrimaryKey))
    def references: Option[sql.References] = properties.flatten {
      case r @ References(_, _) => Some(r)
      case _ => None
    }.headOption
    def isSingularPrimaryKey = properties.contains(SingularPrimaryKey)
    def sqlNameInTable(table: Table) = s"${table.ref.fullName}.$sqlName"
  }

  case class CompositePrimaryKey(columnNames: Seq[String]) extends TableProperty

  case class CompositeUnique(columnNames: Seq[String]) extends TableProperty

  case class CompositeForeignKey(
    localColumnNames: Seq[String],
    foreignTable: TableRef,
    foreignColumnNames: Seq[String]) extends TableProperty

  case class TableRef(schema: Option[String], sqlName: String) {
    /* I don't like this name. @todo change this. */
    /* Also, see case notes on Analysis.RowRepsForInsert.sqlColumns. */
    def fullName = schema.map(s => s"${s.toLowerCase}.").getOrElse("") + sqlName.toLowerCase
  }

  sealed trait Statement
  case class CreateTable(table: TableRef, properties: Seq[TableProperty]) extends Statement
  case class CreateSchema(name: String) extends Statement
  case class AlterTable(table: TableRef, action: AlterTableAction) extends Statement
  case class DropTable(table: TableRef) extends Statement

  sealed trait AlterTableAction
  case class AddProperty(tableProperty: TableProperty) extends AlterTableAction
  case class DropColumn(column: String) extends AlterTableAction
  case class SetColumnProperty(column: String, property: sql.ColumnProperty) extends AlterTableAction
  case class DropColumnProperty(column: String, property: sql.ColumnProperty) extends AlterTableAction
  case class ColumnType(column: String, typ: Type) extends AlterTableAction
  case class DropConstraint(name: String) extends AlterTableAction

  case object Ignored extends Statement

  case class Table(ref: TableRef, properties: Seq[TableProperty]) {
    def columns = properties.flatten {
      case c @ Column(_, _, _) => Some(c)
      case _ => None
    }.toList

    val singularPrimaryKeyColumns = columns.filter(_.isSingularPrimaryKey)
    val nonSingularPrimaryKeyColumns = columns.filterNot(_.isSingularPrimaryKey)
  }

  sealed trait ColumnProperty
  case object Null                                       extends ColumnProperty
  case object NotNull                                    extends ColumnProperty
  /** Column is the only primary key for the table */
  case object SingularPrimaryKey                         extends ColumnProperty
  case object Default                                    extends ColumnProperty
  case class References(table: TableRef, column: String) extends ColumnProperty
  case object Unique                                     extends ColumnProperty
  case object Constraint                                 extends ColumnProperty
}
