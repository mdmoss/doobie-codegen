package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object UserDefinedTypes extends UserDefinedTypes {

  case class Row(
    userDefinedTypeCatalog: Option[String],
    userDefinedTypeSchema: Option[String],
    userDefinedTypeName: Option[String],
    userDefinedTypeCategory: Option[String],
    isInstantiable: Option[String],
    isFinal: Option[String],
    orderingForm: Option[String],
    orderingCategory: Option[String],
    orderingRoutineCatalog: Option[String],
    orderingRoutineSchema: Option[String],
    orderingRoutineName: Option[String],
    referenceType: Option[String],
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
    sourceDtdIdentifier: Option[String],
    refDtdIdentifier: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(userDefinedTypeCatalog, userDefinedTypeSchema, userDefinedTypeName, userDefinedTypeCategory, isInstantiable, isFinal, orderingForm, orderingCategory, orderingRoutineCatalog, orderingRoutineSchema, orderingRoutineName, referenceType, dataType, characterMaximumLength, characterOctetLength, characterSetCatalog, characterSetSchema, characterSetName, collationCatalog, collationSchema, collationName, numericPrecision, numericPrecisionRadix, numericScale, datetimePrecision, intervalType, intervalPrecision, sourceDtdIdentifier, refDtdIdentifier)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"user_defined_type_catalog, user_defined_type_schema, user_defined_type_name, user_defined_type_category, is_instantiable, is_final, ordering_form, ordering_category, ordering_routine_catalog, ordering_routine_schema, ordering_routine_name, reference_type, data_type, character_maximum_length, character_octet_length, character_set_catalog, character_set_schema, character_set_name, collation_catalog, collation_schema, collation_name, numeric_precision, numeric_precision_radix, numeric_scale, datetime_precision, interval_type, interval_precision, source_dtd_identifier, ref_dtd_identifier"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".user_defined_type_catalog, " ++ Fragment.const0(a) ++ fr".user_defined_type_schema, " ++ Fragment.const0(a) ++ fr".user_defined_type_name, " ++ Fragment.const0(a) ++ fr".user_defined_type_category, " ++ Fragment.const0(a) ++ fr".is_instantiable, " ++ Fragment.const0(a) ++ fr".is_final, " ++ Fragment.const0(a) ++ fr".ordering_form, " ++ Fragment.const0(a) ++ fr".ordering_category, " ++ Fragment.const0(a) ++ fr".ordering_routine_catalog, " ++ Fragment.const0(a) ++ fr".ordering_routine_schema, " ++ Fragment.const0(a) ++ fr".ordering_routine_name, " ++ Fragment.const0(a) ++ fr".reference_type, " ++ Fragment.const0(a) ++ fr".data_type, " ++ Fragment.const0(a) ++ fr".character_maximum_length, " ++ Fragment.const0(a) ++ fr".character_octet_length, " ++ Fragment.const0(a) ++ fr".character_set_catalog, " ++ Fragment.const0(a) ++ fr".character_set_schema, " ++ Fragment.const0(a) ++ fr".character_set_name, " ++ Fragment.const0(a) ++ fr".collation_catalog, " ++ Fragment.const0(a) ++ fr".collation_schema, " ++ Fragment.const0(a) ++ fr".collation_name, " ++ Fragment.const0(a) ++ fr".numeric_precision, " ++ Fragment.const0(a) ++ fr".numeric_precision_radix, " ++ Fragment.const0(a) ++ fr".numeric_scale, " ++ Fragment.const0(a) ++ fr".datetime_precision, " ++ Fragment.const0(a) ++ fr".interval_type, " ++ Fragment.const0(a) ++ fr".interval_precision, " ++ Fragment.const0(a) ++ fr".source_dtd_identifier, " ++ Fragment.const0(a) ++ fr".ref_dtd_identifier"
  }

  case class Shape(userDefinedTypeCatalog: Option[String] = None, userDefinedTypeSchema: Option[String] = None, userDefinedTypeName: Option[String] = None, userDefinedTypeCategory: Option[String] = None, isInstantiable: Option[String] = None, isFinal: Option[String] = None, orderingForm: Option[String] = None, orderingCategory: Option[String] = None, orderingRoutineCatalog: Option[String] = None, orderingRoutineSchema: Option[String] = None, orderingRoutineName: Option[String] = None, referenceType: Option[String] = None, dataType: Option[String] = None, characterMaximumLength: Option[Int] = None, characterOctetLength: Option[Int] = None, characterSetCatalog: Option[String] = None, characterSetSchema: Option[String] = None, characterSetName: Option[String] = None, collationCatalog: Option[String] = None, collationSchema: Option[String] = None, collationName: Option[String] = None, numericPrecision: Option[Int] = None, numericPrecisionRadix: Option[Int] = None, numericScale: Option[Int] = None, datetimePrecision: Option[Int] = None, intervalType: Option[String] = None, intervalPrecision: Option[Int] = None, sourceDtdIdentifier: Option[String] = None, refDtdIdentifier: Option[String] = None)

  object Shape {
    def NoDefaults(userDefinedTypeCatalog: Option[String], userDefinedTypeSchema: Option[String], userDefinedTypeName: Option[String], userDefinedTypeCategory: Option[String], isInstantiable: Option[String], isFinal: Option[String], orderingForm: Option[String], orderingCategory: Option[String], orderingRoutineCatalog: Option[String], orderingRoutineSchema: Option[String], orderingRoutineName: Option[String], referenceType: Option[String], dataType: Option[String], characterMaximumLength: Option[Int], characterOctetLength: Option[Int], characterSetCatalog: Option[String], characterSetSchema: Option[String], characterSetName: Option[String], collationCatalog: Option[String], collationSchema: Option[String], collationName: Option[String], numericPrecision: Option[Int], numericPrecisionRadix: Option[Int], numericScale: Option[Int], datetimePrecision: Option[Int], intervalType: Option[String], intervalPrecision: Option[Int], sourceDtdIdentifier: Option[String], refDtdIdentifier: Option[String]): Shape = Shape(userDefinedTypeCatalog, userDefinedTypeSchema, userDefinedTypeName, userDefinedTypeCategory, isInstantiable, isFinal, orderingForm, orderingCategory, orderingRoutineCatalog, orderingRoutineSchema, orderingRoutineName, referenceType, dataType, characterMaximumLength, characterOctetLength, characterSetCatalog, characterSetSchema, characterSetName, collationCatalog, collationSchema, collationName, numericPrecision, numericPrecisionRadix, numericScale, datetimePrecision, intervalType, intervalPrecision, sourceDtdIdentifier, refDtdIdentifier)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
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
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))))))))))))))))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.userDefinedTypeCatalog, (row.userDefinedTypeSchema, (row.userDefinedTypeName, (row.userDefinedTypeCategory, (row.isInstantiable, (row.isFinal, (row.orderingForm, (row.orderingCategory, (row.orderingRoutineCatalog, (row.orderingRoutineSchema, (row.orderingRoutineName, (row.referenceType, (row.dataType, (row.characterMaximumLength, (row.characterOctetLength, (row.characterSetCatalog, (row.characterSetSchema, (row.characterSetName, (row.collationCatalog, (row.collationSchema, (row.collationName, (row.numericPrecision, (row.numericPrecisionRadix, (row.numericScale, (row.datetimePrecision, (row.intervalType, (row.intervalPrecision, (row.sourceDtdIdentifier, (row.refDtdIdentifier)))))))))))))))))))))))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
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
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))))))))))))))))))))))))))

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
    t._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2._2),
        (row) => (row.userDefinedTypeCatalog, (row.userDefinedTypeSchema, (row.userDefinedTypeName, (row.userDefinedTypeCategory, (row.isInstantiable, (row.isFinal, (row.orderingForm, (row.orderingCategory, (row.orderingRoutineCatalog, (row.orderingRoutineSchema, (row.orderingRoutineName, (row.referenceType, (row.dataType, (row.characterMaximumLength, (row.characterOctetLength, (row.characterSetCatalog, (row.characterSetSchema, (row.characterSetName, (row.collationCatalog, (row.collationSchema, (row.collationName, (row.numericPrecision, (row.numericPrecisionRadix, (row.numericScale, (row.datetimePrecision, (row.intervalType, (row.intervalPrecision, (row.sourceDtdIdentifier, (row.refDtdIdentifier)))))))))))))))))))))))))))))
      )
    }

}
trait UserDefinedTypes {
  import UserDefinedTypes._

  def create(userDefinedTypeCatalog: Option[String] = None, userDefinedTypeSchema: Option[String] = None, userDefinedTypeName: Option[String] = None, userDefinedTypeCategory: Option[String] = None, isInstantiable: Option[String] = None, isFinal: Option[String] = None, orderingForm: Option[String] = None, orderingCategory: Option[String] = None, orderingRoutineCatalog: Option[String] = None, orderingRoutineSchema: Option[String] = None, orderingRoutineName: Option[String] = None, referenceType: Option[String] = None, dataType: Option[String] = None, characterMaximumLength: Option[Int] = None, characterOctetLength: Option[Int] = None, characterSetCatalog: Option[String] = None, characterSetSchema: Option[String] = None, characterSetName: Option[String] = None, collationCatalog: Option[String] = None, collationSchema: Option[String] = None, collationName: Option[String] = None, numericPrecision: Option[Int] = None, numericPrecisionRadix: Option[Int] = None, numericScale: Option[Int] = None, datetimePrecision: Option[Int] = None, intervalType: Option[String] = None, intervalPrecision: Option[Int] = None, sourceDtdIdentifier: Option[String] = None, refDtdIdentifier: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(userDefinedTypeCatalog, userDefinedTypeSchema, userDefinedTypeName, userDefinedTypeCategory, isInstantiable, isFinal, orderingForm, orderingCategory, orderingRoutineCatalog, orderingRoutineSchema, orderingRoutineName, referenceType, dataType, characterMaximumLength, characterOctetLength, characterSetCatalog, characterSetSchema, characterSetName, collationCatalog, collationSchema, collationName, numericPrecision, numericPrecisionRadix, numericScale, datetimePrecision, intervalType, intervalPrecision, sourceDtdIdentifier, refDtdIdentifier))
  }

  def createVoid(userDefinedTypeCatalog: Option[String] = None, userDefinedTypeSchema: Option[String] = None, userDefinedTypeName: Option[String] = None, userDefinedTypeCategory: Option[String] = None, isInstantiable: Option[String] = None, isFinal: Option[String] = None, orderingForm: Option[String] = None, orderingCategory: Option[String] = None, orderingRoutineCatalog: Option[String] = None, orderingRoutineSchema: Option[String] = None, orderingRoutineName: Option[String] = None, referenceType: Option[String] = None, dataType: Option[String] = None, characterMaximumLength: Option[Int] = None, characterOctetLength: Option[Int] = None, characterSetCatalog: Option[String] = None, characterSetSchema: Option[String] = None, characterSetName: Option[String] = None, collationCatalog: Option[String] = None, collationSchema: Option[String] = None, collationName: Option[String] = None, numericPrecision: Option[Int] = None, numericPrecisionRadix: Option[Int] = None, numericScale: Option[Int] = None, datetimePrecision: Option[Int] = None, intervalType: Option[String] = None, intervalPrecision: Option[Int] = None, sourceDtdIdentifier: Option[String] = None, refDtdIdentifier: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(userDefinedTypeCatalog, userDefinedTypeSchema, userDefinedTypeName, userDefinedTypeCategory, isInstantiable, isFinal, orderingForm, orderingCategory, orderingRoutineCatalog, orderingRoutineSchema, orderingRoutineName, referenceType, dataType, characterMaximumLength, characterOctetLength, characterSetCatalog, characterSetSchema, characterSetName, collationCatalog, collationSchema, collationName, numericPrecision, numericPrecisionRadix, numericScale, datetimePrecision, intervalType, intervalPrecision, sourceDtdIdentifier, refDtdIdentifier))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.UserDefinedTypes.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.user_defined_types (user_defined_type_catalog, user_defined_type_schema, user_defined_type_name, user_defined_type_category, is_instantiable, is_final, ordering_form, ordering_category, ordering_routine_catalog, ordering_routine_schema, ordering_routine_name, reference_type, data_type, character_maximum_length, character_octet_length, character_set_catalog, character_set_schema, character_set_name, collation_catalog, collation_schema, collation_name, numeric_precision, numeric_precision_radix, numeric_scale, datetime_precision, interval_type, interval_precision, source_dtd_identifier, ref_dtd_identifier) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.UserDefinedTypes.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("user_defined_type_catalog", "user_defined_type_schema", "user_defined_type_name", "user_defined_type_category", "is_instantiable", "is_final", "ordering_form", "ordering_category", "ordering_routine_catalog", "ordering_routine_schema", "ordering_routine_name", "reference_type", "data_type", "character_maximum_length", "character_octet_length", "character_set_catalog", "character_set_schema", "character_set_name", "collation_catalog", "collation_schema", "collation_name", "numeric_precision", "numeric_precision_radix", "numeric_scale", "datetime_precision", "interval_type", "interval_precision", "source_dtd_identifier", "ref_dtd_identifier")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.UserDefinedTypes.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.UserDefinedTypes.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.UserDefinedTypes.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.UserDefinedTypes.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.user_defined_types
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
      FROM information_schema.user_defined_types
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
