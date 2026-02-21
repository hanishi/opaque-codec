package domain.before

import spray.json.*

object UserId {
  opaque type UserId = String
  def apply(value: String): UserId = value
  extension (id: UserId) { def value: String = id }
  given Ordering[UserId] = Ordering.String.on(_.value)
  given JsonFormat[UserId] with {
    def write(id: UserId) = JsString(id.value)
    def read(json: JsValue) = json match {
      case JsString(s) => UserId(s)
      case other => deserializationError(s"Expected string, got $other")
    }
  }
}
