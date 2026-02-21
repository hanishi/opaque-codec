package domain.before

import spray.json.*

object ImpressionTimestamp {
  opaque type ImpressionTimestamp = Long
  def apply(value: Long): ImpressionTimestamp = value
  extension (ts: ImpressionTimestamp) { def value: Long = ts }
  given Ordering[ImpressionTimestamp] = Ordering.Long.on(_.value)
  given JsonFormat[ImpressionTimestamp] with {
    def write(ts: ImpressionTimestamp) = JsNumber(ts.value)
    def read(json: JsValue) = json match {
      case JsNumber(n) => ImpressionTimestamp(n.toLong)
      case other => deserializationError(s"Expected number, got $other")
    }
  }
}
