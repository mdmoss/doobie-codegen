package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TestUuidMultiget extends TestUuidMultiget {

  case class Uuid(value: UUID)

  case class Row(
    uuid: mdmoss.doobiegen.db.gen.TestUuidMultiget.Uuid
  ) {
    def toShape: Shape = Shape.NoDefaults(uuid.value)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"uuid"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".uuid"
  }

  case class Shape(uuid: UUID)

  object Shape {
    def NoDefaults(uuid: UUID): Shape = Shape(uuid)
  }

    implicit def TestUuidMultigetIdComposite: Composite[Uuid] = {
      implicitly[Composite[UUID]].xmap(
        (f1) => Uuid(f1),
        (a) => a.value
      )
    }

    private val zippedRowComposite = implicitly[Composite[mdmoss.doobiegen.db.gen.TestUuidMultiget.Uuid]]

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t),
        (row) => (row.uuid)
      )
    }

    private val zippedShapeComposite = implicitly[Composite[UUID]]

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t),
        (row) => (row.uuid)
      )
    }

}
trait TestUuidMultiget {
  import TestUuidMultiget._

  def create(uuid: UUID): ConnectionIO[Row] = {
    create(Shape(uuid))
  }

  def createVoid(uuid: UUID): ConnectionIO[Unit] = {
    createVoid(Shape(uuid))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestUuidMultiget.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_uuid_multiget (uuid) VALUES (?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestUuidMultiget.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("uuid")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestUuidMultiget.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestUuidMultiget.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestUuidMultiget.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestUuidMultiget.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def getInner(uuid: mdmoss.doobiegen.db.gen.TestUuidMultiget.Uuid): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_uuid_multiget
      WHERE test_uuid_multiget.uuid = ${uuid}
    """).query[Row]
  }
  def get(uuid: mdmoss.doobiegen.db.gen.TestUuidMultiget.Uuid): ConnectionIO[Row] = {
    getInner(uuid).unique
  }

  private[gen] def findInner(uuid: UUID): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_uuid_multiget
      WHERE test_uuid_multiget.uuid = ${uuid}
    """).query[Row]
  }
  def find(uuid: UUID): ConnectionIO[Option[Row]] = {
    findInner(uuid).option
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_uuid_multiget
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
      FROM test_uuid_multiget
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

  private[gen] def multigetInnerBase(uuid: Option[Seq[mdmoss.doobiegen.db.gen.TestUuidMultiget.Uuid]]): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_uuid_multiget
      WHERE (${uuid.isEmpty} OR test_uuid_multiget.uuid = ANY(${{uuid}.toSeq.flatten.map(_.value).toArray}))
    """).query[Row]
  }

  def multiget(uuid: Seq[mdmoss.doobiegen.db.gen.TestUuidMultiget.Uuid]): ConnectionIO[List[Row]] = {
    if (uuid.nonEmpty) {
      val distinctValues = uuid.distinct
      for {
        resultRaw    <- multigetInnerBase(Some(distinctValues)).list
        resultGrouped = resultRaw.groupBy(_.uuid)
      } yield uuid.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }

  private[gen] def updateInner(row: mdmoss.doobiegen.db.gen.TestUuidMultiget.Row): Update0 = {
    sql"""
      UPDATE test_uuid_multiget
      SET uuid = ${row.uuid}
      WHERE uuid = ${row.uuid}
    """.update
  }
  def update(row: mdmoss.doobiegen.db.gen.TestUuidMultiget.Row): ConnectionIO[Int] = {
    updateInner(row).run
  }

}
