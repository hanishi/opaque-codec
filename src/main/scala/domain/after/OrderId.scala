package domain.after

import codec.OpaqueCodec

object OrderId {
  opaque type OrderId = String
  def apply(value: String): OrderId = value
  given OpaqueCodec[OrderId, String] = OpaqueCodec.fromEvidence
}
