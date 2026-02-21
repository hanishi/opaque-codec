package domain.after

import codec.OpaqueCodec

object SKU {
  opaque type SKU = String
  def apply(value: String): SKU = value
  given OpaqueCodec[SKU, String] = OpaqueCodec.fromEvidence
}
