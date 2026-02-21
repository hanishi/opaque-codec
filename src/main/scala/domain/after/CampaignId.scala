package domain.after

object CampaignId {
  opaque type CampaignId = String
  def apply(value: String): CampaignId = value
  given CampaignId =:= String = summon
}
