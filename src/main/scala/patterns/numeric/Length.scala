package patterns.numeric

import codec.OpaqueCodec

object Inches {
  opaque type Inches = Double
  def apply(value: Double): Inches = value
  given OpaqueCodec[Inches, Double] = OpaqueCodec.fromEvidence

  extension (i: Inches) {
    def toCentimeters: Centimeters.Centimeters = Centimeters(i * 2.54)
    def value: Double = i
  }
}

object Centimeters {
  opaque type Centimeters = Double
  def apply(value: Double): Centimeters = value
  given OpaqueCodec[Centimeters, Double] = OpaqueCodec.fromEvidence

  extension (c: Centimeters) {
    def toInches: Inches.Inches = Inches(c / 2.54)
    def value: Double = c
  }
}
