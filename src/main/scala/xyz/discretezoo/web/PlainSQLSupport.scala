package xyz.discretezoo.web

import slick.ast.Ordering.{Desc, Direction}
import slick.jdbc.GetResult
import xyz.discretezoo.web.ZooPostgresProfile.api._

trait PlainSQLSupport[T <: ZooObject] {

  val inCollection: Map[String, String]
  val tableName: String
  implicit val getResult: GetResult[T]

  def count(qp: SearchParam): DBIO[Int] = {
    val where = if (plainGetWhere(qp).length > 0) s" WHERE ${plainGetWhere(qp)}" else ""
    sql"""SELECT COUNT(*) FROM "#$tableName"#$where;""".as[Int].head
  }

  def get(qp: ResultParam): DBIO[Seq[T]] = {
    sql"""SELECT * FROM "#$tableName"
                   WHERE #${plainGetWhere(qp.parameters)}
                   #${plainSortBy(qp.sort)}
                   LIMIT #${qp.limit} OFFSET #${qp.offset};""".as[T]
  }

  private def plainSortBy(sortBy: Seq[(String, Direction)]): String = {
    sortBy.map(t => s"${t._1}${if (t._2 == Desc) " DESC" else ""}").mkString(", ")
  }

  private def plainFilter(condition: Condition): String = {
    condition match {
      case c: BooleanCondition => s"""(${if (!c.b) "not " else ""}"${c.field.toUpperCase}")"""
      case c: NumericCondition => s"""("${c.field.toUpperCase}" ${c.op} ${c.i})"""
    }
  }

  private def plainGetWhere(sp: SearchParam): String = {
    val collections = sp.collections.map(inCollection.get)
      .collect({ case Some(v) => s"""("$v" IS NOT NULL)""" }).mkString(" OR ")
    val collectionCondition = if (collections.length > 0) s"($collections)" else ""
    (sp.filter.map(plainFilter) :+ collectionCondition).mkString(" AND ")
  }

}
