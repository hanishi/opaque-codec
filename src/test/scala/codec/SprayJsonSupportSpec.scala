package codec

import spray.json.*
import codec.OpaqueJsonSupport.given
import domain.after.UserId.UserId
import domain.after.OrderId.OrderId
import domain.after.Email.Email
import domain.after.SKU.SKU
import domain.after.Timestamp.Timestamp
import domain.after.ValidatedEmail.ValidatedEmail

class SprayJsonSupportSpec extends munit.FunSuite {

  test("JsonFormat[UserId] serializes to JSON string and back") {
    val uid = domain.after.UserId("user-42")
    val json = uid.toJson
    assertEquals(json, JsString("user-42"))
    assertEquals(json.convertTo[UserId], uid)
  }

  test("JsonFormat[OrderId] serializes to JSON string and back") {
    val oid = domain.after.OrderId("order-99")
    val json = oid.toJson
    assertEquals(json, JsString("order-99"))
    assertEquals(json.convertTo[OrderId], oid)
  }

  test("JsonFormat[Email] serializes to JSON string and back") {
    val email = domain.after.Email("alice@example.com")
    val json = email.toJson
    assertEquals(json, JsString("alice@example.com"))
    assertEquals(json.convertTo[Email], email)
  }

  test("JsonFormat[SKU] serializes to JSON string and back") {
    val sku = domain.after.SKU("SKU-1234")
    val json = sku.toJson
    assertEquals(json, JsString("SKU-1234"))
    assertEquals(json.convertTo[SKU], sku)
  }

  test("JsonFormat[Timestamp] serializes to JSON number and back") {
    val ts = domain.after.Timestamp(1700000000L)
    val json = ts.toJson
    assertEquals(json, JsNumber(1700000000L))
    assertEquals(json.convertTo[Timestamp], ts)
  }

  test("JsonFormat[UserId] rejects non-string JSON") {
    intercept[DeserializationException] {
      JsNumber(42).convertTo[UserId]
    }
  }

  test("JsonFormat[Timestamp] rejects non-number JSON") {
    intercept[DeserializationException] {
      JsString("not-a-number").convertTo[Timestamp]
    }
  }

  test("JsonFormat[ValidatedEmail] serializes via <:< encoder and validated decoder") {
    val Right(ve) = domain.after.ValidatedEmail("alice@example.com"): @unchecked
    val json = ve.toJson
    assertEquals(json, JsString("alice@example.com"))
    assertEquals(json.convertTo[ValidatedEmail], ve)
  }

  test("JsonFormat[ValidatedEmail] rejects invalid email on deserialization") {
    intercept[DeserializationException] {
      JsString("not-an-email").convertTo[ValidatedEmail]
    }
  }
}
