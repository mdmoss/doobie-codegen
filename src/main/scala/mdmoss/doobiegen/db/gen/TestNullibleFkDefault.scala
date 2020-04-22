package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object TestNullibleFkDefault extends TestNullibleFkDefault {

  case class Id(value: Long)

  case class Row(
    id: mdmoss.doobiegen.db.gen.TestNullibleFkDefault.Id,
    fk: Option[Long]
  ) {
    def toShape: Shape = Shape.NoDefaults(id.value, fk)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"id, fk"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".id, " ++ Fragment.const0(a) ++ fr".fk"
  }

  case class Shape(id: Long, fk: Option[Long] = None)

  object Shape {
    def NoDefaults(id: Long, fk: Option[Long]): Shape = Shape(id, fk)
  }

    implicit def TestNullibleFkDefaultIdComposite: Composite[Id] = {
      Composite.fromMeta(doobie.util.meta.Meta.LongMeta).xmap(
        (f1) => Id(f1),
        (a) => a.value
      )
    }

    private val zippedRowComposite = implicitly[Composite[mdmoss.doobiegen.db.gen.TestNullibleFkDefault.Id]]
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta))

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.id, (row.fk))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(Composite.fromMetaOption(doobie.util.meta.Meta.LongMeta))

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.id, (row.fk))
      )
    }

}
trait TestNullibleFkDefault {
  import TestNullibleFkDefault._

  def create(id: Long, fk: Option[Long] = None): ConnectionIO[Row] = {
    create(Shape(id, fk))
  }

  def createVoid(id: Long, fk: Option[Long] = None): ConnectionIO[Unit] = {
    createVoid(Shape(id, fk))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.TestNullibleFkDefault.Shape]): Update[Shape] = {
    val sql = "INSERT INTO test_nullible_fk_default (id, fk) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.TestNullibleFkDefault.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("id", "fk")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.TestNullibleFkDefault.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.TestNullibleFkDefault.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.TestNullibleFkDefault.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.TestNullibleFkDefault.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def getInner(id: mdmoss.doobiegen.db.gen.TestNullibleFkDefault.Id): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_nullible_fk_default
      WHERE test_nullible_fk_default.id = ${id}
    """).query[Row]
  }
  def get(id: mdmoss.doobiegen.db.gen.TestNullibleFkDefault.Id): ConnectionIO[Row] = {
    getInner(id).unique
  }

  private[gen] def findInner(id: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_nullible_fk_default
      WHERE test_nullible_fk_default.id = ${id}
    """).query[Row]
  }
  def find(id: Long): ConnectionIO[Option[Row]] = {
    findInner(id).option
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_nullible_fk_default
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
      FROM test_nullible_fk_default
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

  private[gen] def multigetInnerBase(id: Option[Seq[mdmoss.doobiegen.db.gen.TestNullibleFkDefault.Id]]): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM test_nullible_fk_default
      WHERE (${id.isEmpty} OR test_nullible_fk_default.id = ANY(${{id}.toSeq.flatten.map(_.value).toArray}))
    """).query[Row]
  }

  def multiget(id: Seq[mdmoss.doobiegen.db.gen.TestNullibleFkDefault.Id]): ConnectionIO[List[Row]] = {
    if (id.nonEmpty) {
      val distinctValues = id.distinct
      for {
        resultRaw    <- multigetInnerBase(Some(distinctValues)).list
        resultGrouped = resultRaw.groupBy(_.id)
      } yield id.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }

  private[gen] def updateInner(row: mdmoss.doobiegen.db.gen.TestNullibleFkDefault.Row): Update0 = {
    sql"""
      UPDATE test_nullible_fk_default
      SET id = ${row.id}, fk = ${row.fk}
      WHERE id = ${row.id}
    """.update
  }
  def update(row: mdmoss.doobiegen.db.gen.TestNullibleFkDefault.Row): ConnectionIO[Int] = {
    updateInner(row).run
  }

}
