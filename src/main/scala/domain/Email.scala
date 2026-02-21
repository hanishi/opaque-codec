package domain

object Email {
  opaque type Email = String

  def apply(value: String): Email = value

  given Email =:= String = summon
}
