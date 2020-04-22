package mdmoss.doobiegen.db.s2.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import doobie.postgres.imports._

object Test2 extends Test2 {

  case class Id(value: Long)

  case class Row(
    id: mdmoss.doobiegen.db.s2.gen.Test2.Id,
    partner: mdmoss.doobiegen.db.s1.gen.Test.Id
  ) {
    def toShape: Shape = Shape.NoDefaults(id.value, partner)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"id, partner"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".id, " ++ Fragment.const0(a) ++ fr".partner"
  }

  case class Shape(id: Long, partner: mdmoss.doobiegen.db.s1.gen.Test.Id)

  object Shape {
    def NoDefaults(id: Long, partner: mdmoss.doobiegen.db.s1.gen.Test.Id): Shape = Shape(id, partner)
  }

    implicit def Test2IdComposite: Composite[Id] = {
      Composite.fromMeta(doobie.util.meta.Meta.LongMeta).xmap(
        (f1) => Id(f1),
        (a) => a.value
      )
    }

    private val zippedRowComposite = implicitly[Composite[mdmoss.doobiegen.db.s2.gen.Test2.Id]]
    .zip(implicitly[Composite[mdmoss.doobiegen.db.s1.gen.Test.Id]])

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.id, (row.partner))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(implicitly[Composite[mdmoss.doobiegen.db.s1.gen.Test.Id]])

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.id, (row.partner))
      )
    }

}
trait Test2 {
  import Test2._

  def create(id: Long, partner: mdmoss.doobiegen.db.s1.gen.Test.Id): ConnectionIO[Row] = {
    create(Shape(id, partner))
  }

  def createVoid(id: Long, partner: mdmoss.doobiegen.db.s1.gen.Test.Id): ConnectionIO[Unit] = {
    createVoid(Shape(id, partner))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.s2.gen.Test2.Shape]): Update[Shape] = {
    val sql = "INSERT INTO s2.test2 (id, partner) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.s2.gen.Test2.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("id", "partner")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.s2.gen.Test2.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.s2.gen.Test2.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.s2.gen.Test2.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.s2.gen.Test2.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def getInner(id: mdmoss.doobiegen.db.s2.gen.Test2.Id): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM s2.test2
      WHERE s2.test2.id = ${id}
    """).query[Row]
  }
  def get(id: mdmoss.doobiegen.db.s2.gen.Test2.Id): ConnectionIO[Row] = {
    getInner(id).unique
  }

  private[gen] def findInner(id: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM s2.test2
      WHERE s2.test2.id = ${id}
    """).query[Row]
  }
  def find(id: Long): ConnectionIO[Option[Row]] = {
    findInner(id).option
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM s2.test2
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
      FROM s2.test2
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

  private[gen] def multigetInnerBase(id: Option[Seq[mdmoss.doobiegen.db.s2.gen.Test2.Id]], partner: Option[Seq[mdmoss.doobiegen.db.s1.gen.Test.Id]]): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM s2.test2
      WHERE (${id.isEmpty} OR s2.test2.id = ANY(${{id}.toSeq.flatten.map(_.value).toArray}))
    AND (${partner.isEmpty} OR s2.test2.partner = ANY(${{partner}.toSeq.flatten.map(_.value).toArray}))
    """).query[Row]
  }

  def multiget(id: Seq[mdmoss.doobiegen.db.s2.gen.Test2.Id]): ConnectionIO[List[Row]] = {
    if (id.nonEmpty) {
      val distinctValues = id.distinct
      for {
        resultRaw    <- multigetInnerBase(Some(distinctValues), None).list
        resultGrouped = resultRaw.groupBy(_.id)
      } yield id.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }
  def multigetByPartner(partner: Seq[mdmoss.doobiegen.db.s1.gen.Test.Id]): ConnectionIO[List[Row]] = {
    if (partner.nonEmpty) {
      val distinctValues = partner.distinct
      for {
        resultRaw    <- multigetInnerBase(None, Some(distinctValues)).list
        resultGrouped = resultRaw.groupBy(_.partner)
      } yield partner.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }
  def getByPartner(partner: mdmoss.doobiegen.db.s1.gen.Test.Id): ConnectionIO[List[Row]] = {
    multigetInnerBase(None, Some(Seq(partner))).list
  }

  private[gen] def updateInner(row: mdmoss.doobiegen.db.s2.gen.Test2.Row): Update0 = {
    sql"""
      UPDATE s2.test2
      SET id = ${row.id}, partner = ${row.partner}
      WHERE id = ${row.id}
    """.update
  }
  def update(row: mdmoss.doobiegen.db.s2.gen.Test2.Row): ConnectionIO[Int] = {
    updateInner(row).run
  }

}
