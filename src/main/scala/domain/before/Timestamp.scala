package domain.before

import spray.json.*

object Timestamp {
  opaque type Timestamp = Long
  def apply(value: Long): Timestamp = value
  extension (ts: Timestamp) { def value: Long = ts }
  given Ordering[Timestamp] = Ordering.Long.on(_.value)
  given JsonFormat[Timestamp] with {
    def write(ts: Timestamp) = JsNumber(ts.value)
    def read(json: JsValue) = json match {
      case JsNumber(n) => Timestamp(n.toLong)
      case other => deserializationError(s"Expected number, got $other")
    }
  }
}
