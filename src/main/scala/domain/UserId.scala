package domain

object UserId {
  opaque type UserId = String

  def apply(value: String): UserId = value

  given UserId =:= String = summon
}
