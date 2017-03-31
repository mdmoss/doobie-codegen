package mdmoss.doobiegen

object GenOptions {

  sealed trait GenOption

  case object NoWrite             extends GenOption
  case object AlwaysInsertDefault extends GenOption
  case object IgnoreDefault       extends GenOption
  case object Ignore              extends GenOption
}
