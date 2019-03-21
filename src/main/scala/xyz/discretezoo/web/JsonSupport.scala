package xyz.discretezoo.web

import java.util.UUID
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{JsonFormat, _}
import xyz.discretezoo.web.ZooJsonAPI._
import xyz.discretezoo.web.db.TESTMatrices.Matrices

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol with NullOptions {

  implicit val formatCount: RootJsonFormat[Count] = jsonFormat1(Count)
  implicit val formatParameter: RootJsonFormat[Parameter] = jsonFormat2(Parameter)
  implicit val formatSearchParameters: RootJsonFormat[SearchParameters] = jsonFormat3(SearchParameters)
  implicit val formatResultsParameters: RootJsonFormat[ResultsParameters] = jsonFormat4(ResultsParameters)

  implicit object formatUUID extends JsonFormat[UUID] {
    def write(u: UUID): JsValue = u.toString.toJson
    def read(json: JsValue) =
      throw new UnsupportedOperationException("Missing implementation for the UUID JsonReader")
  }

  implicit object formatZooObject extends RootJsonFormat[ZooObject] {
    override def write(o: ZooObject): JsValue = o match {
      case o: Matrices => JsObject(
        List(
        Some("mat" -> o.mat.toJson),
        Some("trace" -> o.trace.toJson),
        Some("orthogonal" -> o.orthogonal.toJson),
        Some("eigenvalues" -> o.eigenvalues.toJson),
        Some("characteristic" -> o.characteristic.toJson)
        ).flatten: _*
      )
    }
    override def read(json: JsValue): ZooObject =
      throw new UnsupportedOperationException("Missing implementation for the ZooObject JsonReader")
  }

  implicit object formatSearchResults extends RootJsonFormat[SearchResult] {
    override def write(o: SearchResult): JsValue = {
      JsObject("pages" -> o.pages.toJson, "data" -> o.data.toJson)
    }
   override def read(json: JsValue): SearchResult =
      throw new UnsupportedOperationException("Missing implementation for the SearchResults JsonReader")
  }
}