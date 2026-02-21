package domain.before

import spray.json.*

object Email {
  opaque type Email = String
  def apply(value: String): Email = value
  extension (e: Email) { def value: String = e }
  given Ordering[Email] = Ordering.String.on(_.value)
  given JsonFormat[Email] with {
    def write(e: Email) = JsString(e.value)
    def read(json: JsValue) = json match {
      case JsString(s) => Email(s)
      case other => deserializationError(s"Expected string, got $other")
    }
  }
}
