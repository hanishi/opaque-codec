package codec

object OpaqueNumeric {

  // OpaqueCodec[T, U] guarantees T and U erase to the same JVM type,
  // so Numeric[U] IS Numeric[T] at runtime. Reusing the existing instance
  // via asInstanceOf avoids the virtual dispatch overhead of wrapping
  // every operation through codec.encode/decode.
  given derived[T, U](using codec: OpaqueCodec[T, U], num: Numeric[U]): Numeric[T] =
    num.asInstanceOf[Numeric[T]]

  extension [T](x: T)(using num: Numeric[T]) {
    def +(y: T): T = num.plus(x, y)
    def -(y: T): T = num.minus(x, y)
    def *(y: T): T = num.times(x, y)
    def unary_- : T = num.negate(x)
  }
}
