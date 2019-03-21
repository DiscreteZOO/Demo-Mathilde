package xyz.discretezoo.web

import slick.lifted.TableQuery
import xyz.discretezoo.web.db.ZooDb

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import xyz.discretezoo.web.ZooPostgresProfile.api._
import xyz.discretezoo.web.db.TESTMatrices.MatricesTable

object Create {

  def main(args: Array[String]): Unit = {

    object tbMatrices extends TableQuery(new MatricesTable(_))

    try {
      Await.result(ZooDb.db.run(DBIO.seq(
        tbMatrices.schema.create
      )), Duration.Inf)
    }

  }

}
