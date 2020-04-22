package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object CollationCharacterSetApplicability extends CollationCharacterSetApplicability {

  case class Row(
    collationCatalog: Option[String],
    collationSchema: Option[String],
    collationName: Option[String],
    characterSetCatalog: Option[String],
    characterSetSchema: Option[String],
    characterSetName: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(collationCatalog, collationSchema, collationName, characterSetCatalog, characterSetSchema, characterSetName)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"collation_catalog, collation_schema, collation_name, character_set_catalog, character_set_schema, character_set_name"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".collation_catalog, " ++ Fragment.const0(a) ++ fr".collation_schema, " ++ Fragment.const0(a) ++ fr".collation_name, " ++ Fragment.const0(a) ++ fr".character_set_catalog, " ++ Fragment.const0(a) ++ fr".character_set_schema, " ++ Fragment.const0(a) ++ fr".character_set_name"
  }

  case class Shape(collationCatalog: Option[String] = None, collationSchema: Option[String] = None, collationName: Option[String] = None, characterSetCatalog: Option[String] = None, characterSetSchema: Option[String] = None, characterSetName: Option[String] = None)

  object Shape {
    def NoDefaults(collationCatalog: Option[String], collationSchema: Option[String], collationName: Option[String], characterSetCatalog: Option[String], characterSetSchema: Option[String], characterSetName: Option[String]): Shape = Shape(collationCatalog, collationSchema, collationName, characterSetCatalog, characterSetSchema, characterSetName)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2),
        (row) => (row.collationCatalog, (row.collationSchema, (row.collationName, (row.characterSetCatalog, (row.characterSetSchema, (row.characterSetName))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2),
        (row) => (row.collationCatalog, (row.collationSchema, (row.collationName, (row.characterSetCatalog, (row.characterSetSchema, (row.characterSetName))))))
      )
    }

}
trait CollationCharacterSetApplicability {
  import CollationCharacterSetApplicability._

  def create(collationCatalog: Option[String] = None, collationSchema: Option[String] = None, collationName: Option[String] = None, characterSetCatalog: Option[String] = None, characterSetSchema: Option[String] = None, characterSetName: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(collationCatalog, collationSchema, collationName, characterSetCatalog, characterSetSchema, characterSetName))
  }

  def createVoid(collationCatalog: Option[String] = None, collationSchema: Option[String] = None, collationName: Option[String] = None, characterSetCatalog: Option[String] = None, characterSetSchema: Option[String] = None, characterSetName: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(collationCatalog, collationSchema, collationName, characterSetCatalog, characterSetSchema, characterSetName))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.CollationCharacterSetApplicability.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.collation_character_set_applicability (collation_catalog, collation_schema, collation_name, character_set_catalog, character_set_schema, character_set_name) VALUES (?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.CollationCharacterSetApplicability.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("collation_catalog", "collation_schema", "collation_name", "character_set_catalog", "character_set_schema", "character_set_name")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.CollationCharacterSetApplicability.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.CollationCharacterSetApplicability.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.CollationCharacterSetApplicability.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.CollationCharacterSetApplicability.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.collation_character_set_applicability
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
      FROM information_schema.collation_character_set_applicability
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
