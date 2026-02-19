package codec

import spray.json.*

object SprayJsonSupport extends LowPrioritySprayJsonSupport:

  // --- Bidirectional JsonFormat from StringCodec (=:= based, higher priority) ---
  given sprayJsonFormat[T](using codec: StringCodec[T]): JsonFormat[T] with
    def write(t: T) = JsString(codec.encode(t))
    def read(json: JsValue): T =
      json match
        case JsString(s) => codec.decode(s)
        case other =>
          deserializationError(s"Expected JSON string, got $other")

  // --- Bidirectional JsonFormat from LongCodec (=:= based, higher priority) ---
  given sprayLongJsonFormat[T](using codec: LongCodec[T]): JsonFormat[T] with
    def write(t: T) = JsNumber(codec.encode(t))
    def read(json: JsValue): T =
      json match
        case JsNumber(n) => codec.decode(n.toLongExact)
        case other =>
          deserializationError(s"Expected JSON number, got $other")

trait LowPrioritySprayJsonSupport:

  // --- JsonFormat from StringEncoder + StringDecoder (<:< based, lower priority) ---
  given sprayValidatedStringJsonFormat[T](using encoder: StringEncoder[T], decoder: StringDecoder[T]): JsonFormat[T] with
    def write(t: T) = JsString(encoder.encode(t))
    def read(json: JsValue): T =
      json match
        case JsString(s) =>
          decoder.decode(s) match
            case Right(t) => t
            case Left(err) => deserializationError(err)
        case other =>
          deserializationError(s"Expected JSON string, got $other")

  // --- JsonFormat from LongEncoder + LongDecoder (<:< based, lower priority) ---
  given sprayValidatedLongJsonFormat[T](using encoder: LongEncoder[T], decoder: LongDecoder[T]): JsonFormat[T] with
    def write(t: T) = JsNumber(encoder.encode(t))
    def read(json: JsValue): T =
      json match
        case JsNumber(n) =>
          decoder.decode(n.toLongExact) match
            case Right(t) => t
            case Left(err) => deserializationError(err)
        case other =>
          deserializationError(s"Expected JSON number, got $other")
