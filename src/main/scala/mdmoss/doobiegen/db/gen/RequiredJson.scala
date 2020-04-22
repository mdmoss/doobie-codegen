package mdmoss.doobiegen.db.gen

/* Todo handle imports better */
import doobie.imports._
import java.sql.{Date, Timestamp, Time}
import java.util.UUID
import java.time.{LocalDate, LocalDateTime}
import scalaz._, Scalaz._

import argonaut.{Json, Parse}
import org.postgresql.util.PGobject
import doobie.postgres.imports._

object RequiredJson extends RequiredJson {

  case class Id(value: Long)

  case class Row(
    id: mdmoss.doobiegen.db.gen.RequiredJson.Id,
    j: argonaut.Json
  ) {
    def toShape: Shape = Shape.NoDefaults(id.value, j)
  }

  object Row {
    val ColumnsFragment: Fragment = fr"id, j"
    def aliasedColumnsFragment(a: String): Fragment = Fragment.const0(a) ++ fr".id, " ++ Fragment.const0(a) ++ fr".j"
  }

  case class Shape(id: Long, j: argonaut.Json)

  object Shape {
    def NoDefaults(id: Long, j: argonaut.Json): Shape = Shape(id, j)
  }

    implicit def RequiredJsonIdComposite: Composite[Id] = {
      Composite.fromMeta(doobie.util.meta.Meta.LongMeta).xmap(
        (f1) => Id(f1),
        (a) => a.value
      )
    }

    private val zippedRowComposite = implicitly[Composite[mdmoss.doobiegen.db.gen.RequiredJson.Id]]
    .zip(implicitly[Composite[argonaut.Json]])

    implicit def RowComposite: Composite[Row] = {
      zippedRowComposite.xmap(
        t => Row(t._1,
    t._2),
        (row) => (row.id, (row.j))
      )
    }

    private val zippedShapeComposite = Composite.fromMeta(doobie.util.meta.Meta.LongMeta)
    .zip(implicitly[Composite[argonaut.Json]])

    implicit def ShapeComposite: Composite[Shape] = {
      zippedShapeComposite.xmap(
        t => Shape(t._1,
    t._2),
        (row) => (row.id, (row.j))
      )
    }

}
trait RequiredJson {
  import RequiredJson._

  implicit val JsonMeta: doobie.imports.Meta[Json] =
    doobie.imports.Meta.other[PGobject]("json").xmap[Json](
      a => Parse.parse(a.getValue).fold(sys.error, identity), // failure raises an exception
      a => {
        val p = new PGobject
        p.setType("json")
        p.setValue(a.nospaces)
        p
      }
    )

  def create(id: Long, j: argonaut.Json): ConnectionIO[Row] = {
    create(Shape(id, j))
  }

  def createVoid(id: Long, j: argonaut.Json): ConnectionIO[Unit] = {
    createVoid(Shape(id, j))
  }

  private[gen] def insertMany(values: List[mdmoss.doobiegen.db.gen.RequiredJson.Shape]): Update[Shape] = {
    val sql = "INSERT INTO required_json (id, j) VALUES (?, ?)"
    Update[Shape](sql)
  }

  private[gen] def createManyP(values: List[mdmoss.doobiegen.db.gen.RequiredJson.Shape]): scalaz.stream.Process[ConnectionIO, Row] = {
    insertMany(values).updateManyWithGeneratedKeys[Row]("id", "j")(values)
  }

  def createMany(values: List[mdmoss.doobiegen.db.gen.RequiredJson.Shape]): ConnectionIO[List[Row]] = {
    if (values.nonEmpty) createManyP(values).runLog.map(_.toList) else List.empty.point[ConnectionIO]
  }

  def createManyVoid(values: List[mdmoss.doobiegen.db.gen.RequiredJson.Shape]): ConnectionIO[Unit] = {
    if (values.nonEmpty) insertMany(values).updateMany[List](values).map(_ => ()) else ().point[ConnectionIO]
  }

  def create(shape: mdmoss.doobiegen.db.gen.RequiredJson.Shape): ConnectionIO[Row] = {
    createMany(shape :: Nil).map(_.head)
  }

  def createVoid(shape: mdmoss.doobiegen.db.gen.RequiredJson.Shape): ConnectionIO[Unit] = {
    createManyVoid(shape :: Nil)
  }

  private[gen] def getInner(id: mdmoss.doobiegen.db.gen.RequiredJson.Id): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM required_json
      WHERE required_json.id = ${id}
    """).query[Row]
  }
  def get(id: mdmoss.doobiegen.db.gen.RequiredJson.Id): ConnectionIO[Row] = {
    getInner(id).unique
  }

  private[gen] def findInner(id: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM required_json
      WHERE required_json.id = ${id}
    """).query[Row]
  }
  def find(id: Long): ConnectionIO[Option[Row]] = {
    findInner(id).option
  }

  private[gen] def allInner(offset: Long, limit: Long): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM required_json
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
      FROM required_json
    """.query[Long]
  }
  def count(): ConnectionIO[Long] = {
    countInner().unique
  }

  private[gen] def multigetInnerBase(id: Option[Seq[mdmoss.doobiegen.db.gen.RequiredJson.Id]]): Query0[Row] = {
    (sql"""
      SELECT """ ++ Row.ColumnsFragment ++ sql"""
      FROM required_json
      WHERE (${id.isEmpty} OR required_json.id = ANY(${{id}.toSeq.flatten.map(_.value).toArray}))
    """).query[Row]
  }

  def multiget(id: Seq[mdmoss.doobiegen.db.gen.RequiredJson.Id]): ConnectionIO[List[Row]] = {
    if (id.nonEmpty) {
      val distinctValues = id.distinct
      for {
        resultRaw    <- multigetInnerBase(Some(distinctValues)).list
        resultGrouped = resultRaw.groupBy(_.id)
      } yield id.toList.flatMap(x => resultGrouped.getOrElse(x, List.empty))
    } else List.empty.point[ConnectionIO]
  }

  private[gen] def updateInner(row: mdmoss.doobiegen.db.gen.RequiredJson.Row): Update0 = {
    sql"""
      UPDATE required_json
      SET id = ${row.id}, j = ${row.j}
      WHERE id = ${row.id}
    """.update
  }
  def update(row: mdmoss.doobiegen.db.gen.RequiredJson.Row): ConnectionIO[Int] = {
    updateInner(row).run
  }

}
