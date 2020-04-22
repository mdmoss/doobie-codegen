package mdmoss.doobiegen.db.information_schema.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object CharacterSets extends CharacterSets {

  case class Row(
    characterSetCatalog: Option[String],
    characterSetSchema: Option[String],
    characterSetName: Option[String],
    characterRepertoire: Option[String],
    formOfUse: Option[String],
    defaultCollateCatalog: Option[String],
    defaultCollateSchema: Option[String],
    defaultCollateName: Option[String]
  ) {
    def toShape: Shape = Shape.NoDefaults(characterSetCatalog, characterSetSchema, characterSetName, characterRepertoire, formOfUse, defaultCollateCatalog, defaultCollateSchema, defaultCollateName)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"character_set_catalog, character_set_schema, character_set_name, character_repertoire, form_of_use, default_collate_catalog, default_collate_schema, default_collate_name"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".character_set_catalog, " ++ Fragment.const0(a) ++ fr".character_set_schema, " ++ Fragment.const0(a) ++ fr".character_set_name, " ++ Fragment.const0(a) ++ fr".character_repertoire, " ++ Fragment.const0(a) ++ fr".form_of_use, " ++ Fragment.const0(a) ++ fr".default_collate_catalog, " ++ Fragment.const0(a) ++ fr".default_collate_schema, " ++ Fragment.const0(a) ++ fr".default_collate_name"
  }

  case class Shape(characterSetCatalog: Option[String] = None, characterSetSchema: Option[String] = None, characterSetName: Option[String] = None, characterRepertoire: Option[String] = None, formOfUse: Option[String] = None, defaultCollateCatalog: Option[String] = None, defaultCollateSchema: Option[String] = None, defaultCollateName: Option[String] = None)

  object Shape {
    def NoDefaults(characterSetCatalog: Option[String], characterSetSchema: Option[String], characterSetName: Option[String], characterRepertoire: Option[String], formOfUse: Option[String], defaultCollateCatalog: Option[String], defaultCollateSchema: Option[String], defaultCollateName: Option[String]): Shape = Shape(characterSetCatalog, characterSetSchema, characterSetName, characterRepertoire, formOfUse, defaultCollateCatalog, defaultCollateSchema, defaultCollateName)
  }

    private val zippedRowComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2),
        (row) => (row.characterSetCatalog, (row.characterSetSchema, (row.characterSetName, (row.characterRepertoire, (row.formOfUse, (row.defaultCollateCatalog, (row.defaultCollateSchema, (row.defaultCollateName))))))))
      )
    }

    private val zippedShapeComposite = Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.StringMeta))))))))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2._1,
    t._2._2._1,
    t._2._2._2._1,
    t._2._2._2._2._1,
    t._2._2._2._2._2._1,
    t._2._2._2._2._2._2._1,
    t._2._2._2._2._2._2._2),
        (row) => (row.characterSetCatalog, (row.characterSetSchema, (row.characterSetName, (row.characterRepertoire, (row.formOfUse, (row.defaultCollateCatalog, (row.defaultCollateSchema, (row.defaultCollateName))))))))
      )
    }

}
trait CharacterSets {
  import CharacterSets._

  def create(characterSetCatalog: Option[String] = None, characterSetSchema: Option[String] = None, characterSetName: Option[String] = None, characterRepertoire: Option[String] = None, formOfUse: Option[String] = None, defaultCollateCatalog: Option[String] = None, defaultCollateSchema: Option[String] = None, defaultCollateName: Option[String] = None): ConnectionIO[Row] = {
    create(Shape(characterSetCatalog, characterSetSchema, characterSetName, characterRepertoire, formOfUse, defaultCollateCatalog, defaultCollateSchema, defaultCollateName))
  }

  def createVoid(characterSetCatalog: Option[String] = None, characterSetSchema: Option[String] = None, characterSetName: Option[String] = None, characterRepertoire: Option[String] = None, formOfUse: Option[String] = None, defaultCollateCatalog: Option[String] = None, defaultCollateSchema: Option[String] = None, defaultCollateName: Option[String] = None): ConnectionIO[Unit] = {
    createVoid(Shape(characterSetCatalog, characterSetSchema, characterSetName, characterRepertoire, formOfUse, defaultCollateCatalog, defaultCollateSchema, defaultCollateName))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.information_schema.gen.CharacterSets.Shape]): Update[Shape] = {
    val sql = "INSERT INTO information_schema.character_sets (character_set_catalog, character_set_schema, character_set_name, character_repertoire, form_of_use, default_collate_catalog, default_collate_schema, default_collate_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.information_schema.gen.CharacterSets.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("character_set_catalog", "character_set_schema", "character_set_name", "character_repertoire", "form_of_use", "default_collate_catalog", "default_collate_schema", "default_collate_name")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.information_schema.gen.CharacterSets.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.information_schema.gen.CharacterSets.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.information_schema.gen.CharacterSets.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.information_schema.gen.CharacterSets.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM information_schema.character_sets
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
      FROM information_schema.character_sets
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

}
