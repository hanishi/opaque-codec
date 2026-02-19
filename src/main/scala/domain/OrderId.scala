package domain

object OrderId:
  opaque type OrderId = String

  def apply(value: String): OrderId = value

  given OrderId =:= String = summon
