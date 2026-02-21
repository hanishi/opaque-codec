package domain.after

import codec.OpaqueCodec

object Email {
  opaque type Email = String
  def apply(value: String): Email = value
  given OpaqueCodec[Email, String] = OpaqueCodec.fromEvidence
}
