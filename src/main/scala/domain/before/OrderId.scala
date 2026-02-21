package domain.before

import spray.json.*

object OrderId {
  opaque type OrderId = String
  def apply(value: String): OrderId = value
  extension (id: OrderId) { def value: String = id }
  given Ordering[OrderId] = Ordering.String.on(_.value)
  given JsonFormat[OrderId] with {
    def write(id: OrderId) = JsString(id.value)
    def read(json: JsValue) = json match {
      case JsString(s) => OrderId(s)
      case other => deserializationError(s"Expected string, got $other")
    }
  }
}
