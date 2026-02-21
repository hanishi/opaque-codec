package domain.before

import spray.json.*

object CampaignId {
  opaque type CampaignId = String
  def apply(value: String): CampaignId = value
  extension (id: CampaignId) { def value: String = id }
  given Ordering[CampaignId] = Ordering.String.on(_.value)
  given JsonFormat[CampaignId] with {
    def write(id: CampaignId) = JsString(id.value)
    def read(json: JsValue) = json match {
      case JsString(s) => CampaignId(s)
      case other => deserializationError(s"Expected string, got $other")
    }
  }
}
