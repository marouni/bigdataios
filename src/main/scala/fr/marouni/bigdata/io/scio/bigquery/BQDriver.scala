package fr.marouni.bigdata.io.scio.bigquery

import com.spotify.scio.ContextAndArgs
import com.spotify.scio.bigquery._
import com.spotify.scio.bigquery.types.BigQueryType

object BQDriver extends App {

  @BigQueryType.fromTable("bigquery-public-data:austin_bikeshare.bikeshare_stations")
  class Row

  @BigQueryType.toTable
  case class ActiveStation(name: String, id: Long)

  override def main(cmdArgs: Array[String]): Unit = {

    val (sc, args) = ContextAndArgs(cmdArgs)

    sc.typedBigQuery[Row]()
      .filter(row => row.status.contains("active"))
      .map(row => ActiveStation(row.name.getOrElse("N/A"), row.station_id.getOrElse(0L)))
      .saveAsTypedBigQuery("dev-tests-222219:dev_tests.active_stations_ref_4")

    sc.close()

  }

}
