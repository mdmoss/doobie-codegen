package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TestPkName extends TestPkName {

  case class SomeComplicatedName(value: String)

  case class Row(
    someComplicatedName: mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName
  ) {
    def toShape: Shape = Shape.NoDefaults(someComplicatedName.value)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"some_complicated_name"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".some_complicated_name"
  }

  case class Shape(someComplicatedName: String)

  object Shape {
    def NoDefaults(someComplicatedName: String): Shape = Shape(someComplicatedName)
  }

    implicit def TestPkNameIdComposite: Composite[SomeComplicatedName] = {
      Composite.fromMeta(doobie.util.meta.Meta.StringMeta).xmap(
        (f1) => SomeComplicatedName(f1),
        (a) => a.value
      )
    }

    private val zippedRowComposite = implicitly[Composite[mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName]]

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t),
        (row) => (row.someComplicatedName)
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.StringMeta)

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t),
        (row) => (row.someComplicatedName)
      )
    }

}
trait TestPkName {
  import TestPkName._

  def create(someComplicatedName: String): ConnectionIO[Row] = {
    create(Shape(someComplicatedName))
  }

  def createVoid(someComplicatedName: String): ConnectionIO[Unit] = {
    createVoid(Shape(someComplicatedName))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestPkName.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_pk_name (some_complicated_name) VALUES (?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestPkName.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("some_complicated_name")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestPkName.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestPkName.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestPkName.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestPkName.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def getInner(someComplicatedName: mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_pk_name
      WHERE test_pk_name.some_complicated_name = ${someComplicatedName}
    """).query[Row]
  }
  def get(someComplicatedName: mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName): ConnectionIO[Row] = {
    getInner(someComplicatedName).unique
  }

  private[gen] def findInner(someComplicatedName: String): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_pk_name
      WHERE test_pk_name.some_complicated_name = ${someComplicatedName}
    """).query[Row]
  }
  def find(someComplicatedName: String): ConnectionIO[Option[Row]] = {
    findInner(someComplicatedName).option
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_pk_name
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
      FROM test_pk_name
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

  private[gen] def multigetInnerBase(someComplicatedName: Option[Seq[mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName]]): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_pk_name
      WHERE (${someComplicatedName.isEmpty} OR test_pk_name.some_complicated_name = ANY(${{someComplicatedName}.toSeq.flatten.map(_.value).toArray}))
    """).query[Row]
  }

  def multiget(someComplicatedName: Seq[mdmoss.doobiegen.db.gen.TestPkName.SomeComplicatedName]): ConnectionIO[List[Row]] = {
    if (someComplicatedName.nonEmpty) {
      val distinctValues = someComplicatedName.distinct
      for {
        resultRaw    <- multigetInnerBase(Some(distinctValues)).list
        resultGrouped = resultRaw.groupBy(_.someComplicatedName)
      } yield someComplicatedName.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }

  private[gen] def updateInner(row: mdmoss.doobiegen.db.gen.TestPkName.Row): Update0 = {
    sql"""
      UPDATE test_pk_name
      SET some_complicated_name = ${row.someComplicatedName}
      WHERE some_complicated_name = ${row.someComplicatedName}
    """.update
  }
  def update(row: mdmoss.doobiegen.db.gen.TestPkName.Row): ConnectionIO[Int] = {
    updateInner(row).run
  }

}
