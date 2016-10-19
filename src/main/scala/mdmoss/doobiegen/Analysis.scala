package mdmoss.doobiegen

import mdmoss.doobiegen.GenOptions.{AlwaysInsertDefault, GenOption, NoWrite}
import mdmoss.doobiegen.Runner.Target
import mdmoss.doobiegen.sql.{Column, Table, TableRef}

case class RowRepField(sourceTable: Table, source: List[Column], scalaName: String, scalaType: ScalaType, defaultValue: Option[String] = None)

case class Insert(fn: FunctionDef)

case class InsertMany(fn: FunctionDef)

case class Create(fn: FunctionDef)

case class CreateMany(process: FunctionDef, list: FunctionDef)

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
    def camelCase: String = """_([a-z])""".r.replaceAllIn(s, m => m.group(1).capitalize)
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

  def privateScope(table: Table) = target.`package`.split('.').reverse.head

  def resolve(ref: TableRef): Table = model.tables.filter { t =>
    t.ref.schema.map(_.toLowerCase) == ref.schema.map(_.toLowerCase) &&
    t.ref.sqlName.toLowerCase == ref.sqlName.toLowerCase
  }.head

  def columnOptions(table: Table, column: Column): List[GenOption] = {
    target.columnOptions.get(table.ref.fullName).flatMap(_.get(column.sqlName)).toList.flatten
  }

  def isNoWrite(table: Table, column: Column): Boolean = {
    val options = columnOptions(table, column)
    DefaultNoWriteSqlTypes.contains(column.sqlType) || options.contains(NoWrite)
  }

  def isNoInsert(table: Table, column: Column): Boolean = {
    val options = columnOptions(table, column)
    options.contains(AlwaysInsertDefault) || isNoWrite(table, column)
  }

  def isNoWrite(table: Table, rowRepField: RowRepField): Boolean = isNoWrite(table, rowRepField.source.head)
  def isNoInsert(table: Table, rowRepField: RowRepField): Boolean = isNoInsert(table, rowRepField.source.head)

  def pkNewType(table: Table): Option[(List[RowRepField], ScalaType)] = table.primaryKeyColumns match {
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

    val parts = pkPart.toList ++ table.nonPrimaryKeyColumns.map(_.scalaRep(table))
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
    t1.schema.map(_.toLowerCase) == t2.schema.map(_.toLowerCase) &&
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
    r.source.head.references match {
      case Some(sql.References(fTable, fCol)) =>
        val p = paramType(fTable, getColumn(fTable, fCol)).copy(name = r.scalaName)
        r.source.head.isNullible match {
          case true => p.copy(`type` = p.`type`.copy(symbol = s"Option[${p.`type`.qualifiedSymbol}]", "None", None))
          case false => p
        }
      case None =>
        /* In this case, we want to use unwrapped types, not the primary key - so we go back to the original rep */
        val rep = r.source.headOption.map(_.scalaRep(r.sourceTable)).getOrElse(r)
        val defaultIsNull = r.source.headOption.exists(_.isNullible)
        FunctionParam(rep.scalaName, rep.scalaType, default = r.defaultValue)
    }
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
      """.stripMargin.trim

    val fn = FunctionDef(Some(privateScope(table)), "insert", params, "Update0", body)
    Insert(fn)
  }

  def insertMany(table: Table): InsertMany = {

    val shape = rowShape(table)
    val rowType = rowNewType(table)

    val values = rowNewType(table)._1.map(r =>
      if   (isNoInsert(table, r)) "default"
      else                        "?"
    ).mkString(", ")

    val body =
      s"""
        |val sql = "INSERT INTO ${table.ref.fullName} (${rowNewType(table)._1.sqlColumns}) VALUES ($values)"
        |Update[${shape._2.symbol}](sql)
      """.stripMargin

    val param = FunctionParam("values", ScalaType(s"List[${shape._2.qualifiedSymbol}]", "List()", None))
    val fn = FunctionDef(Some(privateScope(table)), "insertMany", List(param), "Update[Shape]", body)
    InsertMany(fn)
  }

  def create(table: Table): Create = {
    val in = insert(table)
    val rowType = rowNewType(table)

    val body =
      s"""
        |  insert(${in.fn.params.map(f => f.name).mkString(", ")})
        |    .withUniqueGeneratedKeys[${rowType._2.symbol}](${rowType._1.flatMap(_.source.map(s => "\"" + s.sqlName.toLowerCase() + "\"")).mkString(", ")})
     """.stripMargin

    val fn = FunctionDef(None, "create", in.fn.params, s"ConnectionIO[${rowType._2.symbol}]", body)
    Create(fn)
  }

  def createMany(table: Table): CreateMany = {
    val im = insertMany(table)
    val rowType = rowNewType(table)

    val insertData = im.fn.params.map(_.name).mkString(", ")
    val columns = rowType._1.flatMap(_.source).map(s => "\"" + s.sqlName.toLowerCase + "\"").mkString(", ")

    val pBody =
      s"""
          insertMany(${im.fn.params.map(_.name).mkString(", ")}).updateManyWithGeneratedKeys[${rowType._2.symbol}]($columns)($insertData)
       """

    val process = FunctionDef(Some(privateScope(table)), "createManyP", im.fn.params, s"scalaz.stream.Process[ConnectionIO, ${rowType._2.symbol}]", pBody)

    val body =
      s"""
         createManyP(${im.fn.params.map(_.name).mkString(", ")}).runLog.map(_.toList)
       """

    val list = FunctionDef(None, "createMany", im.fn.params, s"ConnectionIO[List[${rowType._2.symbol}]]", body)
    CreateMany(process, list)
  }

  def get(table: Table): Option[Get] = pkNewType(table).map { pk =>

    val rowType = rowNewType(table)

    val innerBody =
      s"""sql\"\"\"
         |  SELECT ${rowType._1.sqlColumnsInTable(table)}
         |  FROM ${table.ref.fullName}
         |  WHERE ${pk._1.sqlColumnsInTable(table)} = $${${pk._1.head.source.head.scalaName}}
         |\"\"\".query[${rowType._2.symbol}]
       """.stripMargin

    val inner = FunctionDef(Some(privateScope(table)), "getInner", Seq(FunctionParam(pk._1.head.source.head.scalaName, pk._2)), s"Query0[${rowType._2.symbol}]", innerBody)

    val outerBody =
      s"""
         |getInner(${inner.params.map(_.name).mkString(",")}).unique
       """.stripMargin

    val outer = FunctionDef(None, "get", inner.params, s"ConnectionIO[${rowType._2.symbol}]", outerBody)

    Get(inner, outer)
  }

  def find(table: Table): Option[Find] = pkNewType(table).map { pk =>

    val rowType = rowNewType(table)

    val innerBody =
      s"""sql\"\"\"
          |  SELECT ${rowType._1.sqlColumnsInTable(table)}
          |  FROM ${table.ref.fullName}
          |  WHERE ${pk._1.sqlColumnsInTable(table)} = $${${pk._1.head.source.head.scalaName}}
          |\"\"\".query[${rowType._2.symbol}]
       """.stripMargin

    val params = pk._1 match {
      case p :: Nil => Seq(FunctionParam(p.source.head.scalaName, p.scalaType))
      case ps => ps.zipWithIndex.map { case (p, i) => FunctionParam(p.source.head.scalaName, p.scalaType)}
    }
    val inner = FunctionDef(Some(privateScope(table)), "findInner", params, s"Query0[${rowType._2.symbol}]", innerBody)

    val outerBody = s"""findInner(${params.map(_.name).mkString(", ")}).option"""
    val outer = FunctionDef(None, "find", params, s"ConnectionIO[Option[${rowType._2.symbol}]]", outerBody)

    Find(inner, outer)
  }

  def allUnbounded(table: Table): AllUnbounded = {
    val rowType = rowNewType(table)

    /* This is a super hack, but will do for now */
    val bigintMax = "9223372036854775807L"

    val body =
      s"""allInner(0, $bigintMax).list"""

    val call = FunctionDef(None, "allUnbounded", Seq(), s"ConnectionIO[List[${rowType._2.symbol}]]", body)

    AllUnbounded(call)
  }

  def all(table: Table): All = {
    val rowType = rowNewType(table)
    val offsetLimit = Seq(FunctionParam("offset", ScalaType("Long", "0L", None)), FunctionParam("limit", ScalaType("Long", "0L", None)))

    val innerBody =
      s"""sql\"\"\"
          |  SELECT ${rowType._1.sqlColumnsInTable(table)}
          |  FROM ${table.ref.fullName}
          |  OFFSET $$offset
          |  LIMIT $$limit
          |\"\"\".query[${rowType._2.symbol}]
       """.stripMargin

    val inner = FunctionDef(Some(privateScope(table)), "allInner", offsetLimit, s"Query0[${rowType._2.symbol}]", innerBody)

    val outerBody =
      s"""allInner(offset, limit).list"""

    val outer = FunctionDef(None, "all", offsetLimit, s"ConnectionIO[List[${rowType._2.symbol}]]", outerBody)

    All(inner, outer)
  }

  def count(table: Table): Count = {
    val innerBody =
      s"""sql\"\"\"
          |  SELECT COUNT(*)
          |  FROM ${table.ref.fullName}
          |\"\"\".query[Long]
       """.stripMargin

    val inner = FunctionDef(Some(privateScope(table)), "countInner", Seq(), "Query0[Long]", innerBody)

    val outerBody = s"countInner().unique"
    val outer = FunctionDef(None, "count", Seq(), "ConnectionIO[Long]", outerBody)

    Count(inner, outer)
  }

  def baseMultiget(table: Table): Option[BaseMultiget] = {
    val rowType = rowNewType(table)

    val params = pkNewType(table).map { pk =>
      FunctionParam(pk._1.head.source.head.scalaName, app(app(pk._2, "Seq"), "Option"))
    }.toList ++
    table.columns.flatMap {
      case c @ Column(colName, colType, copProps) if c.reference.isDefined && !c.isNullible && !table.primaryKeyColumns.contains(c) =>
        Seq(FunctionParam(c.scalaName, app(app(c.scalaType, "Seq"), "Option")))

      case _ => Seq()
    }

    val returnType = s"Query0[${rowType._2.symbol}]"

    val joins = (pkNewType(table).map { pk =>
      val arrayName = pk._1.head.source.head.scalaName

      val matchArray = pk._1.head.source.head.reference match {
        case Some(ref) => s"$${{${arrayName}}.toSeq.flatten.map(_.value.value).toArray}"
        case None      => s"$${{${arrayName}}.toSeq.flatten.map(_.value).toArray}"
      }
      val columnType = pk._1.head.source.head.sqlType.underlyingType

      s"LEFT JOIN unnest($matchArray::$columnType[]) WITH ORDINALITY t0(val, ord) ON t0.val = ${pk._1.head.source.head.sqlNameInTable(table)}"
    }.toList ++ table.columns.zipWithIndex.flatMap {
        case (c@Column(colName, colType, copProps), i) if c.reference.isDefined && !c.isNullible && !table.primaryKeyColumns.contains(c) =>
          val matchArray = s"$${{${c.scalaName}}.toSeq.flatten.map(_.value).toArray}"

            Seq(
              s"LEFT JOIN unnest(${matchArray}::${c.sqlType.underlyingType}[]) WITH ORDINALITY t$i(val, ord) ON t$i.val = ${c.sqlNameInTable(table)}"
            )
        case _ => Seq()
        }
      ).mkString("\n")

    val where = "WHERE " + (pkNewType(table).map { pk =>
      val arrayName = pk._1.head.source.head.scalaName

      val matchArray = pk._1.head.source.head.reference match {
        case Some(ref) => s"$${{${arrayName}}.toSeq.flatten.map(_.value.value).toArray}"
        case None => s"$${{${arrayName}}.toSeq.flatten.map(_.value).toArray}"
      }

      s"($${$arrayName.isEmpty} OR ${pk._1.head.source.head.sqlNameInTable(table)} = ANY($matchArray))"
    }.toList ++ table.columns.zipWithIndex.flatMap {
      case (c@Column(colName, colType, copProps), i) if c.reference.isDefined && !c.isNullible && !table.primaryKeyColumns.contains(c) =>
        val matchArray = s"$${{${c.scalaName}}.toSeq.flatten.map(_.value).toArray}"

        Seq(
          s"($${${c.scalaName}.isEmpty} OR ${c.sqlNameInTable(table)} = ANY($matchArray))"
        )
      case _ => Seq()
    }
      ).mkString("\nAND ")

    val orderBy = "ORDER BY " + (pkNewType(table).map(_ => "t0.ord").toList ++
      table.columns.zipWithIndex.flatMap {
        case (c@Column(colName, colType, copProps), i) if c.reference.isDefined && !c.isNullible && !table.primaryKeyColumns.contains(c) =>
          Seq(s"t$i.ord")
        case _ => Seq()
      }
      ).mkString(", ")

    val body =
      s"""sql\"\"\"
        |  SELECT ${rowType._1.sqlColumnsInTable(table)}
        |  FROM ${table.ref.fullName}
        |  $joins
        |  $where
        |  $orderBy
        |\"\"\".query[${rowType._2.symbol}]
        """.stripMargin

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

  def isInMultiget(t: Table, c: Column) = c.reference.isDefined && !c.isNullible && !t.primaryKeyColumns.contains(c)

  def multigets(table: Table): Seq[MultiGet] = {
    val rowType = rowNewType(table)

    /* All of these now call through to the underlying multiget */
    val returnType = s"ConnectionIO[List[${rowType._2.symbol}]]"

    val numPkFields = if (pkNewType(table).isDefined) 1 else 0

    baseMultiget(table).toList.flatMap { base =>

        pkNewType(table).toList.flatMap { pk =>

          val params = pluralise(List(FunctionParam(table.primaryKeyColumns.head.scalaName, pk._2)))

          val baseParams = s"Some(${params.map(_.name).head})" :: List.fill(base.fn.params.length - 1)("None")
          val body = s"multigetInnerBase(${baseParams.mkString(", ")}).list"

          List(
            MultiGet(FunctionDef(None, "multiget", params, returnType, body))
          )

        } ++ pkNewType(table).toList.flatMap { pk =>

          val params = pluralise(List(FunctionParam(table.primaryKeyColumns.head.scalaName, table.primaryKeyColumns.head.scalaType)))

          val baseParams = s"Some(${params.map(_.name).head}.map(${pk._2.symbol}(_)))" :: List.fill(base.fn.params.length - 1)("None")
          val body = s"multigetInnerBase(${baseParams.mkString(", ")}).list"

          if (table.primaryKeyColumns.head.references.isDefined) {
            List(
              MultiGet(FunctionDef(None, s"multigetBy${table.primaryKeyColumns.head.unsafeScalaName.capitalize}", params, returnType, body))
            )
          } else {
            List()
          }

        } ++table.columns.filter(isInMultiget(table, _)).zipWithIndex.flatMap {
          case (c@Column(colName, colType, copProps), i) if c.reference.isDefined && !c.isNullible && !table.primaryKeyColumns.contains(c) =>

            val params = pluralise(List(FunctionParam(c.scalaName, c.scalaType)))

            val paramsBefore = i + numPkFields
            val paramsAfter = base.fn.params.length - (paramsBefore + 1)
            val baseParams = List.fill(paramsBefore)("None") ++ List(s"Some(${params.map(_.name).head})") ++ List.fill(paramsAfter)("None")
            val body = s"multigetInnerBase(${baseParams.mkString(", ")}).list"

            List(MultiGet(FunctionDef(None, s"multigetBy${c.unsafeScalaName.capitalize}", params, returnType, body)))

          case  _ => List()
        } ++ table.columns.filter(isInMultiget(table, _)).zipWithIndex.flatMap {
          case (c@Column(colName, colType, copProps), i) if c.reference.isDefined && !c.isNullible && !table.primaryKeyColumns.contains(c) =>

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
         |  WHERE ${table.primaryKeyColumns.head.sqlName} = $${row.${table.primaryKeyColumns.head.scalaName}}
         |\"\"\".update
         |
       """.stripMargin

    val inner = FunctionDef(Some(privateScope(table)), "updateInner", params, "Update0", innerBody)

    val outerBody =
      s"""updateInner(row).run"""

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
            case sql.Integer         => ScalaType("Int", "0", None)
            case sql.Text            => ScalaType("String", "\"\"", None)
            case sql.Timestamp       => ScalaType("Timestamp", "new Timestamp(0L)", None)
            case sql.JsonB           => ScalaType("Json", "jEmptyObject", Some("argonaut"))
            case sql.Json            => ScalaType("Json", "jEmptyObject", Some("argonaut"))
            case sql.Geometry        => ScalaType("PGgeometry", "new PGgeometry()", None)
            case sql.SmallInt        => ScalaType("Short", "0.toShort", None)
            case sql.Time            => ScalaType("Time", "new Time(0L)", None)
            case sql.Uuid            => ScalaType("UUID", "UUID.randomUUID()", None)
          }
      }

      column.isNullible match {
        case true => ScalaType(s"Option[${base.qualifiedSymbol}]", "None", None)
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
