package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}

import doobie.postgres.imports._
import scalaz._, Scalaz._

object TestForeignPk extends TestForeignPk {

  case class Name(value: mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName)

  case class Row(
    name: mdmoss.doobiegen.db.gen.TestForeignPk.Name
  ) {
    def toShape: Shape = Shape.NoDefaults(name.value)
  }

  case class Shape(name: mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName)

  object Shape {
    def NoDefaults(name: mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName): Shape = Shape(name)
  }

}

trait TestForeignPk {
  import TestForeignPk._

  def create(name: mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName): ConnectionIO[Row] = {
    create(Shape(name))
  }

  private[db] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestForeignPk.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_foreign_pk (name) VALUES (?)"
    Update[Shape](sql)
  }

  private[db] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestForeignPk.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("name")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestForeignPk.Shape]): ConnectionIO[List[Row]] = {
    createManyP(values).runLog.map(_.toList)
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestForeignPk.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  private[db] def getInner(name: mdmoss.doobiegen.db.gen.TestForeignPk.Name): Query0[Row] = {
    sql"""
      SELECT test_foreign_pk.name
      FROM test_foreign_pk
      WHERE test_foreign_pk.name = ${name}
    """.query[Row]
  }
  def get(name: mdmoss.doobiegen.db.gen.TestForeignPk.Name): ConnectionIO[Row] = {
    getInner(name).unique
  }

  private[db] def findInner(name: mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName): Query0[Row] = {
    sql"""
      SELECT test_foreign_pk.name
      FROM test_foreign_pk
      WHERE test_foreign_pk.name = ${name}
    """.query[Row]
  }
  def find(name: mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName): ConnectionIO[Option[Row]] = {
    findInner(name).option
  }

  private[db] def allInner(offset: Long, limit: Long): Query0[Row] = {
    sql"""
      SELECT test_foreign_pk.name
      FROM test_foreign_pk
      OFFSET $offset
      LIMIT $limit
    """.query[Row]
  }

  def all(offset: Long, limit: Long): ConnectionIO[List[Row]] = {
    allInner(offset, limit).list
  }

  def allUnbounded(): ConnectionIO[List[Row]] = {
    allInner(0, 9223372036854775807L).list
  }

  private[db] def countInner(): Query0[Long] = {
    sql"""
      SELECT COUNT(*)
      FROM test_foreign_pk
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

  private[db] def multigetInnerBase(name: Option[Seq[mdmoss.doobiegen.db.gen.TestForeignPk.Name]]): Query0[Row] = {
    sql"""
      SELECT test_foreign_pk.name
      FROM test_foreign_pk
      LEFT JOIN unnest(${{name}.toSeq.flatten.map(_.value.value).toArray}::text[]) WITH ORDINALITY t0(val, ord) ON t0.val = test_foreign_pk.name
      WHERE (${name.isEmpty} OR test_foreign_pk.name = ANY(${{name}.toSeq.flatten.map(_.value.value).toArray}))
      ORDER BY t0.ord
    """.query[Row]
  }

  def multiget(name: Seq[mdmoss.doobiegen.db.gen.TestForeignPk.Name]): ConnectionIO[List[Row]] = {
    multigetInnerBase(Some(name)).list
  }
  def multigetByName(name: Seq[mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName]): ConnectionIO[List[Row]] = {
    multigetInnerBase(Some(name.map(Name(_)))).list
  }

  private[db] def updateInner(row: mdmoss.doobiegen.db.gen.TestForeignPk.Row): Update0 = {
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
         