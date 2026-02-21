package domain.before

import spray.json.*

object CreativeId {
  opaque type CreativeId = String
  def apply(value: String): CreativeId = value
  extension (id: CreativeId) { def value: String = id }
  given Ordering[CreativeId] = Ordering.String.on(_.value)
  given JsonFormat[CreativeId] with {
    def write(id: CreativeId) = JsString(id.value)
    def read(json: JsValue) = json match {
      case JsString(s) => CreativeId(s)
      case other => deserializationError(s"Expected string, got $other")
    }
  }
}
