package domain.after

import codec.OpaqueCodec

object UserId {
  opaque type UserId = String
  def apply(value: String): UserId = value
  given OpaqueCodec[UserId, String] = OpaqueCodec.fromEvidence
}
