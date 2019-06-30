package fr.marouni.bigdata.io.scio.wordcount

import com.spotify.scio.ContextAndArgs
import org.apache.beam.sdk.io.Compression

object ScioWordCountDriver extends App {

  override def main(cmdArgs: Array[String]): Unit = {

    val (sc, args) = ContextAndArgs(cmdArgs)

    sc
      .textFile("/home/abbass/Documents/presentations/nantes_scala_meetup/resources/*")
      .flatMap(line => line.split(" "))
      .countByValue
      .saveAsTextFile("/tmp/output_scio.txt", numShards = 1, compression = Compression.BZIP2)

    sc.close()

  }
}
