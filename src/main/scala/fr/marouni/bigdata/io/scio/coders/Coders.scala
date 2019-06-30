package fr.marouni.bigdata.io.scio.coders

import java.io.{InputStream, OutputStream}

class Coders {

  trait ScioCoder[A] {
    def encode(a: A): OutputStream
    def decode(is : InputStream) : A
  }

  def encode[A: ScioCoder](a: A): OutputStream =
    implicitly[ScioCoder[A]].encode(a)

  def decode[A: ScioCoder](is : InputStream): A =
    implicitly[ScioCoder[A]].decode(is)

}
