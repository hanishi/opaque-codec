package codec

import spray.json.*
import codec.SprayJsonSupport.given
import domain.UserId.UserId
import domain.OrderId.OrderId
import domain.Email.Email
import domain.SKU.SKU
import domain.Timestamp.Timestamp
import domain.ValidatedEmail.ValidatedEmail

class SprayJsonSupportSpec extends munit.FunSuite:

  test("JsonFormat[UserId] serializes to JSON string and back") {
    val format = summon[JsonFormat[UserId]]
    val uid = domain.UserId("user-42")
    val json = format.write(uid)
    assertEquals(json, JsString("user-42"))
    assertEquals(format.read(json), uid)
  }

  test("JsonFormat[OrderId] serializes to JSON string and back") {
    val format = summon[JsonFormat[OrderId]]
    val oid = domain.OrderId("order-99")
    val json = format.write(oid)
    assertEquals(json, JsString("order-99"))
    assertEquals(format.read(json), oid)
  }

  test("JsonFormat[Email] serializes to JSON string and back") {
    val format = summon[JsonFormat[Email]]
    val email = domain.Email("alice@example.com")
    val json = format.write(email)
    assertEquals(json, JsString("alice@example.com"))
    assertEquals(format.read(json), email)
  }

  test("JsonFormat[SKU] serializes to JSON string and back") {
    val format = summon[JsonFormat[SKU]]
    val sku = domain.SKU("SKU-1234")
    val json = format.write(sku)
    assertEquals(json, JsString("SKU-1234"))
    assertEquals(format.read(json), sku)
  }

  test("JsonFormat[Timestamp] serializes to JSON number and back") {
    val format = summon[JsonFormat[Timestamp]]
    val ts = domain.Timestamp(1700000000L)
    val json = format.write(ts)
    assertEquals(json, JsNumber(1700000000L))
    assertEquals(format.read(json), ts)
  }

  test("JsonFormat[UserId] rejects non-string JSON") {
    val format = summon[JsonFormat[UserId]]
    intercept[DeserializationException] {
      format.read(JsNumber(42))
    }
  }

  test("JsonFormat[Timestamp] rejects non-number JSON") {
    val format = summon[JsonFormat[Timestamp]]
    intercept[DeserializationException] {
      format.read(JsString("not-a-number"))
    }
  }

  test("JsonFormat[ValidatedEmail] serializes via <:< encoder and validated decoder") {
    val format = summon[JsonFormat[ValidatedEmail]]
    val Right(ve) = domain.ValidatedEmail("alice@example.com"): @unchecked
    val json = format.write(ve)
    assertEquals(json, JsString("alice@example.com"))
    assertEquals(format.read(json), ve)
  }

  test("JsonFormat[ValidatedEmail] rejects invalid email on deserialization") {
    val format = summon[JsonFormat[ValidatedEmail]]
    intercept[DeserializationException] {
      format.read(JsString("not-an-email"))
    }
  }
