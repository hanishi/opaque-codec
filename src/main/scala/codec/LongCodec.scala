package codec

trait LongCodec[T]:
  def encode(t: T): Long
  def decode(l: Long): T

object LongCodec:

  given LongCodec[Long] with
    def encode(t: Long): Long = t
    def decode(l: Long): Long = l

  private def fromConversion[T](
    to: T => Long,
    from: Long => T
  ): LongCodec[T] =
    new LongCodec[T]:
      def encode(t: T): Long = to(t)
      def decode(l: Long): T = from(l)

  inline given derived[T](using inline ev: T =:= Long): LongCodec[T] =
    fromConversion(ev(_), ev.flip(_))
