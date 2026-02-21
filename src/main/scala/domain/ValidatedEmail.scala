package domain

import codec.StringDecoder

object ValidatedEmail {
  opaque type ValidatedEmail <: String = String

  def apply(value: String): Either[String, ValidatedEmail] =
    if value.contains("@") then Right(value)
    else Left(s"Invalid email: $value")

  // No =:= export — encoding works automatically via <:< from the upper bound.
  // Decoding goes through the smart constructor to enforce validation.
  given StringDecoder[ValidatedEmail] with {
    def decode(s: String): Either[String, ValidatedEmail] = apply(s)
  }
}
