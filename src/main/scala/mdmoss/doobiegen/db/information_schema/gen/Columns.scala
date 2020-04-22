package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object Columns extends Columns {

  case class Row(
    tableCatalog: Option[String],
    tableSchema: Option[String],
    tableName: Option[String],
    columnName: Option[String],
    ordinalPosition: Option[Int],
    columnDefault: Option[String],
    isNullable: Option[String],
    dataType: Option[String],
    characterMaximumLength: Option[Int],
    characterOctetLength: Option[Int],
    numericPrecision: Option[Int],
    numericPrecisionRadix: Option[Int],
    numericScale: Option[Int],
    datetimePrecision: Option[Int],
    intervalType: Option[String],
    intervalPrecision: Option[Int],
    characterSetCatalog: Option[String],
    characterSetSchema: Option[String],
    characterSetName: Option[String],
    collationCatalog: Option[String],
    collationSchema: Option[String],
    collationName: Option[String],
    domainCatalog: Option[String],
    domainSchema: Option[String],
    domainName: Option[String],
    udtCatalog: Option[String],
    udtSchema: Option[String],
    udtName: Option[String],
    scopeCatalog: Option[String],
    scopeSchema: Option[String],
    scopeName: Option[String],
    maximumCardinality: Option[Int],
    dtdIdentifier: Option[String],
    isSelfReferencing: Option[String],
    isIdentity: Option[String],
    identityGeneration: Option[String],
    identityStart: Option[String],
    identityIncrement: Option[String],
    identityMaximum: Option[String],
    identityMinimum: Option[String],
    identityCycle: Option[String],
    isGenerated: Option[String],
    generationExpression: Option[String],
    isUpdatable: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(tableCatalog, tableSchema, tableName, columnName, ordinalPosition, columnDefault, isNullable, dataType, characterMaximumLength, characterOctetLength, numericPrecision, numericPrecisionRadix, numericScale, datetimePrecision, intervalType, intervalPrecision, characterSetCatalog, characterSetSchema, characterSetName, collationCatalog, collationSchema, collationName, domainCatalog, domainSchema, domainName, udtCatalog, udtSchema, udtName, scopeCatalog, scopeSchema, scopeName, maximumCardinality, dtdIdentifier, isSelfReferencing, isIdentity, identityGeneration, identityStart, identityIncrement, identityMaximum, identityMinimum, identityCycle, isGenerated, generationExpression, isUpdatable)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"table_catalog, table_schema, table_name, column_name, ordinal_position, column_default, is_nullable, data_type, character_maximum_length, character_octet_length, numeric_precision, numeric_precision_radix, numeric_scale, datetime_precision, interval_type, interval_precision, character_set_catalog, character_set_schema, character_set_name, collation_catalog, collation_schema, collation_name, domain_catalog, domain_schema, domain_name, udt_catalog, udt_schema, udt_name, scope_catalog, scope_schema, scope_name, maximum_cardinality, dtd_identifier, is_self_referencing, is_identity, identity_generation, identity_start, identity_increment, identity_maximum, identity_minimum, identity_cycle, is_generated, generation_expression, is_updatable"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".table_catalog, " ++ Fragment.const0(a) ++ fr".table_schema, " ++ Fragment.const0(a) ++ fr".table_name, " ++ Fragment.const0(a) ++ fr".column_name, " ++ Fragment.const0(a) ++ fr".ordinal_position, " ++ Fragment.const0(a) ++ fr".column_default, " ++ Fragment.const0(a) ++ fr".is_nullable, " ++ Fragment.const0(a) ++ fr".data_type, " ++ Fragment.const0(a) ++ fr".character_maximum_length, " ++ Fragment.const0(a) ++ fr".character_octet_length, " ++ Fragment.const0(a) ++ fr".numeric_precision, " ++ Fragment.const0(a) ++ fr".numeric_precision_radix, " ++ Fragment.const0(a) ++ fr".numeric_scale, " ++ Fragment.const0(a) ++ fr".datetime_precision, " ++ Fragment.const0(a) ++ fr".interval_type, " ++ Fragment.const0(a) ++ fr".interval_precision, " ++ Fragment.const0(a) ++ fr".character_set_catalog, " ++ Fragment.const0(a) ++ fr".character_set_schema, " ++ Fragment.const0(a) ++ fr".character_set_name, " ++ Fragment.const0(a) ++ fr".collation_catalog, " ++ Fragment.const0(a) ++ fr".collation_schema, " ++ Fragment.const0(a) ++ fr".collation_name, " ++ Fragment.const0(a) ++ fr".domain_catalog, " ++ Fragment.const0(a) ++ fr".domain_schema, " ++ Fragment.const0(a) ++ fr".domain_name, " ++ Fragment.const0(a) ++ fr".udt_catalog, " ++ Fragment.const0(a) ++ fr".udt_schema, " ++ Fragment.const0(a) ++ fr".udt_name, " ++ Fragment.const0(a) ++ fr".scope_catalog, " ++ Fragment.const0(a) ++ fr".scope_schema, " ++ Fragment.const0(a) ++ fr".scope_name, " ++ Fragment.const0(a) ++ fr".maximum_cardinality, " ++ Fragment.const0(a) ++ fr".dtd_identifier, " ++ Fragment.const0(a) ++ fr".is_self_referencing, " ++ Fragment.const0(a) ++ fr".is_identity, " ++ Fragment.const0(a) ++ fr".identity_generation, " ++ Fragment.const0(a) ++ fr".identity_start, " ++ Fragment.const0(a) ++ fr".identity_increment, " ++ Fragment.const0(a) ++ fr".identity_maximum, " ++ Fragment.const0(a) ++ fr".identity_minimum, " ++ Fragment.const0(a) ++ fr".identity_cycle, " ++ Fragment.const0(a) ++ fr".is_generated, " ++ Fragment.const0(a) ++ fr".generation_expression, " ++ Fragment.const0(a) ++ fr".is_updatable"
  }

  case class Shape(tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, columnName: Option[String] = None, ordinalPosition: Option[Int] = None, columnDefault: Option[String] = None, isNullable: Option[String] = None, dataType: Option[String] = None, characterMaximumLength: Option[Int] = None, characterOctetLength: Option[Int] = None, numericPrecision: Option[Int] = None, numericPrecisionRadix: Option[Int] = None, numericScale: Option[Int] = None, datetimePrecision: Option[Int] = None, intervalType: Option[String] = None, intervalPrecision: Option[Int] = None, characterSetCatalog: Option[String] = None, characterSetSchema: Option[String] = None, characterSetName: Option[String] = None, collationCatalog: Option[String] = None, collationSchema: Option[String] = None, collationName: Option[String] = None, domainCatalog: Option[String] = None, domainSchema: Option[String] = None, domainName: Option[String] = None, udtCatalog: Option[String] = None, udtSchema: Option[String] = None, udtName: Option[String] = None, scopeCatalog: Option[String] = None, scopeSchema: Option[String] = None, scopeName: Option[String] = None, maximumCardinality: Option[Int] = None, dtdIdentifier: Option[String] = None, isSelfReferencing: Option[String] = None, isIdentity: Option[String] = None, identityGeneration: Option[String] = None, identityStart: Option[String] = None, identityIncrement: Option[String] = None, identityMaximum: Option[String] = None, identityMinimum: Option[String] = None, identityCycle: Option[String] = None, isGenerated: Option[String] = None, generationExpression: Option[String] = None, isUpdatable: Option[String] = None)

  object Shape {
    def NoDefaults(tableCatalog: Option[String], tableSchema: Option[String], tableName: Option[String], columnName: Option[String], ordinalPosition: Option[Int], columnDefault: Option[String], isNullable: Option[String], dataType: Option[String], characterMaximumLength: Option[Int], characterOctetLength: Option[Int], numericPrecision: Option[Int], numericPrecisionRadix: Option[Int], numericScale: Option[Int], datetimePrecision: Option[Int], intervalType: Option[String], intervalPrecision: Option[Int], characterSetCatalog: Option[String], characterSetSchema: Option[String], characterSetName: Option[String], collationCatalog: Option[String], collationSchema: Option[String], collationName: Option[String], domainCatalog: Option[String], domainSchema: Option[String], domainName: Option[String], udtCatalog: Option[String], udtSchema: Option[String], udtName: Option[String], scopeCatalog: Option[String], scopeSchema: Option[String], scopeName: Option[String], maximumCardinality: Option[Int], dtdIdentifier: Option[String], isSelfReferencing: Option[String], isIdentity: Option[String], identityGeneration: Option[String], identityStart: Option[String], identityIncrement: Option[String], identityMaximum: Option[String], identityMinimum: Option[String], identityCycle: Option[String], isGenerated: Option[String], generationExpression: Option[String], isUpdatable: Option[String]): Shape = Shape(tableCatalog, tableSchema, tableName, columnName, ordinalPosition, columnDefault, isNullable, dataType, characterMaximumLength, characterOctetLength, numericPrecision, numericPrecisionRadix, numericScale, datetimePrecision, intervalType, intervalPrecision, characterSetCatalog, characterSetSchema, characterSetName, collationCatalog, collationSchema, collationName, domainCatalog, domainSchema, domainName, udtCatalog, udtSchema, udtName, scopeCatalog, scopeSchema, scopeName, maximumCardinality, dtdIdentifier, isSelfReferencing, isIdentity, identityGeneration, identityStart, identityIncrement, identityMaximum, identityMinimum, identityCycle, isGenerated, generationExpression, isUpdatable)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))))))))))))))))))))))))))))))))))))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.tableCatalog, (row.tableSchema, (row.tableName, (row.columnName, (row.ordinalPosition, (row.columnDefault, (row.isNullable, (row.dataType, (row.characterMaximumLength, (row.characterOctetLength, (row.numericPrecision, (row.numericPrecisionRadix, (row.numericScale, (row.datetimePrecision, (row.intervalType, (row.intervalPrecision, (row.characterSetCatalog, (row.characterSetSchema, (row.characterSetName, (row.collationCatalog, (row.collationSchema, (row.collationName, (row.domainCatalog, (row.domainSchema, (row.domainName, (row.udtCatalog, (row.udtSchema, (row.udtName, (row.scopeCatalog, (row.scopeSchema, (row.scopeName, (row.maximumCardinality, (row.dtdIdentifier, (row.isSelfReferencing, (row.isIdentity, (row.identityGeneration, (row.identityStart, (row.identityIncrement, (row.identityMaximum, (row.identityMinimum, (row.identityCycle, (row.isGenerated, (row.generationExpression, (row.isUpdatable))))))))))))))))))))))))))))))))))))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))))))))))))))))))))))))))))))))))))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.tableCatalog, (row.tableSchema, (row.tableName, (row.columnName, (row.ordinalPosition, (row.columnDefault, (row.isNullable, (row.dataType, (row.characterMaximumLength, (row.characterOctetLength, (row.numericPrecision, (row.numericPrecisionRadix, (row.numericScale, (row.datetimePrecision, (row.intervalType, (row.intervalPrecision, (row.characterSetCatalog, (row.characterSetSchema, (row.characterSetName, (row.collationCatalog, (row.collationSchema, (row.collationName, (row.domainCatalog, (row.domainSchema, (row.domainName, (row.udtCatalog, (row.udtSchema, (row.udtName, (row.scopeCatalog, (row.scopeSchema, (row.scopeName, (row.maximumCardinality, (row.dtdIdentifier, (row.isSelfReferencing, (row.isIdentity, (row.identityGeneration, (row.identityStart, (row.identityIncrement, (row.identityMaximum, (row.identityMinimum, (row.identityCycle, (row.isGenerated, (row.generationExpression, (row.isUpdatable))))))))))))))))))))))))))))))))))))))))))))
      )
    }

}
trait Columns {
  import Columns._

  def create(tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, columnName: Option[String] = None, ordinalPosition: Option[Int] = None, columnDefault: Option[String] = None, isNullable: Option[String] = None, dataType: Option[String] = None, characterMaximumLength: Option[Int] = None, characterOctetLength: Option[Int] = None, numericPrecision: Option[Int] = None, numericPrecisionRadix: Option[Int] = None, numericScale: Option[Int] = None, datetimePrecision: Option[Int] = None, intervalType: Option[String] = None, intervalPrecision: Option[Int] = None, characterSetCatalog: Option[String] = None, characterSetSchema: Option[String] = None, characterSetName: Option[String] = None, collationCatalog: Option[String] = None, collationSchema: Option[String] = None, collationName: Option[String] = None, domainCatalog: Option[String] = None, domainSchema: Option[String] = None, domainName: Option[String] = None, udtCatalog: Option[String] = None, udtSchema: Option[String] = None, udtName: Option[String] = None, scopeCatalog: Option[String] = None, scopeSchema: Option[String] = None, scopeName: Option[String] = None, maximumCardinality: Option[Int] = None, dtdIdentifier: Option[String] = None, isSelfReferencing: Option[String] = None, isIdentity: Option[String] = None, identityGeneration: Option[String] = None, identityStart: Option[String] = None, identityIncrement: Option[String] = None, identityMaximum: Option[String] = None, identityMinimum: Option[String] = None, identityCycle: Option[String] = None, isGenerated: Option[String] = None, generationExpression: Option[String] = None, isUpdatable: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(tableCatalog, tableSchema, tableName, columnName, ordinalPosition, columnDefault, isNullable, dataType, characterMaximumLength, characterOctetLength, numericPrecision, numericPrecisionRadix, numericScale, datetimePrecision, intervalType, intervalPrecision, characterSetCatalog, characterSetSchema, characterSetName, collationCatalog, collationSchema, collationName, domainCatalog, domainSchema, domainName, udtCatalog, udtSchema, udtName, scopeCatalog, scopeSchema, scopeName, maximumCardinality, dtdIdentifier, isSelfReferencing, isIdentity, identityGeneration, identityStart, identityIncrement, identityMaximum, identityMinimum, identityCycle, isGenerated, generationExpression, isUpdatable))
  }

  def createVoid(tableCatalog: Option[String] = None, tableSchema: Option[String] = None, tableName: Option[String] = None, columnName: Option[String] = None, ordinalPosition: Option[Int] = None, columnDefault: Option[String] = None, isNullable: Option[String] = None, dataType: Option[String] = None, characterMaximumLength: Option[Int] = None, characterOctetLength: Option[Int] = None, numericPrecision: Option[Int] = None, numericPrecisionRadix: Option[Int] = None, numericScale: Option[Int] = None, datetimePrecision: Option[Int] = None, intervalType: Option[String] = None, intervalPrecision: Option[Int] = None, characterSetCatalog: Option[String] = None, characterSetSchema: Option[String] = None, characterSetName: Option[String] = None, collationCatalog: Option[String] = None, collationSchema: Option[String] = None, collationName: Option[String] = None, domainCatalog: Option[String] = None, domainSchema: Option[String] = None, domainName: Option[String] = None, udtCatalog: Option[String] = None, udtSchema: Option[String] = None, udtName: Option[String] = None, scopeCatalog: Option[String] = None, scopeSchema: Option[String] = None, scopeName: Option[String] = None, maximumCardinality: Option[Int] = None, dtdIdentifier: Option[String] = None, isSelfReferencing: Option[String] = None, isIdentity: Option[String] = None, identityGeneration: Option[String] = None, identityStart: Option[String] = None, identityIncrement: Option[String] = None, identityMaximum: Option[String] = None, identityMinimum: Option[String] = None, identityCycle: Option[String] = None, isGenerated: Option[String] = None, generationExpression: Option[String] = None, isUpdatable: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(tableCatalog, tableSchema, tableName, columnName, ordinalPosition, columnDefault, isNullable, dataType, characterMaximumLength, characterOctetLength, numericPrecision, numericPrecisionRadix, numericScale, datetimePrecision, intervalType, intervalPrecision, characterSetCatalog, characterSetSchema, characterSetName, collationCatalog, collationSchema, collationName, domainCatalog, domainSchema, domainName, udtCatalog, udtSchema, udtName, scopeCatalog, scopeSchema, scopeName, maximumCardinality, dtdIdentifier, isSelfReferencing, isIdentity, identityGeneration, identityStart, identityIncrement, identityMaximum, identityMinimum, identityCycle, isGenerated, generationExpression, isUpdatable))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.Columns.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.columns (table_catalog, table_schema, table_name, column_name, ordinal_position, column_default, is_nullable, data_type, character_maximum_length, character_octet_length, numeric_precision, numeric_precision_radix, numeric_scale, datetime_precision, interval_type, interval_precision, character_set_catalog, character_set_schema, character_set_name, collation_catalog, collation_schema, collation_name, domain_catalog, domain_schema, domain_name, udt_catalog, udt_schema, udt_name, scope_catalog, scope_schema, scope_name, maximum_cardinality, dtd_identifier, is_self_referencing, is_identity, identity_generation, identity_start, identity_increment, identity_maximum, identity_minimum, identity_cycle, is_generated, generation_expression, is_updatable) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.Columns.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("table_catalog", "table_schema", "table_name", "column_name", "ordinal_position", "column_default", "is_nullable", "data_type", "character_maximum_length", "character_octet_length", "numeric_precision", "numeric_precision_radix", "numeric_scale", "datetime_precision", "interval_type", "interval_precision", "character_set_catalog", "character_set_schema", "character_set_name", "collation_catalog", "collation_schema", "collation_name", "domain_catalog", "domain_schema", "domain_name", "udt_catalog", "udt_schema", "udt_name", "scope_catalog", "scope_schema", "scope_name", "maximum_cardinality", "dtd_identifier", "is_self_referencing", "is_identity", "identity_generation", "identity_start", "identity_increment", "identity_maximum", "identity_minimum", "identity_cycle", "is_generated", "generation_expression", "is_updatable")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.Columns.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.Columns.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.Columns.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.Columns.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.columns
      OFFSET $offset
      LIMIT $limit
    """).query[Row]
  }

  def all(offset: Long, limit: Long): ConnectionIO[List[Row]] = {
    allInner(offset, limit).list
  }

  def allUnbounded(): ConnectionIO[List[Row]] = {
    allInner(0, 9223372036854775807L).list
  }

  private[gen] def countInner(): Query0[Long] = {
    sql"""
      SELECT COUNT(*)
      FROM information_schema.columns
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
