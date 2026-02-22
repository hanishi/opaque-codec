package domain.after

import codec.OpaqueCodec
import patterns.ulid.ULID

object TraceId {
  opaque type TraceId = String
  def apply(value: String): TraceId = value
  def newTraceId: TraceId = ULID.newULIDString
  given OpaqueCodec[TraceId, String] = OpaqueCodec.fromEvidence
}
