package mdmoss.doobiegen

import mdmoss.doobiegen.GenOptions.{AlwaysInsertDefault, GenOption, NoWrite, IgnoreDefault}
import mdmoss.doobiegen.Runner.Target
import mdmoss.doobiegen.sql.{Column, Table, TableRef}

case class RowRepField(sourceTable: Table, source: List[Column], scalaName: String, scalaType: ScalaType, defaultValue: Option[String] = None)

case class Insert(fn: FunctionDef)

case class InsertMany(fn: FunctionDef)

case class Create(fn: FunctionDef, void: FunctionDef)

case class CreateMany(process: FunctionDef, list: FunctionDef, void: FunctionDef)

case class Get(inner: FunctionDef, outer: FunctionDef)

case class Find(inner: FunctionDef, outer: FunctionDef)

case class All(inner: FunctionDef, outer: FunctionDef)

case class AllUnbounded(fn: FunctionDef)

case class Count(inner: FunctionDef, outer: FunctionDef)

case class BaseMultiget(fn: FunctionDef)

case class MultiGet(inner: FunctionDef)

case class Update(inner: FunctionDef, outer: FunctionDef)

object Analysis {

  /* Helpers */
  implicit class CamelCaseStrings(s: String) {
    def camelCase: String = "_([a-z])".r.replaceAllIn(s, m => m.group(1).capitalize)
  }

  implicit class RowRepsForInsert(l: List[RowRepField]) {
    /* We lowercase everything here to avoid case issues */
    def sqlColumns: String = l.flatMap(_.source).map(c => c.sqlName.toLowerCase).mkString(", ")
    def sqlColumnsInTable(table: Table) = l.flatMap(_.source).map(c => c.sqlNameInTable(table).toLowerCase).mkString(", ")

    /*
     * Case is an interesting question. According to what I've heard about the issue, we should be lowercasing
     * any table and column names that aren't quoted.
     *
     * See http://stackoverflow.com/questions/20878932/are-postgresql-column-names-case-sensitive
     *
     * I think the correct way to handle this is to do what the database does: Add quotes to the parser, and use either
     * a lowercase string or the exact, quoted string as appropriate. @Todo.
     */
  }

  /** Returns an arbitrary using the given constructor and the arb instance for each type in order */
  def merge(constructor: String, scalaTypes: List[ScalaType]): String = {
    s"$constructor(" + scalaTypes.map(_.qualifiedArb).mkString(", ") + ")"
  }

  def makeSafe(string: String): String = if (ReservedWords.contains(string)) s"`$string`" else string

  val ReservedWords = Seq(
    "type",
    "package"
  )

  val DefaultNoWriteSqlTypes = Seq(
    sql.BigSerial
  )
}

class Analysis(val model: DbModel, val target: Target) {
  import Analysis._

  def targetPackage(table: Table) = target.`package` + table.ref.schema.map(s => s".$s").getOrElse("") + ".gen"

  def targetObject(table: Table) = table.ref.sqlName.camelCase.capitalize

  def fullTarget(table: Table) = targetPackage(table) + "." + targetObject(table)

  def privateScope(table: Table) = "gen"

  def resolve(ref: TableRef): Table = model.tables.find { t =>
    equalRef(t.ref, ref)
  } match {
    case Some(table) => table
    case None =>
      println("Are we missing a table? Couldn't find a ref")
      println(ref)
      assert(false)
      null
  }

  def columnOptions(table: Table, column: Column): List[GenOption] = {
    target.columnOptions.get(table.ref.fullName).flatMap(_.get(column.sqlName)).toList.flatten
  }

  def statementOptions(table: Table): List[StatementTypes.Statement] = {
    target.statements.flatMap(_.get(table.ref.fullName)).toList.flatten ++ target.tableSpecificStatements.getOrElse(table.ref.fullName, List.empty)
  }

  def isNoWrite(table: Table, column: Column): Boolean = {
    val options = columnOptions(table, column)
    DefaultNoWriteSqlTypes.contains(column.sqlType) || options.contains(NoWrite)
  }

  def isNoInsert(table: Table, column: Column): Boolean = {
    val options = columnOptions(table, column)
    (options.contains(AlwaysInsertDefault) || isNoWrite(table, column)) && !options.contains(IgnoreDefault)
  }

  def isNoWrite(table: Table, rowRepField: RowRepField): Boolean = isNoWrite(table, rowRepField.source.head)
  def isNoInsert(table: Table, rowRepField: RowRepField): Boolean = isNoInsert(table, rowRepField.source.head)

  def pkNewType(table: Table): Option[(List[RowRepField], ScalaType)] = table.columns.filter(_.isSingularPrimaryKey) match {
    case Nil      => None
    case c :: Nil =>
      val rep = c.scalaRep(table).copy(scalaName = "value")
      val symbol = c.sqlName.camelCase.capitalize
      val arb = merge(symbol, List(c.scalaType))
      Some(List(rep), ScalaType(symbol, arb, Some(fullTarget(table))))
    case cs       => None
  }

  def rowNewType(table: Table): (List[RowRepField], ScalaType) = {
    /* We'll put the primary key first, if any, then other components */
    val pkPart = pkNewType(table).map {
      case (reps, newType) => reps match {
        case r :: Nil => RowRepField(table, r.source, r.source.head.scalaName, newType)
        case rs       => RowRepField(table, rs.flatMap(_.source), "pk", newType)
      }
    }

    val parts = pkPart.toList ++ table.nonSingularPrimaryKeyColumns.map(_.scalaRep(table))
    val arb = merge("Row", parts.map(_.scalaType))
    (parts, ScalaType("Row", arb, Some(fullTarget(table))))
  }

  /**
    * Generates a `Shape` for a table, which is like a `Row`, except:
    * - Omits columns that are of types listed in `SkipInsert`
    * - Unwraps newtypes to their raw form (for easier inserting)
    * The intended usage of `Shape` is for `createMany` and similar methods.
    *
    * Sometimes Row == Shape, and that's fine. @Todo unify in these cases?
    */
  def rowShape(table: Table): (List[RowRepField], ScalaType) =  {
    val parts = table.columns.filterNot(isNoInsert(table, _)).map(_.scalaRep(table))

    (parts, ScalaType("Shape", merge("Shape", parts.map(_.scalaType)), Some(fullTarget(table))))
  }

  /* We need this to get around casing issues for now. Todo fix this */
  def equalRef(t1: TableRef, t2: TableRef): Boolean = {
    t1.schema.filter(_ != "public").map(_.toLowerCase) == t2.schema.filter(_ != "public").map(_.toLowerCase) &&
    t1.sqlName.toLowerCase == t2.sqlName.toLowerCase
  }

  def getColumn(table: TableRef, name: String): sql.Column = {
    model.tables.filter(t => equalRef(t.ref, table)).head.columns.filter(_.sqlName == name).head
  }

  def getTable(table: TableRef) = model.tables.find(t => equalRef(t.ref, table)).get

  /* Gets the param type used to reference a table and column */
  def paramType(table: TableRef, column: sql.Column): FunctionParam = {
    val source = rowNewType(getTable(table))._1.filter(_.source.contains(column)).head
    FunctionParam(source.scalaName, source.scalaType)
  }

  def getParam(r: RowRepField): FunctionParam = {
    val options = columnOptions(r.sourceTable, r.source.head)
    r.source.head.references match {
      case Some(sql.References(fTable, fCol)) =>
        val p = paramType(fTable, getColumn(fTable, fCol)).copy(name = r.scalaName)
        if (r.source.head.isNullible) {
          val default = if (options.contains(IgnoreDefault)) None else Some("None")
          p.copy(
            `type` = p.`type`.copy(symbol = s"Option[${p.`type`.qualifiedSymbol}]", "None", None),
            default = default
          )
        } else {
          p
        }
      case None =>
        /* In this case, we want to use unwrapped types, not the primary key - so we go back to the original rep */
        val rep = r.source.headOption.map(_.scalaRep(r.sourceTable)).getOrElse(r)

        val default = if (options.contains(IgnoreDefault)) None else r.defaultValue
        FunctionParam(rep.scalaName, rep.scalaType, default = default)
    }
  }

  private def fragmentSelectExpression(table: Table): String = {
    s"""\"\"\" ++ ${rowNewType(table)._2.symbol}.ColumnsFragment ++ sql\"\"\""""
  }

  /* We somehow get information about row sources / values from different places. @Todo unify */
  def insert(table: Table): Insert = {

    val params = rowNewType(table)._1.filterNot(isNoInsert(table, _)).map(getParam)

    val values = rowNewType(table)._1.map(r =>
      if   (isNoInsert(table, r))  "default"
      else                        s"$${${getParam(r).name}}"
    ).mkString(", ")

    val body =
      s"""sql\"\"\"
         |  INSERT INTO ${table.ref.fullName} (${rowNewType(table)._1.sqlColumns})
         |  VALUES ($values)
         |\"\"\".update
         |""".stripMargin.trim

    val fn = FunctionDef(Some(privateScope(table)), "insert", params, "Update0", body)
    Insert(fn)
  }

  def insertMany(table: Table): InsertMany = {
    val shape = rowShape(table)

    val values = rowNewType(table)._1.map(r =>
      if   (isNoInsert(table, r)) "default"
      else                        "?"
    ).mkString(", ")

    val body =
      s"""val sql = "INSERT INTO ${table.ref.fullName} (${rowNewType(table)._1.sqlColumns}) VALUES ($values)"
         |Update[${shape._2.symbol}](sql)
         |""".stripMargin

    val fn = FunctionDef(None, "insertMany", List(), "Update[Shape]", body)
    InsertMany(fn)
  }

  def create(table: Table): Create = {
    val in = insert(table)
    val rowType = rowNewType(table)

    val body =
      s"create(Shape(${in.fn.params.map(f => f.name).mkString(", ")}))"

    val fn = FunctionDef(None, "create", in.fn.params, s"ConnectionIO[${rowType._2.symbol}]", body)

    val vBody =
      s"createVoid(Shape(${in.fn.params.map(f => f.name).mkString(", ")}))"

    val void = FunctionDef(None, "createVoid", in.fn.params, "ConnectionIO[Unit]", vBody)
    Create(fn, void)
  }

  def createMany(table: Table): CreateMany = {
    val im = insertMany(table)
    val shape = rowShape(table)
    val rowType = rowNewType(table)
    val param = FunctionParam("values", ScalaType(s"List[${shape._2.qualifiedSymbol}]", "List()", None))

    val insertData = param.name
    val columns = rowType._1.flatMap(_.source).map(s => "\"" + s.sqlName.toLowerCase + "\"").mkString(", ")

    val pBody =
      s"insertMany.updateManyWithGeneratedKeys[${rowType._2.symbol}]($columns)($insertData)"

    val process = FunctionDef(Some(privateScope(table)), "createManyP", List(param), s"scalaz.stream.Process[ConnectionIO, ${rowType._2.symbol}]", pBody)

    val anyParamsNonEmpty = s"${param.name}.nonEmpty"

    val body =
      s"if ($anyParamsNonEmpty) createManyP(${param.name}).runLog.map(_.toList) else List.empty.point[ConnectionIO]"

    val list = FunctionDef(None, "createMany", List(param), s"ConnectionIO[List[${rowType._2.symbol}]]", body)

    val vBody =
      s"if ($anyParamsNonEmpty) insertMany.updateMany[List]($insertData).map(_ => ()) else ().point[ConnectionIO]"

    val void = FunctionDef(None, "createManyVoid", List(param), "ConnectionIO[Unit]", vBody)
    CreateMany(process, list, void)
  }

  def createShape(table: Table): Create = {
    val shape = rowShape(table)
    val rowType = rowNewType(table)

    val body =
      "createMany(shape :: Nil).map(_.head)"

    val fn = FunctionDef(None, "create", FunctionParam("shape", shape._2, None) :: Nil, s"ConnectionIO[${rowType._2.symbol}]", body)

    val vBody =
      "createManyVoid(shape :: Nil)"

    val void = FunctionDef(None, "createVoid", FunctionParam("shape", shape._2, None) :: Nil, s"ConnectionIO[Unit]", vBody)
    Create(fn, void)
  }

  def get(table: Table, withFragment: Boolean): Option[Get] = pkNewType(table).map { pk =>

    val rowType = rowNewType(table)

    val selectExpression = if (withFragment) {
      fragmentSelectExpression(table)
    } else {
      rowType._1.sqlColumnsInTable(table)
    }

    val innerBody =
      s"""(sql\"\"\"
         |  SELECT $selectExpression
         |  FROM ${table.ref.fullName}
         |  WHERE ${pk._1.sqlColumnsInTable(table)} = $${${pk._1.head.source.head.scalaName}}
         |\"\"\").query[${rowType._2.symbol}]
         |""".stripMargin

    val inner = FunctionDef(Some(privateScope(table)), "getInner", Seq(FunctionParam(pk._1.head.source.head.scalaName, pk._2)), s"Query0[${rowType._2.symbol}]", innerBody)

    val outerBody =
      s"getInner(${inner.params.map(_.name).mkString(",")}).unique"

    val outer = FunctionDef(None, "get", inner.params, s"ConnectionIO[${rowType._2.symbol}]", outerBody)

    Get(inner, outer)
  }

  def find(table: Table, withFragment: Boolean): Option[Find] = pkNewType(table).map { pk =>

    val rowType = rowNewType(table)

    val selectExpression = if (withFragment) {
      fragmentSelectExpression(table)
    } else {
      rowType._1.sqlColumnsInTable(table)
    }

    val innerBody =
      s"""(sql\"\"\"
         |  SELECT $selectExpression
         |  FROM ${table.ref.fullName}
         |  WHERE ${pk._1.sqlColumnsInTable(table)} = $${${pk._1.head.source.head.scalaName}}
         |\"\"\").query[${rowType._2.symbol}]
         |""".stripMargin

    val params = pk._1 match {
      case p :: Nil => Seq(FunctionParam(p.source.head.scalaName, p.scalaType))
      case ps => ps.zipWithIndex.map { case (p, i) => FunctionParam(p.source.head.scalaName, p.scalaType)}
    }
    val inner = FunctionDef(Some(privateScope(table)), "findInner", params, s"Query0[${rowType._2.symbol}]", innerBody)

    val outerBody = s"findInner(${params.map(_.name).mkString(", ")}).option"
    val outer = FunctionDef(None, "find", params, s"ConnectionIO[Option[${rowType._2.symbol}]]", outerBody)

    Find(inner, outer)
  }

  def allUnbounded(table: Table): AllUnbounded = {
    val rowType = rowNewType(table)

    /* This is a super hack, but will do for now */
    val bigintMax = "9223372036854775807L"

    val body =
      s"allInner(0, $bigintMax).list"

    val call = FunctionDef(None, "allUnbounded", Seq(), s"ConnectionIO[List[${rowType._2.symbol}]]", body)

    AllUnbounded(call)
  }

  def all(table: Table, withFragment: Boolean): All = {
    val rowType = rowNewType(table)
    val offsetLimit = Seq(FunctionParam("offset", ScalaType("Long", "0L", None)), FunctionParam("limit", ScalaType("Long", "0L", None)))

    val selectExpression = if (withFragment) {
      fragmentSelectExpression(table)
    } else {
      rowType._1.sqlColumnsInTable(table)
    }

    val innerBody =
      s"""(sql\"\"\"
         |  SELECT $selectExpression
         |  FROM ${table.ref.fullName}
         |  OFFSET $$offset
         |  LIMIT $$limit
         |\"\"\").query[${rowType._2.symbol}]
         |""".stripMargin

    val inner = FunctionDef(Some(privateScope(table)), "allInner", offsetLimit, s"Query0[${rowType._2.symbol}]", innerBody)

    val outerBody =
      "allInner(offset, limit).list"

    val outer = FunctionDef(None, "all", offsetLimit, s"ConnectionIO[List[${rowType._2.symbol}]]", outerBody)

    All(inner, outer)
  }

  def count(table: Table): Count = {
    val innerBody =
      s"""sql\"\"\"
         |  SELECT COUNT(*)
         |  FROM ${table.ref.fullName}
         |\"\"\".query[Long]
         |""".stripMargin

    val inner = FunctionDef(Some(privateScope(table)), "countInner", Seq(), "Query0[Long]", innerBody)

    val outerBody = "countInner().unique"
    val outer = FunctionDef(None, "count", Seq(), "ConnectionIO[Long]", outerBody)

    Count(inner, outer)
  }

  private def unwrapsNeeded(field: RowRepField): Int = {
    field.source.head.references match {
      case None => 1
      case Some(ref) =>
        val lower = model.tables.find { t => equalRef(t.ref, ref.table)}.get
        val lowerField = rowNewType(lower)._1.find(_.source.head.sqlName == ref.column)
        lowerField match {
          case None => 2
          case Some(f) => 1 + unwrapsNeeded(f)
        }
    }
  }

  def baseMultiget(table: Table, withFragment: Boolean): Option[BaseMultiget] = {
    val rowType = rowNewType(table)

    /* Hacky for now */
    val excludedColumns: Set[String] = statementOptions(table).collect {
      case rm: StatementTypes.RefinedMultiget => rm.excludeColumns.getOrElse(Nil)
    }.flatten.toSet

    val multigetColumns = table.columns.filterNot { c => excludedColumns.contains(c.sqlName) }

    val params = pkNewType(table).map { pk =>
      FunctionParam(pk._1.head.source.head.scalaName, app(app(pk._2, "Seq"), "Option"))
    }.toList ++
    multigetColumns.flatMap {
      case c @ Column(colName, colType, copProps) if c.reference.isDefined && !c.isNullible && !c.isSingularPrimaryKey =>
        Seq(FunctionParam(c.scalaName, app(app(c.scalaType, "Seq"), "Option")))

      case _ => Seq()
    }

    val returnType = s"Query0[${rowType._2.symbol}]"

    val where = "WHERE " + (pkNewType(table).map { pk =>
      val arrayName = pk._1.head.source.head.scalaName

      val unwraps = List.fill(unwrapsNeeded(pk._1.head))("value").mkString(".")
      val matchArray = s"$${{${arrayName}}.toSeq.flatten.map(_.$unwraps).toArray}"

      s"($${$arrayName.isEmpty} OR ${pk._1.head.source.head.sqlNameInTable(table)} = ANY($matchArray))"
    }.toList ++ multigetColumns.zipWithIndex.flatMap {
      case (c@Column(colName, colType, copProps), i) if c.reference.isDefined && !c.isNullible && !c.isSingularPrimaryKey =>
        val rowRep = rowType._1.find(_.source.head == c).get
        val unwraps = List.fill(unwrapsNeeded(rowRep) - 1)("value").mkString(".")
        val matchArray = s"$${{${c.scalaName}}.toSeq.flatten.map(_.${unwraps}).toArray}"

        Seq(
          s"($${${c.scalaName}.isEmpty} OR ${c.sqlNameInTable(table)} = ANY($matchArray))"
        )
      case _ => Seq()
    }
      ).mkString("\nAND ")

    val selectExpression = if (withFragment) {
      fragmentSelectExpression(table)
    } else {
      rowType._1.sqlColumnsInTable(table)
    }

    val body =
      s"""(sql\"\"\"
         |  SELECT $selectExpression
         |  FROM ${table.ref.fullName}
         |  $where
         |\"\"\").query[${rowType._2.symbol}]
         |""".stripMargin

    val multiget = FunctionDef(
      Some(privateScope(table)),
      "multigetInnerBase",
      params,
      returnType,
      body
    )

    params.length match {
      case 0 => None
      case _ => Some(BaseMultiget(multiget))
    }
  }

  def isInMultiget(t: Table, c: Column) = {
    /* Hacky for now */
    val excludedColumns: Set[String] = statementOptions(t).collect {
      case rm: StatementTypes.RefinedMultiget => rm.excludeColumns.getOrElse(Nil)
    }.flatten.toSet
    c.reference.isDefined && !c.isNullible && !c.isSingularPrimaryKey && !excludedColumns.contains(c.sqlName)
  }

  private def multigetBody(
    base: BaseMultiget,
    columnScalaName: String,
    paramsBefore: Int,
    paramsAfter: Int,
    finalMap: Option[String],
    groupByInnerValue: Boolean = false
  ): String = {
    val listParamName = columnScalaName
    val baseParams = List.fill(paramsBefore)("None") ++
      List(s"Some(distinctValues" + finalMap.map(f => s".map($f)").getOrElse("") + ")") ++
      List.fill(paramsAfter)("None")


    val groupBy = s"_.$columnScalaName" ++ (if (groupByInnerValue) ".value" else "")

    s"""if ($listParamName.nonEmpty) {
       |  val distinctValues = $listParamName.distinct
       |  for {
       |    resultRaw    <- multigetInnerBase(${baseParams.mkString(", ")}).list
       |    resultGrouped = resultRaw.groupBy($groupBy)
       |  } yield $listParamName.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
       |} else List.empty.point[ConnectionIO]
       |""".stripMargin
  }

  def multigets(table: Table, withFragment: Boolean): Seq[MultiGet] = {
    val rowType = rowNewType(table)

    /* All of these now call through to the underlying multiget */
    val returnType = s"ConnectionIO[List[${rowType._2.symbol}]]"

    val numPkFields = if (pkNewType(table).isDefined) 1 else 0

    baseMultiget(table, withFragment).toList.flatMap { base =>

        pkNewType(table).toList.flatMap { pk =>

          val params = pluralise(List(FunctionParam(table.singularPrimaryKeyColumns.head.scalaName, pk._2)))

          val body = multigetBody(base, params.map(_.name).head, 0, base.fn.params.length - 1, None)

          List(
            MultiGet(FunctionDef(None, "multiget", params, returnType, body))
          )

        } ++ pkNewType(table).toList.flatMap { pk =>

          val params = pluralise(List(FunctionParam(table.singularPrimaryKeyColumns.head.scalaName, table.singularPrimaryKeyColumns.head.scalaType)))

          val body = multigetBody(base, params.map(_.name).head, 0, base.fn.params.length - 1, Some(s"${pk._2.symbol}(_)"), groupByInnerValue = true)

          if (table.singularPrimaryKeyColumns.head.references.isDefined) {
            List(
              MultiGet(FunctionDef(None, s"multigetBy${table.singularPrimaryKeyColumns.head.unsafeScalaName.capitalize}", params, returnType, body))
            )
          } else {
            List()
          }

        } ++table.columns.filter(isInMultiget(table, _)).zipWithIndex.flatMap {
          case (c@Column(colName, colType, copProps), i) if c.reference.isDefined && !c.isNullible && !c.isSingularPrimaryKey =>

            val params = pluralise(List(FunctionParam(c.scalaName, c.scalaType)))

            val paramsBefore = i + numPkFields
            val paramsAfter = base.fn.params.length - (paramsBefore + 1)
            val body = multigetBody(base, params.map(_.name).head, paramsBefore, paramsAfter, None)

            List(MultiGet(FunctionDef(None, s"multigetBy${c.unsafeScalaName.capitalize}", params, returnType, body)))

          case  _ => List()
        } ++ table.columns.filter(isInMultiget(table, _)).zipWithIndex.flatMap {
          case (c@Column(colName, colType, copProps), i) if c.reference.isDefined && !c.isNullible && !c.isSingularPrimaryKey =>

            val params = List(FunctionParam(c.scalaName, c.scalaType))

            val paramsBefore = i + numPkFields
            val paramsAfter = base.fn.params.length - (paramsBefore + 1)
            val baseParams = List.fill(paramsBefore)("None") ++ List(s"Some(Seq(${params.map(_.name).head}))") ++ List.fill(paramsAfter)("None")
            val body = s"multigetInnerBase(${baseParams.mkString(", ")}).list"

            List(MultiGet(FunctionDef(None, s"getBy${c.unsafeScalaName.capitalize}", params, returnType, body)))

          case  _ => List()



        }

    }
  }

  /* This contains some hacks. @Todo fix */
  def update(table: Table): Option[Update] =  pkNewType(table).map { pk =>

    val row = rowNewType(table)
    val params = Seq(FunctionParam("row", row._2))

    val innerUpdates = row._1.map(f =>
      if (isNoWrite(table, f)) s"${f.source.head.sqlName} = ${f.source.head.sqlName}"
      else                      s"${f.source.head.sqlName} = $${row.${f.scalaName}}"
    ).mkString(", ")

    val innerBody =
      s"""sql\"\"\"
         |  UPDATE ${table.ref.fullName}
         |  SET $innerUpdates
         |  WHERE ${table.singularPrimaryKeyColumns.head.sqlName} = $${row.${table.singularPrimaryKeyColumns.head.scalaName}}
         |\"\"\".update
         |""".stripMargin

    val inner = FunctionDef(Some(privateScope(table)), "updateInner", params, "Update0", innerBody)

    val outerBody =
      "updateInner(row).run"

    val outer = FunctionDef(None, "update", params, "ConnectionIO[Int]", outerBody)

    Update(inner, outer)
  }

  /**
    * Turn a singular param into a multiple param.
    *
    * eg, (id: Long, name: String) => (params: Seq(Long, String))
    * eg. (id: Long) => (id: Seq(Long))
    */
  def pluralise(params: List[FunctionParam]): List[FunctionParam] = params match {
    case p :: Nil => List(p.copy(`type` = app(p.`type`, "Seq")))
    case ps => ps
  }

  /**
    * Wraps the given type in some applicative thing.
    */
  def app(t: ScalaType, app: String): ScalaType = ScalaType(s"$app[${t.qualifiedSymbol}]", s"$app(${t.qualifiedArb})", None)


  implicit class ColumnScalaRep(column: sql.Column) {

    def unsafeScalaName: String = column.sqlName.camelCase
    def scalaName: String = makeSafe(unsafeScalaName)

    /* This drops any foreign keys after the first, because I'm not sure if that's even valid */
    def reference = column.properties.flatten {
      case r @ sql.References(_, _) => Some(r)
      case _ => None
    }.headOption


    def scalaType: ScalaType = {
      val base = reference match {
        case Some(sql.References(table, col)) =>
          val targetTable = resolve(table)
          val targetColumn = targetTable.columns.filter(_.sqlName.toLowerCase() == col.toLowerCase).head
          foreignType(targetTable, targetColumn)

        case None =>
          column.sqlType match {
            case sql.BigInt          => ScalaType("Long", "0L", None)
            case sql.BigSerial       => ScalaType("Long", "0L", None)
            case sql.Boolean         => ScalaType("Boolean", "true", None)
            case sql.DoublePrecision => ScalaType("Double", "0.0", None)
            case sql.Date            => ScalaType("LocalDate", "LocalDate.of(1, 1, 1)", None)
            case sql.Integer         => ScalaType("Int", "0", None)
            case sql.Decimal(_, _)   => ScalaType("BigDecimal", "BigDecimal(0)", None)
            case sql.Text            => ScalaType("String", "\"\"", None)
            case sql.TimestampTZ     => ScalaType("Timestamp", "new Timestamp(0L)", None)
            case sql.Timestamp       => ScalaType("LocalDateTime", "LocalDateTime.of(1, 1, 1, 1, 1)", None)
            case sql.JsonB           => ScalaType("Json", "Argonaut.jEmptyObject", Some("argonaut"))
            case sql.Json            => ScalaType("Json", "Argonaut.jEmptyObject", Some("argonaut"))
            case sql.Geometry        => ScalaType("PGgeometry", "new PGgeometry()", None)
            case sql.SmallInt        => ScalaType("Short", "0.toShort", None)
            case sql.Time            => ScalaType("Time", "new Time(0L)", None)
            case sql.Uuid            => ScalaType("UUID", "UUID.randomUUID()", None)
          }
      }

      column.isNullible match {
        case true => ScalaType(s"Option[${base.qualifiedSymbol}]", "None", None, optionOf = Some(base))
        case false => base
      }
    }

    def scalaRep(table: Table) = {
      val options = columnOptions(table, column)

      RowRepField(
        table,
        List(column),
        scalaName,
        scalaType,
        defaultValue = if (column.isNullible) Some("None") else None)
    }
  }

  /* This is the type that should be used by other tables referring to the given column */
  def foreignType(table: Table, column: Column): ScalaType = {

    val pk = pkNewType(table).head

    column.isNullible match {
      case true => ScalaType(s"Option[${pk._2.qualifiedSymbol}]", "None", None)
      case false => pk._2
    }
  }
}
