package domain.after

import codec.OpaqueCodec

object ImpressionTimestamp {
  opaque type ImpressionTimestamp = Long
  def apply(value: Long): ImpressionTimestamp = value
  given OpaqueCodec[ImpressionTimestamp, Long] = OpaqueCodec.fromEvidence
}
