package mdmoss.doobiegen

import mdmoss.doobiegen.GenOptions.Ignore
import mdmoss.doobiegen.Runner.Target
import mdmoss.doobiegen.sql.{Column, Table}

object FilterIgnoredFields {

  /** Removes any columns from the model that are tagged with the `Ignore` property. */
  def apply(model: DbModel, target: Target): DbModel = DbModel(
    model.tables.map { t =>
      filterIgnoredFromTable(t, target.columnOptions.getOrElse(t.ref.fullName, Map()))
    }
  )

  private def filterIgnoredFromTable(table: Table, genOptions: Map[String, List[GenOptions.GenOption]]): Table = {
    val noIgnoredColumns = table.properties.filter {
      case Column(name, _, _) if genOptions.getOrElse(name, Nil).contains(Ignore) => false
      case _ => true
    }
    Table(
      ref = table.ref,
      properties = noIgnoredColumns
    )
  }
}
