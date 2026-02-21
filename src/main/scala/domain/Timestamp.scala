package domain

object Timestamp {
  opaque type Timestamp = Long

  def apply(value: Long): Timestamp = value

  given Timestamp =:= Long = summon
}
