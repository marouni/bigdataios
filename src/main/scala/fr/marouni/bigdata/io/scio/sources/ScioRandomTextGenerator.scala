package fr.marouni.bigdata.io.scio.sources


import com.spotify.scio.ScioContext
import com.spotify.scio.io.{ScioIO, Tap, TapOf, TapT}
import com.spotify.scio.values.SCollection
import fr.marouni.bigdata.io.beam.sources.RandomStringGeneratorIO
import fr.marouni.bigdata.io.scio.sources.ScioRandomTextGenerator.ReadParams

case class ScioRandomTextGenerator() extends ScioIO[String] {

  override type ReadP = ScioRandomTextGenerator.ReadParams
  override type WriteP = Nothing

  override val tapT: TapT[String] = TapOf[String]

  override protected def read(sc: ScioContext, params: ReadParams): SCollection[String] = {
    sc
      .customInput("RandomStringGeneratorIO", new RandomStringGeneratorIO.Read(params.reps))
  }

  override protected def write(data: SCollection[String], params: Nothing): Tap[tapT.T] = ???

  override def tap(read: ReadParams): Tap[tapT.T] = ???
}

object ScioRandomTextGenerator {
  case class ReadParams(reps : Int)

  implicit class GenerateRandomStrings(val scioContext: ScioContext) extends AnyVal {
    def generateRandomStrings(reps : Int): SCollection[String] = scioContext
      .read[String](ScioRandomTextGenerator())(ScioRandomTextGenerator.ReadParams(reps))
  }
}
