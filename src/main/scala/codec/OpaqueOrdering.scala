package codec

object OpaqueOrdering:

  private def fromEvidence[T, U](to: T => U, ord: Ordering[U]): Ordering[T] =
    ord.on(to)

  inline given derivedString[T](using inline ev: T =:= String, ord: Ordering[String]): Ordering[T] =
    fromEvidence(ev(_), ord)

  inline given derivedInt[T](using inline ev: T =:= Int, ord: Ordering[Int]): Ordering[T] =
    fromEvidence(ev(_), ord)

  inline given derivedLong[T](using inline ev: T =:= Long, ord: Ordering[Long]): Ordering[T] =
    fromEvidence(ev(_), ord)

  inline given derivedDouble[T](using inline ev: T =:= Double, ord: Ordering[Double]): Ordering[T] =
    fromEvidence(ev(_), ord)

  inline given derivedBigDecimal[T](using inline ev: T =:= BigDecimal, ord: Ordering[BigDecimal]): Ordering[T] =
    fromEvidence(ev(_), ord)
