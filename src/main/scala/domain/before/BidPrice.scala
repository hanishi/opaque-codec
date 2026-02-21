package domain.before

import spray.json.*

object BidPrice {
  opaque type BidPrice = BigDecimal
  def apply(value: BigDecimal): BidPrice = value
  extension (b: BidPrice) { def value: BigDecimal = b }
  given Ordering[BidPrice] = Ordering[BigDecimal].on(_.value)
  given JsonFormat[BidPrice] with {
    def write(b: BidPrice) = JsNumber(b.value)
    def read(json: JsValue) = json match {
      case JsNumber(n) => BidPrice(n)
      case other => deserializationError(s"Expected number, got $other")
    }
  }
}
