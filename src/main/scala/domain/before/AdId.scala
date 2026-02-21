package domain.before

import spray.json.*

object AdId {
  opaque type AdId = Long
  def apply(value: Long): AdId = value
  extension (id: AdId) { def value: Long = id }
  given Ordering[AdId] = Ordering.Long.on(_.value)
  given JsonFormat[AdId] with {
    def write(id: AdId) = JsNumber(id.value)
    def read(json: JsValue) = json match {
      case JsNumber(n) => AdId(n.toLong)
      case other => deserializationError(s"Expected number, got $other")
    }
  }
}
