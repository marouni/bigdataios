package fr.marouni.bigdata.io.spark

import java.util

import org.apache.spark.sql.Row
import org.apache.spark.sql.sources.v2.reader.{DataReader, DataReaderFactory, DataSourceReader}
import org.apache.spark.sql.sources.v2.{DataSourceOptions, DataSourceV2, ReadSupport}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

import scala.util.Random

/**
  * One of the characteristics of V2 API’s is it’s exposed in terms of Java interfaces rather than scala traits.
  * The primary reason for this is better interop with java.
  */

/**
  * The source entry point
  */
class SparkRandomTextGenerator extends DataSourceV2 with ReadSupport {
  override def createReader(options: DataSourceOptions): DataSourceReader =
    new RandomTextReader(options.get("reps").get().toInt)
}

/**
  * The driver entry point, here we're starting an single factory
  * @param reps number of repetitions by worker
  */
class RandomTextReader(val reps: Int) extends DataSourceReader {
  override def readSchema(): StructType = StructType(Array(StructField("random_string", StringType)))

  override def createDataReaderFactories(): util.List[DataReaderFactory[Row]] = {
    val factoryList = new java.util.ArrayList[DataReaderFactory[Row]]
    factoryList.add(new RandomTextReaderFactory(reps))
    factoryList
  }
}

/**
  * Actual code called on each worker
  * @param reps number of repetitions
  */
class RandomTextReaderFactory(val reps: Int) extends DataReaderFactory[Row] with DataReader[Row] {
  override def createDataReader(): DataReader[Row] = new RandomTextReaderFactory(reps)

  var index = 0

  override def next(): Boolean = index < reps

  override def get(): Row = {
    index = index + 1
    case class Datum(name: String, age: Int)
    Row(Random.alphanumeric.take(10).mkString)
  }

  override def close(): Unit = Unit
}
