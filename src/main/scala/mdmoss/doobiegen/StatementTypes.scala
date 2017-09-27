package mdmoss.doobiegen

object StatementTypes {

  sealed trait Statement

  object Get      extends Statement
  object Find     extends Statement

  object CreateMany extends Statement

  object Update extends Statement

  object All   extends Statement
  object Count extends Statement

  case class RefinedMultiget(excludeColumns: Option[List[String]]) extends Statement

  val Create: Statement = CreateMany
  val MultiGet: Statement = RefinedMultiget(excludeColumns = None)
}
