package domain

object AdId {
  opaque type AdId = Long
  def apply(value: Long): AdId = value
  given AdId =:= Long = summon
}
