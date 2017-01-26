package mdmoss.doobiegen

object StatementTypes {

  sealed trait Statement

  object Get      extends Statement
  object MultiGet extends Statement
  object Find     extends Statement

  object CreateMany extends Statement

  object Update extends Statement

  object All   extends Statement
  object Count extends Statement

  val Create = CreateMany
}
