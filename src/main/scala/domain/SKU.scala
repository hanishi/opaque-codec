package domain

object SKU {
  opaque type SKU = String

  def apply(value: String): SKU = value

  given SKU =:= String = summon
}
