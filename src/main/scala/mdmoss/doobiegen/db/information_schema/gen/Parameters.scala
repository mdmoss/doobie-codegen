package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object Parameters extends Parameters {

  case class Row(
    specificCatalog: Option[String],
    specificSchema: Option[String],
    specificName: Option[String],
    ordinalPosition: Option[Int],
    parameterMode: Option[String],
    isResult: Option[String],
    asLocator: Option[String],
    parameterName: Option[String],
    dataType: Option[String],
    characterMaximumLength: Option[Int],
    characterOctetLength: Option[Int],
    characterSetCatalog: Option[String],
    characterSetSchema: Option[String],
    characterSetName: Option[String],
    collationCatalog: Option[String],
    collationSchema: Option[String],
    collationName: Option[String],
    numericPrecision: Option[Int],
    numericPrecisionRadix: Option[Int],
    numericScale: Option[Int],
    datetimePrecision: Option[Int],
    intervalType: Option[String],
    intervalPrecision: Option[Int],
    udtCatalog: Option[String],
    udtSchema: Option[String],
    udtName: Option[String],
    scopeCatalog: Option[String],
    scopeSchema: Option[String],
    scopeName: Option[String],
    maximumCardinality: Option[Int],
    dtdIdentifier: Option[String],
    parameterDefault: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(specificCatalog, specificSchema, specificName, ordinalPosition, parameterMode, isResult, asLocator, parameterName, dataType, characterMaximumLength, characterOctetLength, characterSetCatalog, characterSetSchema, characterSetName, collationCatalog, collationSchema, collationName, numericPrecision, numericPrecisionRadix, numericScale, datetimePrecision, intervalType, intervalPrecision, udtCatalog, udtSchema, udtName, scopeCatalog, scopeSchema, scopeName, maximumCardinality, dtdIdentifier, parameterDefault)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"specific_catalog, specific_schema, specific_name, ordinal_position, parameter_mode, is_result, as_locator, parameter_name, data_type, character_maximum_length, character_octet_length, character_set_catalog, character_set_schema, character_set_name, collation_catalog, collation_schema, collation_name, numeric_precision, numeric_precision_radix, numeric_scale, datetime_precision, interval_type, interval_precision, udt_catalog, udt_schema, udt_name, scope_catalog, scope_schema, scope_name, maximum_cardinality, dtd_identifier, parameter_default"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".specific_catalog, " ++ Fragment.const0(a) ++ fr".specific_schema, " ++ Fragment.const0(a) ++ fr".specific_name, " ++ Fragment.const0(a) ++ fr".ordinal_position, " ++ Fragment.const0(a) ++ fr".parameter_mode, " ++ Fragment.const0(a) ++ fr".is_result, " ++ Fragment.const0(a) ++ fr".as_locator, " ++ Fragment.const0(a) ++ fr".parameter_name, " ++ Fragment.const0(a) ++ fr".data_type, " ++ Fragment.const0(a) ++ fr".character_maximum_length, " ++ Fragment.const0(a) ++ fr".character_octet_length, " ++ Fragment.const0(a) ++ fr".character_set_catalog, " ++ Fragment.const0(a) ++ fr".character_set_schema, " ++ Fragment.const0(a) ++ fr".character_set_name, " ++ Fragment.const0(a) ++ fr".collation_catalog, " ++ Fragment.const0(a) ++ fr".collation_schema, " ++ Fragment.const0(a) ++ fr".collation_name, " ++ Fragment.const0(a) ++ fr".numeric_precision, " ++ Fragment.const0(a) ++ fr".numeric_precision_radix, " ++ Fragment.const0(a) ++ fr".numeric_scale, " ++ Fragment.const0(a) ++ fr".datetime_precision, " ++ Fragment.const0(a) ++ fr".interval_type, " ++ Fragment.const0(a) ++ fr".interval_precision, " ++ Fragment.const0(a) ++ fr".udt_catalog, " ++ Fragment.const0(a) ++ fr".udt_schema, " ++ Fragment.const0(a) ++ fr".udt_name, " ++ Fragment.const0(a) ++ fr".scope_catalog, " ++ Fragment.const0(a) ++ fr".scope_schema, " ++ Fragment.const0(a) ++ fr".scope_name, " ++ Fragment.const0(a) ++ fr".maximum_cardinality, " ++ Fragment.const0(a) ++ fr".dtd_identifier, " ++ Fragment.const0(a) ++ fr".parameter_default"
  }

  case class Shape(specificCatalog: Option[String] = None, specificSchema: Option[String] = None, specificName: Option[String] = None, ordinalPosition: Option[Int] = None, parameterMode: Option[String] = None, isResult: Option[String] = None, asLocator: Option[String] = None, parameterName: Option[String] = None, dataType: Option[String] = None, characterMaximumLength: Option[Int] = None, characterOctetLength: Option[Int] = None, characterSetCatalog: Option[String] = None, characterSetSchema: Option[String] = None, characterSetName: Option[String] = None, collationCatalog: Option[String] = None, collationSchema: Option[String] = None, collationName: Option[String] = None, numericPrecision: Option[Int] = None, numericPrecisionRadix: Option[Int] = None, numericScale: Option[Int] = None, datetimePrecision: Option[Int] = None, intervalType: Option[String] = None, intervalPrecision: Option[Int] = None, udtCatalog: Option[String] = None, udtSchema: Option[String] = None, udtName: Option[String] = None, scopeCatalog: Option[String] = None, scopeSchema: Option[String] = None, scopeName: Option[String] = None, maximumCardinality: Option[Int] = None, dtdIdentifier: Option[String] = None, parameterDefault: Option[String] = None)

  object Shape {
    def NoDefaults(specificCatalog: Option[String], specificSchema: Option[String], specificName: Option[String], ordinalPosition: Option[Int], parameterMode: Option[String], isResult: Option[String], asLocator: Option[String], parameterName: Option[String], dataType: Option[String], characterMaximumLength: Option[Int], characterOctetLength: Option[Int], characterSetCatalog: Option[String], characterSetSchema: Option[String], characterSetName: Option[String], collationCatalog: Option[String], collationSchema: Option[String], collationName: Option[String], numericPrecision: Option[Int], numericPrecisionRadix: Option[Int], numericScale: Option[Int], datetimePrecision: Option[Int], intervalType: Option[String], intervalPrecision: Option[Int], udtCatalog: Option[String], udtSchema: Option[String], udtName: Option[String], scopeCatalog: Option[String], scopeSchema: Option[String], scopeName: Option[String], maximumCardinality: Option[Int], dtdIdentifier: Option[String], parameterDefault: Option[String]): Shape = Shape(specificCatalog, specificSchema, specificName, ordinalPosition, parameterMode, isResult, asLocator, parameterName, dataType, characterMaximumLength, characterOctetLength, characterSetCatalog, characterSetSchema, characterSetName, collationCatalog, collationSchema, collationName, numericPrecision, numericPrecisionRadix, numericScale, datetimePrecision, intervalType, intervalPrecision, udtCatalog, udtSchema, udtName, scopeCatalog, scopeSchema, scopeName, maximumCardinality, dtdIdentifier, parameterDefault)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
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
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))))))))))))))))))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.specificCatalog, (row.specificSchema, (row.specificName, (row.ordinalPosition, (row.parameterMode, (row.isResult, (row.asLocator, (row.parameterName, (row.dataType, (row.characterMaximumLength, (row.characterOctetLength, (row.characterSetCatalog, (row.characterSetSchema, (row.characterSetName, (row.collationCatalog, (row.collationSchema, (row.collationName, (row.numericPrecision, (row.numericPrecisionRadix, (row.numericScale, (row.datetimePrecision, (row.intervalType, (row.intervalPrecision, (row.udtCatalog, (row.udtSchema, (row.udtName, (row.scopeCatalog, (row.scopeSchema, (row.scopeName, (row.maximumCardinality, (row.dtdIdentifier, (row.parameterDefault))))))))))))))))))))))))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
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
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.IntMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))))))))))))))))))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.specificCatalog, (row.specificSchema, (row.specificName, (row.ordinalPosition, (row.parameterMode, (row.isResult, (row.asLocator, (row.parameterName, (row.dataType, (row.characterMaximumLength, (row.characterOctetLength, (row.characterSetCatalog, (row.characterSetSchema, (row.characterSetName, (row.collationCatalog, (row.collationSchema, (row.collationName, (row.numericPrecision, (row.numericPrecisionRadix, (row.numericScale, (row.datetimePrecision, (row.intervalType, (row.intervalPrecision, (row.udtCatalog, (row.udtSchema, (row.udtName, (row.scopeCatalog, (row.scopeSchema, (row.scopeName, (row.maximumCardinality, (row.dtdIdentifier, (row.parameterDefault))))))))))))))))))))))))))))))))
      )
    }

}
trait Parameters {
  import Parameters._

  def create(specificCatalog: Option[String] = None, specificSchema: Option[String] = None, specificName: Option[String] = None, ordinalPosition: Option[Int] = None, parameterMode: Option[String] = None, isResult: Option[String] = None, asLocator: Option[String] = None, parameterName: Option[String] = None, dataType: Option[String] = None, characterMaximumLength: Option[Int] = None, characterOctetLength: Option[Int] = None, characterSetCatalog: Option[String] = None, characterSetSchema: Option[String] = None, characterSetName: Option[String] = None, collationCatalog: Option[String] = None, collationSchema: Option[String] = None, collationName: Option[String] = None, numericPrecision: Option[Int] = None, numericPrecisionRadix: Option[Int] = None, numericScale: Option[Int] = None, datetimePrecision: Option[Int] = None, intervalType: Option[String] = None, intervalPrecision: Option[Int] = None, udtCatalog: Option[String] = None, udtSchema: Option[String] = None, udtName: Option[String] = None, scopeCatalog: Option[String] = None, scopeSchema: Option[String] = None, scopeName: Option[String] = None, maximumCardinality: Option[Int] = None, dtdIdentifier: Option[String] = None, parameterDefault: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(specificCatalog, specificSchema, specificName, ordinalPosition, parameterMode, isResult, asLocator, parameterName, dataType, characterMaximumLength, characterOctetLength, characterSetCatalog, characterSetSchema, characterSetName, collationCatalog, collationSchema, collationName, numericPrecision, numericPrecisionRadix, numericScale, datetimePrecision, intervalType, intervalPrecision, udtCatalog, udtSchema, udtName, scopeCatalog, scopeSchema, scopeName, maximumCardinality, dtdIdentifier, parameterDefault))
  }

  def createVoid(specificCatalog: Option[String] = None, specificSchema: Option[String] = None, specificName: Option[String] = None, ordinalPosition: Option[Int] = None, parameterMode: Option[String] = None, isResult: Option[String] = None, asLocator: Option[String] = None, parameterName: Option[String] = None, dataType: Option[String] = None, characterMaximumLength: Option[Int] = None, characterOctetLength: Option[Int] = None, characterSetCatalog: Option[String] = None, characterSetSchema: Option[String] = None, characterSetName: Option[String] = None, collationCatalog: Option[String] = None, collationSchema: Option[String] = None, collationName: Option[String] = None, numericPrecision: Option[Int] = None, numericPrecisionRadix: Option[Int] = None, numericScale: Option[Int] = None, datetimePrecision: Option[Int] = None, intervalType: Option[String] = None, intervalPrecision: Option[Int] = None, udtCatalog: Option[String] = None, udtSchema: Option[String] = None, udtName: Option[String] = None, scopeCatalog: Option[String] = None, scopeSchema: Option[String] = None, scopeName: Option[String] = None, maximumCardinality: Option[Int] = None, dtdIdentifier: Option[String] = None, parameterDefault: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(specificCatalog, specificSchema, specificName, ordinalPosition, parameterMode, isResult, asLocator, parameterName, dataType, characterMaximumLength, characterOctetLength, characterSetCatalog, characterSetSchema, characterSetName, collationCatalog, collationSchema, collationName, numericPrecision, numericPrecisionRadix, numericScale, datetimePrecision, intervalType, intervalPrecision, udtCatalog, udtSchema, udtName, scopeCatalog, scopeSchema, scopeName, maximumCardinality, dtdIdentifier, parameterDefault))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.Parameters.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.parameters (specific_catalog, specific_schema, specific_name, ordinal_position, parameter_mode, is_result, as_locator, parameter_name, data_type, character_maximum_length, character_octet_length, character_set_catalog, character_set_schema, character_set_name, collation_catalog, collation_schema, collation_name, numeric_precision, numeric_precision_radix, numeric_scale, datetime_precision, interval_type, interval_precision, udt_catalog, udt_schema, udt_name, scope_catalog, scope_schema, scope_name, maximum_cardinality, dtd_identifier, parameter_default) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.Parameters.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("specific_catalog", "specific_schema", "specific_name", "ordinal_position", "parameter_mode", "is_result", "as_locator", "parameter_name", "data_type", "character_maximum_length", "character_octet_length", "character_set_catalog", "character_set_schema", "character_set_name", "collation_catalog", "collation_schema", "collation_name", "numeric_precision", "numeric_precision_radix", "numeric_scale", "datetime_precision", "interval_type", "interval_precision", "udt_catalog", "udt_schema", "udt_name", "scope_catalog", "scope_schema", "scope_name", "maximum_cardinality", "dtd_identifier", "parameter_default")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.Parameters.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.Parameters.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.Parameters.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.Parameters.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.parameters
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
      FROM information_schema.parameters
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
