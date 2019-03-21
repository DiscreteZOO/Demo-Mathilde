package xyz.discretezoo.web.db.TESTMatrices
import java.util.UUID
import slick.collection.heterogeneous.HNil
import slick.lifted.{ProvenShape, Rep}
import xyz.discretezoo.web.DynamicSupport.ColumnSelector
import xyz.discretezoo.web.ZooPostgresProfile.api._

final class MatricesTable(tag: Tag) extends Table[Matrices](tag, "TEST_MATRICES") with ColumnSelector {

def ID: Rep[UUID] = column[UUID]("ID", O.PrimaryKey)
def mat: Rep[List[Int]] = column[List[Int]]("MAT")
def trace: Rep[Option[Int]] = column[Option[Int]]("TRACE")
def orthogonal: Rep[Option[Boolean]] = column[Option[Boolean]]("ORTHOGONAL")
def eigenvalues: Rep[List[Int]] = column[List[Int]]("EIGENVALUES")
def characteristic: Rep[List[Int]] = column[List[Int]]("CHARACTERISTIC")

def * : ProvenShape[Matrices] = (
ID ::
mat ::
trace ::
orthogonal ::
eigenvalues ::
characteristic ::
HNil
).mapTo[Matrices]

val select: Map[String, Rep[_]] = Map(
"mat" -> this.mat,
"trace" -> this.trace,
"orthogonal" -> this.orthogonal,
"eigenvalues" -> this.eigenvalues,
"characteristic" -> this.characteristic
)

val inCollection: Map[String, Rep[Boolean]] = Map(
"ID" -> true
)

}