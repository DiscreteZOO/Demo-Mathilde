package xyz.discretezoo.web.db.TESTMatrices
import java.util.UUID
import xyz.discretezoo.web.ZooObject

case class Matrices(
ID: UUID,
mat: List[Int],
trace: Option[Int],
orthogonal: Option[Boolean],
eigenvalues: List[Int],
characteristic: List[Int]) extends ZooObject {

def select: Map[String, _] = Map(
"mat" -> this.mat,
"trace" -> this.trace,
"orthogonal" -> this.orthogonal,
"eigenvalues" -> this.eigenvalues,
"characteristic" -> this.characteristic
)

}