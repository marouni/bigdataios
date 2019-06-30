package fr.marouni.bigdata.io.scio.sources

import com.spotify.scio.ScioContext
import com.spotify.scio.io.{ScioIO, Tap, TapT}
import com.spotify.scio.values.SCollection

class MyCustomIO[T] extends ScioIO[T] {
  override type ReadP = this.type
  override type WriteP = this.type
  override val tapT: TapT[T] = ???

  override protected def read(sc: ScioContext, params: MyCustomIO.this.type): SCollection[T] = ???

  override protected def write(data: SCollection[T], params: MyCustomIO.this.type): Tap[tapT.T] = ???

  override def tap(read: MyCustomIO.this.type): Tap[tapT.T] = ???
}
