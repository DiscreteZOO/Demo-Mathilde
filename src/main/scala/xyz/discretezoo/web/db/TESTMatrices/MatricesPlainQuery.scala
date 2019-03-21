package xyz.discretezoo.web.db.TESTMatrices
import java.util.UUID
import slick.jdbc.GetResult
import xyz.discretezoo.web.PlainSQLSupport
import xyz.discretezoo.web.ZooPostgresProfile.api._

object MatricesPlainQuery extends PlainSQLSupport[Matrices] {

override val tableName: String = "TEST_MATRICES"
override implicit val getResult: GetResult[Matrices] = GetResult(r => Matrices(r.nextObject.asInstanceOf[UUID], r.<<[Seq[Int]].toList, r.<<, r.<<, r.<<[Seq[Int]].toList, r.<<[Seq[Int]].toList))

val inCollection: Map[String, String] = Map(
"Matrices" -> "ID"
)

}