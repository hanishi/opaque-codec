package patterns.numeric

import codec.OpaqueNumeric.{given, *}
import patterns.numeric.Inches.Inches
import patterns.numeric.Centimeters.Centimeters

class LengthSpec extends munit.FunSuite {

  test("Inches to Centimeters conversion") {
    val inches = Inches(1.0)
    val cm = inches.toCentimeters
    assertEqualsDouble(cm.value, 2.54, 0.001)
  }

  test("Centimeters to Inches conversion") {
    val cm = Centimeters(2.54)
    val inches = cm.toInches
    assertEqualsDouble(inches.value, 1.0, 0.001)
  }

  test("conversion round-trip: Inches -> Centimeters -> Inches") {
    val original = Inches(5.0)
    val roundTripped = original.toCentimeters.toInches
    assertEqualsDouble(roundTripped.value, 5.0, 0.001)
  }

  test("conversion round-trip: Centimeters -> Inches -> Centimeters") {
    val original = Centimeters(10.0)
    val roundTripped = original.toInches.toCentimeters
    assertEqualsDouble(roundTripped.value, 10.0, 0.001)
  }

  test("Inches arithmetic: addition with + operator") {
    val result = Inches(1.0) + Inches(2.0)
    assertEqualsDouble(result.value, 3.0, 0.001)
  }

  test("Inches arithmetic: subtraction with - operator") {
    val result = Inches(5.0) - Inches(2.0)
    assertEqualsDouble(result.value, 3.0, 0.001)
  }

  test("Inches arithmetic: multiplication with * operator") {
    val result = Inches(3.0) * Inches(4.0)
    assertEqualsDouble(result.value, 12.0, 0.001)
  }

  test("Centimeters arithmetic: addition with + operator") {
    val result = Centimeters(2.54) + Centimeters(5.08)
    assertEqualsDouble(result.value, 7.62, 0.001)
  }

  test("List[Inches].sum works via Numeric") {
    val lengths = List(Inches(1.0), Inches(2.0), Inches(3.0))
    val total = lengths.sum
    assertEqualsDouble(total.value, 6.0, 0.001)
  }

  test("List[Centimeters].sum works via Numeric") {
    val lengths = List(Centimeters(2.54), Centimeters(5.08), Centimeters(7.62))
    val total = lengths.sum
    assertEqualsDouble(total.value, 15.24, 0.001)
  }

  test("type safety: Inches and Centimeters are distinct types") {
    // Type safety is enforced at compile time, not runtime.
    // The following would NOT compile:
    //   Inches(1.0) + Centimeters(2.0)  // won't compile — type mismatch
    // Both resolve Numeric instances (may share the underlying Numeric[Double]):
    val numInches = summon[Numeric[Inches]]
    val numCm = summon[Numeric[Centimeters]]
    assert(numInches != null)
    assert(numCm != null)
  }

  test("unary negation with - operator") {
    val a = Inches(3.0)
    val result = -a
    assertEqualsDouble(result.value, -3.0, 0.001)
  }
}
