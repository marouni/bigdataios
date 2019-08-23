package fr.marouni.bigdata.io.scio.avro


import com.spotify.scio.ContextAndArgs
import com.spotify.scio.avro._
import com.spotify.scio.parquet.avro._
import com.spotify.scio.values.SCollection
import fr.marouni.bigdata.io.avro.User

object AvroDriver extends App {

  override def main(cmdArgs: Array[String]): Unit = {

    val (sc1, _) = ContextAndArgs(cmdArgs)

    sc1
      .parallelize(Seq("ONE", "TWO", "THREE"))
      .map(x => {
        User.newBuilder().setName(x).setFavoriteNumber(x.length).setFavoriteColor(x).build()
      }
      )
      .saveAsAvroFile("/tmp/gen_avro_file")

    sc1.close().waitUntilDone()

    val (sc2, _) = ContextAndArgs(cmdArgs)

    sc2
      .avroFile[User]("/tmp/gen_avro_file/*")
      .map(_.toString)
      .saveAsTextFile("/tmp/output_avro_to_text")

    sc2.close()

    val (sc3, _) = ContextAndArgs(cmdArgs)

    sc3
      .parallelize(Seq("ONE", "TWO", "THREE"))
      .map(x => {
        User.newBuilder().setName(x).setFavoriteNumber(x.length).setFavoriteColor(x).build()
      })
        .saveAsParquetAvroFile("/tmp/output_parquet_from_avro_records")

    sc3.close()

    val (sc4, _) = ContextAndArgs(cmdArgs)

    // Macros for generating column projections and row predicates
    val projection = Projection[User](_.getFavoriteNumber)
    val predicate = Predicate[User](x => x.getFavoriteNumber > 3)

    sc4.parquetAvroFile[User]("/tmp/output_parquet_from_avro_records/*", projection, predicate)
      // Map out projected fields right after reading
      .map(_.getFavoriteNumber)
        .saveAsTextFile("/tmp/output_parquet_projected")

    sc4.close()

    val (sc5, _) = ContextAndArgs(cmdArgs)

    val wordcount: SCollection[(Int, Long)] = sc1
      .parallelize(Seq("ONE", "TWO", "THREE"))
      .map(_.length)
      .countByValue

    sc5.close()


  }
}
