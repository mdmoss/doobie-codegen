package mdmoss.doobiegen

import mdmoss.doobiegen.sql._
import org.parboiled2._

class SqlStatementParser(val input: ParserInput) extends Parser {

  def StatementLine = rule { Statement ~ EOI }

  def Statement = rule (
      CreateTable
    | CreateSchema
    | AlterTable
    | Insert
    | CreateIndex
    | DropTable
    | AlterSequence
    | CreateExtension
    | Begin
    | Commit
    | With
    | CreateView
    | Delete
    | DropView
    | Update
  )

  def CreateTable: Rule1[sql.Statement] = rule {
    ("CREATE "  ~ optional("UNLOGGED ") ~ "TABLE " ~ optional("IF NOT EXISTS ") ~ TableRef ~ '(' ~ OptionalWhitespace ~ zeroOrMore(TableProperty) ~ ");") ~>
       { (ref, columns) => sql.CreateTable(ref, columns) }
  }

  def TableProperty: Rule1[sql.TableProperty] = rule (
      Column
    | CompositePrimaryKey
    | CompositeUnique
    | CompositeForeignKey
  )

  def Column: Rule1[sql.Column] = rule {
    (ValidIdentifier ~ Type ~ zeroOrMore(ColumnProperty ~ optional(' ')) ~ optional(',') ~ OptionalWhitespace) ~>
      { (id, typ, props) => sql.Column(id, typ, props) }
  }

  def CompositePrimaryKey: Rule1[sql.CompositePrimaryKey] = rule {
    ("PRIMARY KEY" ~ optional(' ') ~ '(' ~ oneOrMore(ValidIdentifier ~ optional(',') ~ OptionalWhitespace) ~ ')' ~ optional(',') ~ OptionalWhitespace) ~>
      { pkn: Seq[String] => sql.CompositePrimaryKey(pkn) }
  }

  def CompositeUnique: Rule1[sql.CompositeUnique] = rule {
    ("UNIQUE" ~ optional(' ') ~ '(' ~ oneOrMore(ValidIdentifier ~ optional(',') ~ OptionalWhitespace) ~ ')' ~ optional(',') ~ OptionalWhitespace) ~>
      { pkn: Seq[String] => sql.CompositeUnique(pkn) }
  }

  def CompositeForeignKey: Rule1[sql.CompositeForeignKey] = rule {
    ("FOREIGN KEY" ~ optional(' ') ~ '(' ~ oneOrMore(ValidIdentifier ~ optional(',') ~ OptionalWhitespace) ~ ')' ~ OptionalWhitespace
      ~ "REFERENCES" ~ optional(' ') ~ TableRef ~ optional(' ') ~ '(' ~ oneOrMore(ValidIdentifier ~ optional(',') ~ OptionalWhitespace) ~ ')'
      ~ optional(',') ~ OptionalWhitespace) ~>
      { sql.CompositeForeignKey.apply _ }
  }


  /* Note: all of these must be lowercase, and this isn't enforced by parboiled :/ */
  def Type: Rule1[sql.Type] = rule {(
      ignoreCase("bigint") ~                       push(sql.BigInt)
        | ignoreCase("bigserial") ~                push(sql.BigSerial)
        | ignoreCase("boolean") ~                  push(sql.Boolean)
        | ignoreCase("character varying") ~        push(sql.Text)
        | ignoreCase("date") ~                     push(sql.Date)
        | ignoreCase("double precision") ~         push(sql.DoublePrecision)
        | ignoreCase("integer") ~                  push(sql.Integer)
        | ignoreCase("int") ~                      push(sql.Integer)
        | ignoreCase("numeric") ~ Decimal ~>       { d => d }
        | ignoreCase("decimal") ~ Decimal ~>       { d => d }
        | ignoreCase("text") ~                     push(sql.Text)
        | ignoreCase("timestamp with time zone") ~ push(sql.TimestampTZ)
        | ignoreCase("timestamp") ~                push(sql.Timestamp)
        | ignoreCase("time") ~                     push(sql.Time)
        | ignoreCase("jsonb") ~                    push(sql.JsonB)
        | ignoreCase("json") ~                     push(sql.Json)
        | ignoreCase("geometry") ~                 push(sql.Geometry)
        | ignoreCase("smallint") ~                 push(sql.SmallInt)
        | ignoreCase("uuid") ~                     push(sql.Uuid)
      ) ~ OptionalWhitespace
  }

  def defaultChars = CharPredicate.Alpha ++ '_' ++ '-' ++ CharPredicate.Digit ++ '(' ++ ')' ++ ' ' ++ ',' ++ '\''

  /* This rule has to use parens, for an unknown reason */
  def ColumnProperty: Rule1[sql.ColumnProperty] = rule (
    ignoreCase("null") ~ push(sql.Null)
    | ignoreCase("not null") ~ push(sql.NotNull)
    | ignoreCase("primary key") ~ push(sql.SingularPrimaryKey)
      /* Our handling of default is getting messier, but it should be replacable all at once later */
    | ignoreCase("default") ~ " " ~ optional('(') ~ oneOrMore(defaultChars) ~ optional(')') ~ push(sql.Default)
    | ignoreCase("references") ~ " " ~ TableRef ~ "(" ~ ValidIdentifier ~ ")" ~> ((t: TableRef, column: String) => References(t, column))
    | ignoreCase("unique") ~ push(sql.Unique)
    | ignoreCase("constraint") ~ " " ~ ValidIdentifier ~ ignoreCase("check") ~ " (" ~ zeroOrMore(noneOf(")")) ~ ")" ~> { (_: Any) => sql.Constraint }
  )

  /* http://www.postgresql.org/docs/9.4/static/sql-syntax-lexical.html */
  def TableRef: Rule1[sql.TableRef] = rule {
    optional(ValidIdentifier ~ '.') ~ ValidIdentifier ~> { (schema, name) => sql.TableRef(schema, name) }
  }

  def ValidIdentifier: Rule1[String] = rule {
    capture (
      (CharPredicate.Alpha ++ '_') ~ zeroOrMore(CharPredicate.Alpha ++ CharPredicate.Digit ++ '_' ++ '$')
    ) ~ OptionalWhitespace
  }

  def Decimal: Rule1[sql.Decimal] = rule {
    OptionalWhitespace ~ "(" ~ OptionalWhitespace ~
      capture(oneOrMore(CharPredicate.Digit)) ~ OptionalWhitespace ~ "," ~ OptionalWhitespace ~
      capture(oneOrMore(CharPredicate.Digit)) ~ ")" ~> { (precision: String, scale: String) => sql.Decimal(precision.toLong, scale.toLong) }
  }

  def Whitespace = rule { oneOrMore(anyOf(" \n\t")) }
  def OptionalWhitespace = rule { zeroOrMore(anyOf(" \n\t")) }

  def CreateSchema: Rule1[sql.Statement] = rule {
    ("CREATE SCHEMA " ~ optional("IF NOT EXISTS ") ~ ValidIdentifier ~ ';') ~> { (name) => sql.CreateSchema(name) }
  }

  def AlterTable: Rule1[sql.Statement] = rule (
      "ALTER TABLE " ~ TableRef ~ "ADD CONSTRAINT" ~ zeroOrMore(noneOf(";")) ~ ";" ~> ((_: Any) => Ignored)
    | "ALTER TABLE " ~ TableRef ~ "ADD COLUMN " ~ Column ~ ";" ~> ((table: TableRef, column: Column) => sql.AlterTable(table, AddProperty(column)))
    | "ALTER TABLE " ~ TableRef ~ "ADD " ~ Column ~ ";" ~> ((table: TableRef, column: Column) => sql.AlterTable(table, AddProperty(column)))
    | "ALTER TABLE " ~ TableRef ~ "DROP COLUMN " ~ ValidIdentifier ~ ";" ~> ((t: TableRef, column: String) => sql.AlterTable(t, DropColumn(column)))
    | "ALTER TABLE " ~ TableRef ~ "ALTER COLUMN " ~ ValidIdentifier ~ "DROP " ~ ColumnProperty ~ ";" ~>
        ((table: TableRef, column: String, property: ColumnProperty) => sql.AlterTable(table, DropColumnProperty(column, property)))
    | "ALTER TABLE " ~ TableRef ~ "ALTER COLUMN " ~ ValidIdentifier ~ "SET " ~ ColumnProperty ~ ";" ~>
        ((table: TableRef, column: String, property: ColumnProperty) => sql.AlterTable(table, SetColumnProperty(column, property)))
    | "ALTER TABLE " ~ TableRef ~ "ALTER COLUMN " ~ ValidIdentifier ~ "TYPE " ~ Type ~ ";" ~>
        ((table: TableRef, column: String, typ: Type) => sql.AlterTable(table, ColumnType(column, typ)))
    | "ALTER TABLE " ~ TableRef ~ "DROP CONSTRAINT " ~ ValidIdentifier ~ ";" ~> ((table: TableRef, constraint: String) => sql.AlterTable(table, DropConstraint(constraint)))
    | "ALTER TABLE " ~ TableRef ~ "ADD " ~ CompositeForeignKey ~ ";" ~> ((table: TableRef, fkey: CompositeForeignKey) => {
        /* .heads are needed because the parser handles composite foreign keys, but not the generator */
        sql.AlterTable(table, SetColumnProperty(fkey.localColumnNames.head, References(fkey.foreignTable, fkey.foreignColumnNames.head)))
      })
  )

  def Insert: Rule1[sql.Statement] = rule {
    "INSERT" ~ zeroOrMore(noneOf(";")) ~ ";" ~ push(sql.Ignored)
  }

  def CreateIndex: Rule1[sql.Statement] = rule {
    "CREATE INDEX" ~ zeroOrMore(noneOf(";")) ~ ";" ~ push(sql.Ignored)
  }

  def DropTable: Rule1[sql.Statement] = rule {
    "DROP TABLE " ~ TableRef ~ ";" ~> ((table: TableRef) => sql.DropTable(table))
  }

  def AlterSequence: Rule1[sql.Statement] = rule {
    "ALTER SEQUENCE" ~ zeroOrMore(noneOf(";")) ~ ";" ~ push(sql.Ignored)
  }

  def CreateExtension: Rule1[sql.Statement] = rule {
    ignoreCase("create extension") ~ zeroOrMore(noneOf(";")) ~ ";" ~ push(sql.Ignored)
  }

  def Begin = rule { ignoreCase("begin") ~ ";" ~ push(sql.Ignored) }
  def Commit = rule { ignoreCase("commit") ~ ";" ~ push(sql.Ignored) }
  def With = rule { ignoreCase("with") ~ zeroOrMore(noneOf(";")) ~ ";" ~ push(sql.Ignored) }
  def CreateView = rule { ignoreCase("create view") ~ zeroOrMore(noneOf(";")) ~ ";" ~ push(sql.Ignored) }
  def Delete = rule { ignoreCase("delete") ~ zeroOrMore(noneOf(";")) ~ ";" ~ push(sql.Ignored) }
  def DropView = rule { ignoreCase("drop view") ~ zeroOrMore(noneOf(";")) ~ ";" ~ push(sql.Ignored) }
  def Update = rule { ignoreCase("update") ~ zeroOrMore(noneOf(";")) ~ ";" ~ push(sql.Ignored) }
}

