package mdmoss.doobiegen

import mdmoss.doobiegen.sql.Table

object CompositeGen {

  /** Keep in sync with doobie.util.meta.Meta.*. */
  private val metas = Map(
    "Byte" -> "doobie.util.meta.Meta.ByteMeta",
    "Short" -> "doobie.util.meta.Meta.ShortMeta",
    "Int" -> "doobie.util.meta.Meta.IntMeta",
    "Float" -> "doobie.util.meta.Meta.FloatMeta",
    "Long" -> "doobie.util.meta.Meta.LongMeta",
    "Double" -> "doobie.util.meta.Meta.DoubleMeta",
    "java.math.BigDecimal" -> "doobie.util.meta.Meta.BigDecimalMeta",
    "Boolean" -> "doobie.util.meta.Meta.BooleanMeta",
    "String" -> "doobie.util.meta.Meta.StringMeta",
    "Array[Byte]" -> "doobie.util.meta.Meta.ByteArrayMeta",
    "java.sql.Date" -> "doobie.util.meta.Meta.DateMeta",
    "java.sql.Time" -> "doobie.util.meta.Meta.TimeMeta",
    "Timestamp" -> "doobie.util.meta.Meta.TimestampMeta",
    "BigDecimal" -> "doobie.util.meta.Meta.ScalaBigDecimalMeta",
    "java.util.Date" -> "doobie.util.meta.Meta.JavaUtilDateMeta",
    "java.time.Instant" -> "doobie.util.meta.Meta.JavaTimeInstantMeta",
    "java.time.LocalDate" -> "doobie.util.meta.Meta.JavaTimeLocalDateMeta"
  )

  private def composite(field: RowRepField): String = {
    field.scalaType.optionOf match {
      case Some(inner) => metas.get(inner.symbol) match {
        case Some(innerMeta) => s"Composite.fromMetaOption($innerMeta)"
        case None => s"implicitly[Composite[${field.scalaType.qualifiedSymbol}]]" // Summon it
      }
      case None => metas.get(field.scalaType.symbol) match {
        case Some(meta) => s"Composite.fromMeta($meta)" // Use it
        case None => s"implicitly[Composite[${field.scalaType.qualifiedSymbol}]]" // Summon it
      }
    }
  }

  def id(g: Generator, t: Table): String = {
    // Two cases: Either the ID wraps something there's a Meta for and we can handle it directly, or it doesn't and we
    // have to summon it. We can just check if we know about the meta for it.

    g.a.pkNewType(t) match {
      case None => "" // No Id newtype, no meta ¯\_(ツ)_/¯
      case Some(newType) =>

        val idSymbol = newType._2.symbol

        s"""
          |implicit def ${g.a.targetObject(t)}IdComposite: Composite[$idSymbol] = {
          |  ${composite(newType._1.head)}.xmap(
          |    (f1) => $idSymbol(f1),
          |    (a) => a.value
          |  )
          |}
        """.stripMargin
    }
  }

  def row(g: Generator, t: Table): String = {

    val row = g.a.rowNewType(t)

    val composites = row._1.map(composite)

    val combinedMeta = composites.mkString("\n.zip(") ++ (")" * (composites.length - 1))

    val zipped = s"private val zippedRowComposite = $combinedMeta"

    val tupleAccessors = row._1.zipWithIndex.map { case (_, i) => "t." + ("_2." * i) + "_1" }.mkString(",\n").stripSuffix("._1")

    val rowConstructor = s"${row._2.symbol}(" + tupleAccessors + ")"

    val tupleConstructor = "(row." + row._1.map(_.scalaName).mkString(", (row.") + (")" * row._1.length)

    val rowComposite =
      s"""
         |implicit def RowComposite: Composite[${row._2.symbol}] = {
         |  zippedRowComposite.xmap(
         |    t => $rowConstructor,
         |    (row) => $tupleConstructor
         |  )
         |}
       """.stripMargin

    s"""
       |$zipped
       |$rowComposite
     """.stripMargin
  }

  /** Todo: merge this with row(2) above. */
  def shape(g: Generator, t: Table): String = {

    val row = g.a.rowShape(t)

    if (row._1.isEmpty) {
      ""
    } else {
      val composites = row._1.map(composite)

      val combinedMeta = composites.mkString("\n.zip(") ++ (")" * (composites.length - 1))

      val zipped = s"private val zippedShapeComposite = $combinedMeta"

      val tupleAccessors = row._1.zipWithIndex.map { case (_, i) => "t." + ("_2." * i) + "_1" }.mkString(",\n").stripSuffix("._1")

      val shapeConstructor = s"${row._2.symbol}(" + tupleAccessors + ")"

      val tupleConstructor = "(row." + row._1.map(_.scalaName).mkString(", (row.") + (")" * row._1.length)

      val shapeComposite =
        s"""
           |implicit def ShapeComposite: Composite[${row._2.symbol}] = {
           |  zippedShapeComposite.xmap(
           |    t => $shapeConstructor,
           |    (row) => $tupleConstructor
           |  )
           |}
       """.stripMargin

      s"""
         |$zipped
         |$shapeComposite
     """.stripMargin
    }
  }
}