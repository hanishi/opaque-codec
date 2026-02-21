package codec

import domain.after.Timestamp.Timestamp

class LongDecoderSpec extends munit.FunSuite {

  test("LongDecoder[Long] resolves and decodes") {
    val decoder = summon[LongDecoder[Long]]
    assertEquals(decoder.decode(42L), Right(42L))
  }

  test("LongDecoder[Timestamp] auto-derives via =:= and decodes") {
    val decoder = summon[LongDecoder[Timestamp]]
    val expected = domain.after.Timestamp(1700000000L)
    assertEquals(decoder.decode(1700000000L), Right(expected))
  }
}
