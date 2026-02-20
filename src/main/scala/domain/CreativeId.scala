package domain

object CreativeId:
  opaque type CreativeId = String
  def apply(value: String): CreativeId = value
  given CreativeId =:= String = summon
