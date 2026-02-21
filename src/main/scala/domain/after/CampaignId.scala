package domain.after

import codec.OpaqueCodec

object CampaignId {
  opaque type CampaignId = String
  def apply(value: String): CampaignId = value
  given OpaqueCodec[CampaignId, String] = OpaqueCodec.fromEvidence
}
