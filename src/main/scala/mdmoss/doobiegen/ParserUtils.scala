package mdmoss.doobiegen

import scala.annotation.tailrec

object ParserUtils {

  implicit class SqlStringHelpers(sql: String) {
    def stripComments: String = {
      """--[^\n]*(\n)?""".r.replaceAllIn(sql, "")
    }

    def splitOnUnescapedSemicolons: Seq[String] = {
      splitOnUnescapedSemicolonsInner(List(), "", sql, 0)
    }

    def offSections = "(?s)-- doobie-codegen: off.*?-- doobie-codegen: on".r

    def removeOffSections: String = {
      offSections.replaceAllIn(sql, "").trim
    }
  }


  /* This has a bad name - Todo rename */
  /* Also, this is broken for escaped quote characters */
  @tailrec
  private def splitOnUnescapedSemicolonsInner(parts: List[String], take: String, rest: String, quotesSeen: Int): List[String] = {
    rest.headOption match {
      /* We're done, because there's nothing left to look at */
      case None       => take match {
        case "" => parts
        case xs => parts ++ List(xs)
      }
      case Some(';')  => quotesSeen % 2 match {
        /* An even number of quotes before a semicolon means a split */
        case 0 => splitOnUnescapedSemicolonsInner(parts ++ List(take + ";"), "", rest.tail, 0)
        /* Otherwise, continue on */
        case 1 => splitOnUnescapedSemicolonsInner(parts, take + ";", rest.tail, quotesSeen)
      }
      case Some(''') => splitOnUnescapedSemicolonsInner(parts, take + "'", rest.tail, quotesSeen + 1)
      case Some(c)   => splitOnUnescapedSemicolonsInner(parts, take + c,   rest.tail, quotesSeen)
    }
  }

}
