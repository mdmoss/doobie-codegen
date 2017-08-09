package mdmoss.doobiegen

import mdmoss.doobiegen.sql.{Ignored, _}

case class DbModel(tables: Seq[sql.Table])

/* This can probably be cleaned up a lot using lenses */
object DbModel {

  class UnhandledModelChangeException(message: String) extends Exception(message)

  def empty = DbModel(Seq())

  def update(model: DbModel, sql: Statement): DbModel = sql match {

    case CreateTable(table, props) => model.copy(tables = model.tables :+ Table(table, props))

    case AlterTable(table, AddProperty(prop)) => model.copy(tables = model.tables.map { t => t.ref == table match {
      case true => t.copy(properties = t.properties :+ prop)
      case false => t
    }})

    case AlterTable(table, DropColumn(column)) => model.copy(tables = model.tables.map(t =>
      t.ref.schema == table.schema && t.ref.sqlName == table.sqlName match {
        case true => t.copy(properties = t.properties.filter {
          case Column(name, _, _) if name == column => false
          case _ => true
        })
        case false => t
      }))

    case AlterTable(table, SetColumnProperty(column, property)) => model.copy(tables = model.tables.map { t =>
      t.ref.schema == table.schema && t.ref.sqlName == table.sqlName match {
        case true => t.copy(properties = t.properties.map {
          case c@Column(name, _, _) if name == column => c.copy(properties = Seq(property) ++ c.properties)
          case p => p
        })
        case false => t
      }})

    case AlterTable(table, DropColumnProperty(column, property)) => model.copy(tables = model.tables.map { t =>
      t.ref.schema == table.schema && t.ref.sqlName == table.sqlName match {
        case true => t.copy(properties = t.properties.map {
            case c@Column(name, _, _) if name == column => c.copy(properties = c.properties.filterNot(_ == property))
            case p => p
        })
        case false => t
      }})

    case AlterTable(table, ColumnType(column, typ)) => model.copy(tables = model.tables.map { t =>
      t.ref.schema == table.schema && t.ref.sqlName == table.sqlName match {
        case true => t.copy(properties = t.properties.map {
          case c@Column(name, _, _) if name == column => c.copy(sqlType=typ)
          case c => c
        })
        case false => t
      }
    })

    case AlterTable(table, DropConstraint(constraint)) => {
      val isRemovingForeignKey = constraint.endsWith("_fkey")
      val matchesThisTable = constraint.startsWith(table.sqlName)

      val targetColumn = constraint.stripPrefix(table.sqlName + "_").stripSuffix("_fkey")

      if (!isRemovingForeignKey || !matchesThisTable) {
        throw new UnhandledModelChangeException(s"ALTER TABLE can't understand DROP CONSTRAINT $constraint")
      }

      model.copy(tables = model.tables.map { t =>
        t.ref.schema == table.schema && t.ref.sqlName == table.sqlName match {
          case true => t.copy(properties = t.properties.map {
            case c@Column(name, _, _) if name == targetColumn =>
              if (c.properties.count(_.isInstanceOf[References]) != 1) {
                throw new UnhandledModelChangeException(s"ALTER TABLE couldn't find constraint $constraint to drop")
              }
              c.copy(properties = c.properties.filterNot(_.isInstanceOf[References]))
            case c => c
          })
          case false => t
        }
      })
    }

    case DropTable(table) => model.copy(tables = model.tables.filter(_.ref != table))

    /* Statements here have no effect on the model, at present */
    case CreateSchema(_) => model
    case Ignored => model
  }

}