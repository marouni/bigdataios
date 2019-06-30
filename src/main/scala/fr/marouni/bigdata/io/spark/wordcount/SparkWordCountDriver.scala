package fr.marouni.bigdata.io.spark.wordcount

import org.apache.spark.sql.SparkSession

object SparkWordCountDriver extends App {

  override def main(args: Array[String]): Unit = {

    // Get hold of a Spark session
    val spark = SparkSession.builder().appName("Datasource tests").master("local[*]").getOrCreate()

    import spark.sqlContext.implicits._

    // Use the IO as source and dump result to console
    spark
      .read
      .textFile("/home/abbass/Documents/presentations/nantes_scala_meetup/resources")
      .flatMap(line => line.split(" "))
      .groupByKey(identity)
      .count()
      .show()

    // Sleep to get a chance to check the Spark UI before shutting down
    Thread.sleep(100000000)
  }
}
