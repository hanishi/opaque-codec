package codec

trait OpaqueCodec[T, U]:
  def encode(t: T): U
  def decode(u: U): T

object OpaqueCodec:

  private def fromConversion[T, U](
    to: T => U,
    from: U => T
  ): OpaqueCodec[T, U] =
    new OpaqueCodec[T, U]:
      def encode(t: T): U = to(t)
      def decode(u: U): T = from(u)

  inline given derived[T, U](using inline ev: T =:= U): OpaqueCodec[T, U] =
    fromConversion(ev(_), ev.flip(_))
