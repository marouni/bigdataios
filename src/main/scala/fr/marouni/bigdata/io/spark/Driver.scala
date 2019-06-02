package fr.marouni.bigdata.io.spark

import org.apache.spark.sql.SparkSession

object Driver extends App {

  override def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Datasource tests").master("local[*]").getOrCreate()

    spark
      .read
      .format("fr.marouni.bigdata.io.spark.SparkRandomTextGenerator")
      .option("reps", "100")
      .load()
      .show()
  }
}
