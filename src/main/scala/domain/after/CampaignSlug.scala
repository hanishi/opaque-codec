package domain.after

object CampaignSlug {
  opaque type CampaignSlug = String
  def apply(value: String): CampaignSlug = value
  given CampaignSlug =:= String = summon
}
