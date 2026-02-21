package domain.after

import codec.OpaqueCodec

object AdId {
  opaque type AdId = Long
  def apply(value: Long): AdId = value
  given OpaqueCodec[AdId, Long] = OpaqueCodec.fromEvidence
}
