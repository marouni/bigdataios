package fr.marouni.bigdata.io.spark.sources

import org.apache.spark.sql.SparkSession

object SparkDataSourceDriver extends App {

  override def main(args: Array[String]): Unit = {
    // Get hold of a Spark session
    val spark = SparkSession.builder().appName("WordCount tests").master("local[*]").getOrCreate()

    // Use the IO as source and dump result to console
    spark
      .read
      .format("fr.marouni.bigdata.io.spark.sources.SparkRandomTextGenerator")
      .option("reps", "100")
      .load()
      .show()
  }
}
