package mdmoss.doobiegen

import mdmoss.doobiegen.sql.TableRef

case class CodePlan(objects: Seq[ObjectPlan])

case class ObjectPlan(`package`: String, name: String, code: Seq[PlanPart])

sealed trait PlanPart

case class PKNewtype(columns: Seq[sql.Column], scalaType: ScalaType) extends PlanPart {
  def isComposite = columns.length > 1
}

case class RowRep(table: TableRef, fields: Seq[RowRepField], scalaType: ScalaType) extends PlanPart

/**
  * @param `package` location relative to the target package, including target name, for generated types
  *                  eg. for mdmoss.doobiegen.db.gen.Test.Row: ScalaType("Row", "Row()", "db.gen.Test")
  */
case class ScalaType(symbol: String, arb: String, `package`: Option[String], optionOf: Option[ScalaType] = None) {
  def qualifiedSymbol: String = `package`.map(_ + ".").getOrElse("") + symbol
  def qualifiedArb: String = `package`.map(_ + ".").getOrElse("") + arb
}