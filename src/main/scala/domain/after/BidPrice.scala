package domain.after

object BidPrice {
  opaque type BidPrice = BigDecimal
  def apply(value: BigDecimal): BidPrice = value
  given BidPrice =:= BigDecimal = summon
}
