package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object SqlLanguages extends SqlLanguages {

  case class Row(
    sqlLanguageSource: Option[String],
    sqlLanguageYear: Option[String],
    sqlLanguageConformance: Option[String],
    sqlLanguageIntegrity: Option[String],
    sqlLanguageImplementation: Option[String],
    sqlLanguageBindingStyle: Option[String],
    sqlLanguageProgrammingLanguage: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(sqlLanguageSource, sqlLanguageYear, sqlLanguageConformance, sqlLanguageIntegrity, sqlLanguageImplementation, sqlLanguageBindingStyle, sqlLanguageProgrammingLanguage)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"sql_language_source, sql_language_year, sql_language_conformance, sql_language_integrity, sql_language_implementation, sql_language_binding_style, sql_language_programming_language"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".sql_language_source, " ++ Fragment.const0(a) ++ fr".sql_language_year, " ++ Fragment.const0(a) ++ fr".sql_language_conformance, " ++ Fragment.const0(a) ++ fr".sql_language_integrity, " ++ Fragment.const0(a) ++ fr".sql_language_implementation, " ++ Fragment.const0(a) ++ fr".sql_language_binding_style, " ++ Fragment.const0(a) ++ fr".sql_language_programming_language"
  }

  case class Shape(sqlLanguageSource: Option[String] = None, sqlLanguageYear: Option[String] = None, sqlLanguageConformance: Option[String] = None, sqlLanguageIntegrity: Option[String] = None, sqlLanguageImplementation: Option[String] = None, sqlLanguageBindingStyle: Option[String] = None, sqlLanguageProgrammingLanguage: Option[String] = None)

  object Shape {
    def NoDefaults(sqlLanguageSource: Option[String], sqlLanguageYear: Option[String], sqlLanguageConformance: Option[String], sqlLanguageIntegrity: Option[String], sqlLanguageImplementation: Option[String], sqlLanguageBindingStyle: Option[String], sqlLanguageProgrammingLanguage: Option[String]): Shape = Shape(sqlLanguageSource, sqlLanguageYear, sqlLanguageConformance, sqlLanguageIntegrity, sqlLanguageImplementation, sqlLanguageBindingStyle, sqlLanguageProgrammingLanguage)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2),
        (row) => (row.sqlLanguageSource, (row.sqlLanguageYear, (row.sqlLanguageConformance, (row.sqlLanguageIntegrity, (row.sqlLanguageImplementation, (row.sqlLanguageBindingStyle, (row.sqlLanguageProgrammingLanguage)))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2),
        (row) => (row.sqlLanguageSource, (row.sqlLanguageYear, (row.sqlLanguageConformance, (row.sqlLanguageIntegrity, (row.sqlLanguageImplementation, (row.sqlLanguageBindingStyle, (row.sqlLanguageProgrammingLanguage)))))))
      )
    }

}
trait SqlLanguages {
  import SqlLanguages._

  def create(sqlLanguageSource: Option[String] = None, sqlLanguageYear: Option[String] = None, sqlLanguageConformance: Option[String] = None, sqlLanguageIntegrity: Option[String] = None, sqlLanguageImplementation: Option[String] = None, sqlLanguageBindingStyle: Option[String] = None, sqlLanguageProgrammingLanguage: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(sqlLanguageSource, sqlLanguageYear, sqlLanguageConformance, sqlLanguageIntegrity, sqlLanguageImplementation, sqlLanguageBindingStyle, sqlLanguageProgrammingLanguage))
  }

  def createVoid(sqlLanguageSource: Option[String] = None, sqlLanguageYear: Option[String] = None, sqlLanguageConformance: Option[String] = None, sqlLanguageIntegrity: Option[String] = None, sqlLanguageImplementation: Option[String] = None, sqlLanguageBindingStyle: Option[String] = None, sqlLanguageProgrammingLanguage: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(sqlLanguageSource, sqlLanguageYear, sqlLanguageConformance, sqlLanguageIntegrity, sqlLanguageImplementation, sqlLanguageBindingStyle, sqlLanguageProgrammingLanguage))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlLanguages.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.sql_languages (sql_language_source, sql_language_year, sql_language_conformance, sql_language_integrity, sql_language_implementation, sql_language_binding_style, sql_language_programming_language) VALUES (?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlLanguages.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("sql_language_source", "sql_language_year", "sql_language_conformance", "sql_language_integrity", "sql_language_implementation", "sql_language_binding_style", "sql_language_programming_language")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlLanguages.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.SqlLanguages.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.SqlLanguages.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.SqlLanguages.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.sql_languages
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
      FROM information_schema.sql_languages
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
