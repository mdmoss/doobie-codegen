package mdmoss.doobiegen

object GenOptions {

  sealed trait GenOption

  case object NoWrite                      extends GenOption
  case class ScalaDefault(default: String) extends GenOption
}
