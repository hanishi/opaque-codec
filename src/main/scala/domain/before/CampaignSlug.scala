package domain.before

import spray.json.*

object CampaignSlug {
  opaque type CampaignSlug = String
  def apply(value: String): CampaignSlug = value
  extension (s: CampaignSlug) { def value: String = s }
  given Ordering[CampaignSlug] = Ordering.String.on(_.value)
  given JsonFormat[CampaignSlug] with {
    def write(s: CampaignSlug) = JsString(s.value)
    def read(json: JsValue) = json match {
      case JsString(str) => CampaignSlug(str)
      case other => deserializationError(s"Expected string, got $other")
    }
  }
}
