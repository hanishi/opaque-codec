package codec

trait LongDecoder[T] {
  def decode(l: Long): Either[String, T]
}

object LongDecoder {

  given LongDecoder[Long] with {
    def decode(l: Long): Either[String, Long] = Right(l)
  }

  private def fromConversion[T](from: Long => T): LongDecoder[T] =
    (l: Long) => Right(from(l))

  inline given derived[T](using inline ev: T =:= Long): LongDecoder[T] =
    fromConversion(ev.flip(_))
}
