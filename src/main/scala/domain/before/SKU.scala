package domain.before

import spray.json.*

object SKU {
  opaque type SKU = String
  def apply(value: String): SKU = value
  extension (s: SKU) { def value: String = s }
  given Ordering[SKU] = Ordering.String.on(_.value)
  given JsonFormat[SKU] with {
    def write(s: SKU) = JsString(s.value)
    def read(json: JsValue) = json match {
      case JsString(str) => SKU(str)
      case other => deserializationError(s"Expected string, got $other")
    }
  }
}
