package domain.after

object ImpressionTimestamp {
  opaque type ImpressionTimestamp = Long
  def apply(value: Long): ImpressionTimestamp = value
  given ImpressionTimestamp =:= Long = summon
}
