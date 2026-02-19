package codec

trait StringCodec[T]:
  def encode(t: T): String
  def decode(s: String): T

object StringCodec:

  given StringCodec[String] with
    def encode(t: String): String = t
    def decode(s: String): String = s

  private def fromConversion[T](
    to: T => String,
    from: String => T
  ): StringCodec[T] =
    new StringCodec[T]:
      def encode(t: T): String = to(t)
      def decode(s: String): T = from(s)

  inline given derived[T](using inline ev: T =:= String): StringCodec[T] =
    fromConversion(ev(_), ev.flip(_))
