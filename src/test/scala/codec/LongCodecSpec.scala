package codec

import domain.Timestamp.Timestamp

class LongCodecSpec extends munit.FunSuite:

  test("LongCodec[Long] resolves and round-trips") {
    val codec = summon[LongCodec[Long]]
    assertEquals(codec.encode(42L), 42L)
    assertEquals(codec.decode(42L), 42L)
  }

  test("LongCodec[Timestamp] auto-derives and round-trips") {
    val codec = summon[LongCodec[Timestamp]]
    val ts = domain.Timestamp(1700000000L)
    val encoded = codec.encode(ts)
    val decoded = codec.decode(encoded)
    assertEquals(encoded, 1700000000L)
    assertEquals(codec.encode(decoded), 1700000000L)
  }
