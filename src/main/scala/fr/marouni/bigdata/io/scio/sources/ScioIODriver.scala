package fr.marouni.bigdata.io.scio.sources

import com.spotify.scio.ContextAndArgs

object ScioIODriver extends App {

  override def main(cmdArgs: Array[String]): Unit = {

    val (sc, args) = ContextAndArgs(cmdArgs)

    import ScioRandomTextGenerator.GenerateRandomStrings

    sc
      .generateRandomStrings(5)
      .map(_.splitAt(24))
      .saveAsTextFile("/tmp/output_io")

    sc.close()

  }
}
