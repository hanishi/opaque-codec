package codec

import domain.UserId.UserId
import domain.OrderId.OrderId
import domain.Email.Email
import domain.SKU.SKU

class StringCodecSpec extends munit.FunSuite:

  test("StringCodec[String] resolves and round-trips") {
    val codec = summon[StringCodec[String]]
    assertEquals(codec.encode("hello"), "hello")
    assertEquals(codec.decode("hello"), "hello")
  }

  test("StringCodec[UserId] auto-derives and round-trips") {
    val codec = summon[StringCodec[UserId]]
    val uid = domain.UserId("user-42")
    val encoded = codec.encode(uid)
    val decoded = codec.decode(encoded)
    assertEquals(encoded, "user-42")
    assertEquals(codec.encode(decoded), "user-42")
  }

  test("StringCodec[OrderId] auto-derives and round-trips") {
    val codec = summon[StringCodec[OrderId]]
    val oid = domain.OrderId("order-99")
    val encoded = codec.encode(oid)
    val decoded = codec.decode(encoded)
    assertEquals(encoded, "order-99")
    assertEquals(codec.encode(decoded), "order-99")
  }

  test("StringCodec[Email] auto-derives and round-trips") {
    val codec = summon[StringCodec[Email]]
    val email = domain.Email("alice@example.com")
    val encoded = codec.encode(email)
    val decoded = codec.decode(encoded)
    assertEquals(encoded, "alice@example.com")
    assertEquals(codec.encode(decoded), "alice@example.com")
  }

  test("StringCodec[SKU] auto-derives and round-trips") {
    val codec = summon[StringCodec[SKU]]
    val sku = domain.SKU("SKU-1234")
    val encoded = codec.encode(sku)
    val decoded = codec.decode(encoded)
    assertEquals(encoded, "SKU-1234")
    assertEquals(codec.encode(decoded), "SKU-1234")
  }
