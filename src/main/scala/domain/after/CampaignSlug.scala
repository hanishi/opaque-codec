package domain.after

import codec.OpaqueCodec

object CampaignSlug {
  opaque type CampaignSlug = String
  def apply(value: String): CampaignSlug = value
  given OpaqueCodec[CampaignSlug, String] = OpaqueCodec.fromEvidence
}
