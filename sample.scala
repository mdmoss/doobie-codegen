package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TestForeignPk extends TestForeignPk {

  case class Name(value: mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName)

  case class Row(
    name: mdmoss.doobiegen.db.gen.TestForeignPk.Name
  ) {
    def toShape: Shape = Shape.NoDefaults(name.value)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"name"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".name"
  }

  case class Shape(name: mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName)

  object Shape {
    def NoDefaults(name: mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName): Shape = Shape(name)
  }

  implicit def TestForeignPkIdComposite: Composite[Name] = {
    implicitly[Composite[mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName]].xmap(
      (f1) => Name(f1),
      (a) => a.value
    )
  }

  private val zippedRowComposite = implicitly[Composite[mdmoss.doobiegen.db.gen.TestForeignPk.Name]]

  implicit def RowComposite: Composite[Row] = {
    zippedRowComposite.xmap(
      t => Row(t),
      (row) => (row.name)
    )
  }

  private val zippedShapeComposite = implicitly[Composite[mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName]]

  implicit def ShapeComposite: Composite[Shape] = {
    zippedShapeComposite.xmap(
      t => Shape(t),
      (row) => (row.name)
    )
  }

}
trait TestForeignPk {
  import TestForeignPk._

  def create(name: mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName): ConnectionIO[Row] = {
    create(Shape(name))
  }

  def createVoid(name: mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName): ConnectionIO[Unit] = {
    createVoid(Shape(name))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestForeignPk.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_foreign_pk (name) VALUES (?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestForeignPk.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("name")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestForeignPk.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestForeignPk.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestForeignPk.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestForeignPk.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def getInner(name: mdmoss.doobiegen.db.gen.TestForeignPk.Name): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_foreign_pk
      WHERE test_foreign_pk.name = ${name}
    """).query[Row]
  }
  def get(name: mdmoss.doobiegen.db.gen.TestForeignPk.Name): ConnectionIO[Row] = {
    getInner(name).unique
  }

  private[gen] def findInner(name: mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_foreign_pk
      WHERE test_foreign_pk.name = ${name}
    """).query[Row]
  }
  def find(name: mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName): ConnectionIO[Option[Row]] = {
    findInner(name).option
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_foreign_pk
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
      FROM test_foreign_pk
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

  private[gen] def multigetInnerBase(name: Option[Seq[mdmoss.doobiegen.db.gen.TestForeignPk.Name]]): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_foreign_pk
      WHERE (${name.isEmpty} OR test_foreign_pk.name = ANY(${{name}.toSeq.flatten.map(_.value.value).toArray}))
    """).query[Row]
  }

  def multiget(name: Seq[mdmoss.doobiegen.db.gen.TestForeignPk.Name]): ConnectionIO[List[Row]] = {
    if (name.nonEmpty) {
      val distinctValues = name.distinct
      for {
        resultRaw    <- multigetInnerBase(Some(distinctValues)).list
        resultGrouped = resultRaw.groupBy(_.name)
      } yield name.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }
  def multigetByName(name: Seq[mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName]): ConnectionIO[List[Row]] = {
    if (name.nonEmpty) {
      val distinctValues = name.distinct
      for {
        resultRaw    <- multigetInnerBase(Some(distinctValues.map(Name(_)))).list
        resultGrouped = resultRaw.groupBy(_.name.value)
      } yield name.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }

  private[gen] def updateInner(row: mdmoss.doobiegen.db.gen.TestForeignPk.Row): Update0 = {
    sql"""
      UPDATE test_foreign_pk
      SET name = ${row.name}
      WHERE name = ${row.name}
    """.update
  }
  def update(row: mdmoss.doobiegen.db.gen.TestForeignPk.Row): ConnectionIO[Int] = {
    updateInner(row).run
  }

}
