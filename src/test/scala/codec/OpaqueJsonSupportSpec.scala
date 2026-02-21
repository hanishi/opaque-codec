package codec

import spray.json.*
import codec.OpaqueJsonSupport.given
import domain.after.CampaignSlug.CampaignSlug
import domain.after.CreativeId.CreativeId
import domain.after.AdId.AdId
import domain.after.ImpressionTimestamp.ImpressionTimestamp
import domain.after.BudgetAmount.BudgetAmount
import domain.after.BidPrice.BidPrice

class OpaqueJsonSupportSpec extends munit.FunSuite {

  // --- String-based ---

  test("JsonFormat[CampaignSlug] via OpaqueCodec round-trips") {
    val slug = domain.after.CampaignSlug("summer-sale-2024")
    val json = slug.toJson
    assertEquals(json, JsString("summer-sale-2024"))
    assertEquals(json.convertTo[CampaignSlug], slug)
  }

  test("JsonFormat[CreativeId] via OpaqueCodec round-trips") {
    val cid = domain.after.CreativeId("creative-abc")
    val json = cid.toJson
    assertEquals(json, JsString("creative-abc"))
    assertEquals(json.convertTo[CreativeId], cid)
  }

  test("JsonFormat[CampaignSlug] rejects non-string JSON") {
    intercept[DeserializationException] {
      JsNumber(42).convertTo[CampaignSlug]
    }
  }

  // --- Long-based ---

  test("JsonFormat[AdId] via OpaqueCodec round-trips") {
    val aid = domain.after.AdId(123456L)
    val json = aid.toJson
    assertEquals(json, JsNumber(123456L))
    assertEquals(json.convertTo[AdId], aid)
  }

  test("JsonFormat[ImpressionTimestamp] via OpaqueCodec round-trips") {
    val ts = domain.after.ImpressionTimestamp(1700000000L)
    val json = ts.toJson
    assertEquals(json, JsNumber(1700000000L))
    assertEquals(json.convertTo[ImpressionTimestamp], ts)
  }

  test("JsonFormat[AdId] rejects non-number JSON") {
    intercept[DeserializationException] {
      JsString("not-a-number").convertTo[AdId]
    }
  }

  // --- BigDecimal-based ---

  test("JsonFormat[BudgetAmount] via OpaqueCodec round-trips") {
    val budget = domain.after.BudgetAmount(BigDecimal("10000.50"))
    val json = budget.toJson
    assertEquals(json, JsNumber(BigDecimal("10000.50")))
    assertEquals(json.convertTo[BudgetAmount], budget)
  }

  test("JsonFormat[BidPrice] via OpaqueCodec round-trips") {
    val bid = domain.after.BidPrice(BigDecimal("2.75"))
    val json = bid.toJson
    assertEquals(json, JsNumber(BigDecimal("2.75")))
    assertEquals(json.convertTo[BidPrice], bid)
  }

  test("JsonFormat[BidPrice] rejects non-number JSON") {
    intercept[DeserializationException] {
      JsString("not-a-number").convertTo[BidPrice]
    }
  }
}
