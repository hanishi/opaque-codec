package domain.after

import codec.OpaqueCodec

object BidPrice {
  opaque type BidPrice = BigDecimal
  def apply(value: BigDecimal): BidPrice = value
  given OpaqueCodec[BidPrice, BigDecimal] = OpaqueCodec.fromEvidence
}
