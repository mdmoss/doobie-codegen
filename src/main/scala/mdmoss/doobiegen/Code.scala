package mdmoss.doobiegen

import mdmoss.doobiegen.Runner.Target

object Code {

  case class CodegenResult(src: Seq[OutFile], tests: Seq[OutFile])

  def checkTest(o: ObjectPlan, f: FunctionDef): Block = Block(
    s"check(${o.name}.${f.name}(${f.params.map(_.`type`.arb).mkString(", ")}))"
  )

  implicit class indentString(s: String) {
    def indent(nSpaces: Int): String = "  " + s.replace("\n", "\n" + (" " * nSpaces))
    def chomp: String = s.replaceAll("^\\s*", "").replaceAll("\\s*$", "")
  }
}

import Code._

case class OutFile(path: String, name: String, contents: String)

sealed trait CodePart {def pp: String}

case class Import(`package`: String) extends CodePart {
  def pp = s"import ${`package`}"
}

case class FunctionDef(privatePkg: Option[String], name: String, params: Seq[FunctionParam], returnType: String, body: String) extends CodePart {
  def pp = {
    val scope = privatePkg.map(p => s"private[$p] ").getOrElse("")
    val paramsString = params.map { _.renderAsParam }.mkString(", ")

    s"""${scope}def $name($paramsString): $returnType = {
       |${body.chomp.indent(2)}
       |}
     """.stripMargin.chomp
  }
}

case class FunctionParam(name: String, `type`: ScalaType, default: Option[String] = None) {
  def renderAsParam: String = s"$name: ${`type`.qualifiedSymbol}" + default.map(d => s" = $d").getOrElse("")
}

case class CaseClassDef(name: String, fields: Seq[CaseClassField]) extends CodePart {
  def pp = {
    s"case class $name(${fields.map(f => s"${f.name}: ${f.`type`.symbol}").mkString(", ")})"
  }
}
case class CaseClassField(name: String, `type`: ScalaType)

case class Block(body: String) extends CodePart {
  override def pp: String = body
}