package domain.after

import codec.OpaqueCodec

object CreativeId {
  opaque type CreativeId = String
  def apply(value: String): CreativeId = value
  given OpaqueCodec[CreativeId, String] = OpaqueCodec.fromEvidence
}
