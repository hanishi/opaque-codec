package codec

import domain.UserId.UserId
import domain.OrderId.OrderId
import domain.Email.Email
import domain.SKU.SKU
import domain.Timestamp.Timestamp

class OpaqueCodecSpec extends munit.FunSuite {

  test("OpaqueCodec[String, String] resolves and round-trips") {
    val codec = summon[OpaqueCodec[String, String]]
    assertEquals(codec.encode("hello"), "hello")
    assertEquals(codec.decode("hello"), "hello")
  }

  test("OpaqueCodec[UserId, String] auto-derives and round-trips") {
    val codec = summon[OpaqueCodec[UserId, String]]
    val uid = domain.UserId("user-42")
    assertEquals(codec.encode(uid), "user-42")
    assertEquals(codec.decode("user-42"), uid)
  }

  test("OpaqueCodec[OrderId, String] auto-derives and round-trips") {
    val codec = summon[OpaqueCodec[OrderId, String]]
    val oid = domain.OrderId("order-99")
    assertEquals(codec.encode(oid), "order-99")
    assertEquals(codec.decode("order-99"), oid)
  }

  test("OpaqueCodec[Email, String] auto-derives and round-trips") {
    val codec = summon[OpaqueCodec[Email, String]]
    val email = domain.Email("alice@example.com")
    assertEquals(codec.encode(email), "alice@example.com")
    assertEquals(codec.decode("alice@example.com"), email)
  }

  test("OpaqueCodec[SKU, String] auto-derives and round-trips") {
    val codec = summon[OpaqueCodec[SKU, String]]
    val sku = domain.SKU("SKU-1234")
    assertEquals(codec.encode(sku), "SKU-1234")
    assertEquals(codec.decode("SKU-1234"), sku)
  }

  test("OpaqueCodec[Timestamp, Long] auto-derives and round-trips") {
    val codec = summon[OpaqueCodec[Timestamp, Long]]
    val ts = domain.Timestamp(1700000000L)
    assertEquals(codec.encode(ts), 1700000000L)
    assertEquals(codec.decode(1700000000L), ts)
  }
}
