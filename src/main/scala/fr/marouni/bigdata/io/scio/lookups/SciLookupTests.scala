package fr.marouni.bigdata.io.scio.lookups

import java.util.UUID

import com.spotify.scio.ContextAndArgs
import com.spotify.scio.io.{ClosedTap, Tap}
import com.spotify.scio.values.SCollection
import com.spotify.scio.sql._

object SciLookupTests extends App {

  case class Model(id: Int, name: String, description: String)

  override def main(cmdlineArgs: Array[String]): Unit = {
    val (sc, args) = ContextAndArgs(cmdlineArgs)

    val identity: PartialFunction[Int, Int] = {
      case d: Int if d != 0 => d
    }

    val lookupTable: SCollection[(Int, String)] = sc
      .parallelize(Seq((1, "ONE"), (2, "TWO"), (5, "FIVE")))

    val closedTap: ClosedTap[Int] =
      sc
      .parallelize(Seq(1, 2, 1, 2, 1, 5))
      .materialize

    val myTap: Tap[Int] = sc.close().waitUntilDone().tap(closedTap)

    myTap.value.foreach(println(_))

    sc
      .parallelize(Seq(1, 2, 1, 2, 1, 5))
      .collect(identity)
      .hashLookup(lookupTable)
      .saveAsTextFile("/tmp/output_hash")

    sc
      .parallelize(Seq(1, 2, 1, 2, 1, 5))
      .cross(sc.parallelize(Seq(1, 2)))
      .saveAsTextFile("/tmp/output_cross")

    sc
      .parallelize(Seq(1, 2, 1, 2, 1, 5, 4))
      .map((_, UUID.randomUUID().toString))
      .join(lookupTable)
      .saveAsTextFile("/tmp/output_join")

    sc
      .parallelize(Seq(1, 2, 1, 2, 1, 5, 4))
      .map((_, UUID.randomUUID().toString))
      .skewedFullOuterJoin(lookupTable)
      .saveAsTextFile("/tmp/output_join")

    val inputTable = sc
      .parallelize(Seq("AZERTY", "QWERTY", "ITERTY", "FRETY", "GBERTY", "AZERTY"))
      .map(x => Model(x.length, x, s"$x : DETAILED DESCRIPTION"))
    tsql"select distinct(`id`), count(*) from $inputTable group by `id`"
      .as[(Int, Long)]
      .saveAsTextFile("/tmp/output_sql")

    sc.close()
  }
}