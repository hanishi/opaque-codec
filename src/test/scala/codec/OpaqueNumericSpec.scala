package codec

import codec.OpaqueNumeric.{given, *}
import domain.after.BidPrice.BidPrice
import domain.after.Timestamp.Timestamp

class OpaqueNumericSpec extends munit.FunSuite {

  test("Numeric[BidPrice] auto-derives and supports addition") {
    val a = domain.after.BidPrice(BigDecimal("1.50"))
    val b = domain.after.BidPrice(BigDecimal("2.25"))
    val result = a + b
    assertEquals(summon[OpaqueCodec[BidPrice, BigDecimal]].encode(result), BigDecimal("3.75"))
  }

  test("Numeric[BidPrice] supports subtraction") {
    val a = domain.after.BidPrice(BigDecimal("5.00"))
    val b = domain.after.BidPrice(BigDecimal("1.75"))
    val result = a - b
    assertEquals(summon[OpaqueCodec[BidPrice, BigDecimal]].encode(result), BigDecimal("3.25"))
  }

  test("Numeric[BidPrice] supports multiplication") {
    val a = domain.after.BidPrice(BigDecimal("2.00"))
    val b = domain.after.BidPrice(BigDecimal("3.00"))
    val result = a * b
    assertEquals(summon[OpaqueCodec[BidPrice, BigDecimal]].encode(result), BigDecimal("6.00"))
  }

  test("List[BidPrice].sum works via Numeric") {
    val prices = List(
      domain.after.BidPrice(BigDecimal("1.00")),
      domain.after.BidPrice(BigDecimal("2.00")),
      domain.after.BidPrice(BigDecimal("3.00"))
    )
    val total = prices.sum
    assertEquals(summon[OpaqueCodec[BidPrice, BigDecimal]].encode(total), BigDecimal("6.00"))
  }

  test("Numeric[Timestamp] resolves for Long-based types") {
    val a = domain.after.Timestamp(100L)
    val b = domain.after.Timestamp(200L)
    val result = a + b
    assertEquals(summon[OpaqueCodec[Timestamp, Long]].encode(result), 300L)
  }

  test("Numeric[BidPrice] provides Ordering (since Numeric extends Ordering)") {
    val a = domain.after.BidPrice(BigDecimal("1.50"))
    val b = domain.after.BidPrice(BigDecimal("2.50"))
    val num = summon[Numeric[BidPrice]]
    assertEquals(num.compare(a, b) < 0, true)
  }

  test("Numeric[BidPrice] supports parseString") {
    val num = summon[Numeric[BidPrice]]
    val result = num.parseString("42.5")
    assert(result.isDefined)
    assertEquals(summon[OpaqueCodec[BidPrice, BigDecimal]].encode(result.get), BigDecimal("42.5"))
  }

  test("Numeric[BidPrice] supports fromInt") {
    val num = summon[Numeric[BidPrice]]
    val result = num.fromInt(10)
    assertEquals(summon[OpaqueCodec[BidPrice, BigDecimal]].encode(result), BigDecimal(10))
  }

  test("Numeric[BidPrice] supports unary negation") {
    val a = domain.after.BidPrice(BigDecimal("3.50"))
    val result = -a
    assertEquals(summon[OpaqueCodec[BidPrice, BigDecimal]].encode(result), BigDecimal("-3.50"))
  }
}
