package patterns.matchable

import codec.OpaqueCodec

object Street {
  opaque type Street = String
  def apply(value: String): Street = value
  given OpaqueCodec[Street, String] = OpaqueCodec.fromEvidence

  extension (s: Street) {
    def value: String = s
  }
}

object City {
  opaque type City = String
  def apply(value: String): City = value
  given OpaqueCodec[City, String] = OpaqueCodec.fromEvidence

  extension (c: City) {
    def value: String = c
  }
}
