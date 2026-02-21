package domain.after

import codec.OpaqueCodec

object Timestamp {
  opaque type Timestamp = Long
  def apply(value: Long): Timestamp = value
  given OpaqueCodec[Timestamp, Long] = OpaqueCodec.fromEvidence
}
