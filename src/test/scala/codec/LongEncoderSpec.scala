package codec

import domain.Timestamp.Timestamp

class LongEncoderSpec extends munit.FunSuite:

  test("LongEncoder[Long] resolves and encodes") {
    val encoder = summon[LongEncoder[Long]]
    assertEquals(encoder.encode(42L), 42L)
  }

  test("LongEncoder[Timestamp] auto-derives via =:= and encodes") {
    val encoder = summon[LongEncoder[Timestamp]]
    val ts = domain.Timestamp(1700000000L)
    assertEquals(encoder.encode(ts), 1700000000L)
  }
