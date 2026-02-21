package domain.before

import spray.json.*

object ValidatedEmail {
  opaque type ValidatedEmail = String
  def apply(value: String): Either[String, ValidatedEmail] =
    if value.contains("@") then Right(value)
    else Left(s"Invalid email: $value")
  extension (e: ValidatedEmail) { def value: String = e }
  given Ordering[ValidatedEmail] = Ordering.String.on(_.value)
  given JsonFormat[ValidatedEmail] with {
    def write(e: ValidatedEmail) = JsString(e.value)
    def read(json: JsValue) = json match {
      case JsString(s) => apply(s) match {
        case Right(e) => e
        case Left(err) => deserializationError(err)
      }
      case other => deserializationError(s"Expected string, got $other")
    }
  }
}
