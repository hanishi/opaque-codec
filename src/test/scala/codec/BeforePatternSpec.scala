package codec

import spray.json.*
import codec.OpaqueJsonSupport.given
import codec.OpaqueOrdering.given

class BeforePatternSpec extends munit.FunSuite {

  // ── "Before": hand-written .value, Ordering, and JsonFormat per type ──

  import domain.before.UserId.{UserId as BeforeUserId, given}
  import domain.before.OrderId.{OrderId as BeforeOrderId, given}
  import domain.before.Email.{Email as BeforeEmail, given}
  import domain.before.SKU.{SKU as BeforeSKU, given}
  import domain.before.CampaignId.{CampaignId as BeforeCampaignId, given}
  import domain.before.CampaignSlug.{CampaignSlug as BeforeCampaignSlug, given}
  import domain.before.CreativeId.{CreativeId as BeforeCreativeId, given}
  import domain.before.Timestamp.{Timestamp as BeforeTimestamp, given}
  import domain.before.AdId.{AdId as BeforeAdId, given}
  import domain.before.ImpressionTimestamp.{ImpressionTimestamp as BeforeImpressionTimestamp, given}
  import domain.before.BudgetAmount.{BudgetAmount as BeforeBudgetAmount, given}
  import domain.before.BidPrice.{BidPrice as BeforeBidPrice, given}
  import domain.before.ValidatedEmail.{ValidatedEmail as BeforeValidatedEmail, given}

  // — String-based types —

  test("Before: UserId .value / Ordering / JsonFormat") {
    val id = domain.before.UserId("user-42")
    assertEquals(id.value, "user-42")
    val ids = List(domain.before.UserId("charlie"), domain.before.UserId("alice"), domain.before.UserId("bob"))
    assertEquals(ids.sorted.map(_.value), List("alice", "bob", "charlie"))
    val json = id.toJson
    assertEquals(json, JsString("user-42"))
    assertEquals(json.convertTo[BeforeUserId], id)
  }

  test("Before: OrderId .value / Ordering / JsonFormat") {
    val id = domain.before.OrderId("order-99")
    assertEquals(id.value, "order-99")
    val ids = List(domain.before.OrderId("c"), domain.before.OrderId("a"), domain.before.OrderId("b"))
    assertEquals(ids.sorted.map(_.value), List("a", "b", "c"))
    val json = id.toJson
    assertEquals(json, JsString("order-99"))
    assertEquals(json.convertTo[BeforeOrderId], id)
  }

  test("Before: Email .value / Ordering / JsonFormat") {
    val e = domain.before.Email("alice@example.com")
    assertEquals(e.value, "alice@example.com")
    val es = List(domain.before.Email("c@x"), domain.before.Email("a@x"), domain.before.Email("b@x"))
    assertEquals(es.sorted.map(_.value), List("a@x", "b@x", "c@x"))
    val json = e.toJson
    assertEquals(json, JsString("alice@example.com"))
    assertEquals(json.convertTo[BeforeEmail], e)
  }

  test("Before: SKU .value / Ordering / JsonFormat") {
    val s = domain.before.SKU("SKU-1234")
    assertEquals(s.value, "SKU-1234")
    val ss = List(domain.before.SKU("C"), domain.before.SKU("A"), domain.before.SKU("B"))
    assertEquals(ss.sorted.map(_.value), List("A", "B", "C"))
    val json = s.toJson
    assertEquals(json, JsString("SKU-1234"))
    assertEquals(json.convertTo[BeforeSKU], s)
  }

  test("Before: CampaignId .value / Ordering / JsonFormat") {
    val id = domain.before.CampaignId("camp-42")
    assertEquals(id.value, "camp-42")
    val ids = List(domain.before.CampaignId("c"), domain.before.CampaignId("a"), domain.before.CampaignId("b"))
    assertEquals(ids.sorted.map(_.value), List("a", "b", "c"))
    val json = id.toJson
    assertEquals(json, JsString("camp-42"))
    assertEquals(json.convertTo[BeforeCampaignId], id)
  }

  test("Before: CampaignSlug .value / Ordering / JsonFormat") {
    val s = domain.before.CampaignSlug("winter-sale")
    assertEquals(s.value, "winter-sale")
    val ss = List(domain.before.CampaignSlug("winter"), domain.before.CampaignSlug("autumn"), domain.before.CampaignSlug("spring"))
    assertEquals(ss.sorted.map(_.value), List("autumn", "spring", "winter"))
    val json = s.toJson
    assertEquals(json, JsString("winter-sale"))
    assertEquals(json.convertTo[BeforeCampaignSlug], s)
  }

  test("Before: CreativeId .value / Ordering / JsonFormat") {
    val id = domain.before.CreativeId("cr-7")
    assertEquals(id.value, "cr-7")
    val ids = List(domain.before.CreativeId("c"), domain.before.CreativeId("a"), domain.before.CreativeId("b"))
    assertEquals(ids.sorted.map(_.value), List("a", "b", "c"))
    val json = id.toJson
    assertEquals(json, JsString("cr-7"))
    assertEquals(json.convertTo[BeforeCreativeId], id)
  }

  // — Long-based types —

  test("Before: Timestamp .value / Ordering / JsonFormat") {
    val ts = domain.before.Timestamp(1700000000L)
    assertEquals(ts.value, 1700000000L)
    val tss = List(domain.before.Timestamp(300L), domain.before.Timestamp(100L), domain.before.Timestamp(200L))
    assertEquals(tss.sorted.map(_.value), List(100L, 200L, 300L))
    val json = ts.toJson
    assertEquals(json, JsNumber(1700000000L))
    assertEquals(json.convertTo[BeforeTimestamp], ts)
  }

  test("Before: AdId .value / Ordering / JsonFormat") {
    val id = domain.before.AdId(42L)
    assertEquals(id.value, 42L)
    val ids = List(domain.before.AdId(3L), domain.before.AdId(1L), domain.before.AdId(2L))
    assertEquals(ids.sorted.map(_.value), List(1L, 2L, 3L))
    val json = id.toJson
    assertEquals(json, JsNumber(42L))
    assertEquals(json.convertTo[BeforeAdId], id)
  }

  test("Before: ImpressionTimestamp .value / Ordering / JsonFormat") {
    val ts = domain.before.ImpressionTimestamp(1700000000L)
    assertEquals(ts.value, 1700000000L)
    val tss = List(domain.before.ImpressionTimestamp(300L), domain.before.ImpressionTimestamp(100L), domain.before.ImpressionTimestamp(200L))
    assertEquals(tss.sorted.map(_.value), List(100L, 200L, 300L))
    val json = ts.toJson
    assertEquals(json, JsNumber(1700000000L))
    assertEquals(json.convertTo[BeforeImpressionTimestamp], ts)
  }

  // — BigDecimal-based types —

  test("Before: BudgetAmount .value / Ordering / JsonFormat") {
    val b = domain.before.BudgetAmount(BigDecimal("99.95"))
    assertEquals(b.value, BigDecimal("99.95"))
    val bs = List(domain.before.BudgetAmount(BigDecimal("3.50")), domain.before.BudgetAmount(BigDecimal("1.25")), domain.before.BudgetAmount(BigDecimal("2.75")))
    assertEquals(bs.sorted.map(_.value), List(BigDecimal("1.25"), BigDecimal("2.75"), BigDecimal("3.50")))
    val json = b.toJson
    assertEquals(json, JsNumber(BigDecimal("99.95")))
    assertEquals(json.convertTo[BeforeBudgetAmount], b)
  }

  test("Before: BidPrice .value / Ordering / JsonFormat") {
    val b = domain.before.BidPrice(BigDecimal("2.50"))
    assertEquals(b.value, BigDecimal("2.50"))
    val bs = List(domain.before.BidPrice(BigDecimal("3.50")), domain.before.BidPrice(BigDecimal("1.25")), domain.before.BidPrice(BigDecimal("2.75")))
    assertEquals(bs.sorted.map(_.value), List(BigDecimal("1.25"), BigDecimal("2.75"), BigDecimal("3.50")))
    val json = b.toJson
    assertEquals(json, JsNumber(BigDecimal("2.50")))
    assertEquals(json.convertTo[BeforeBidPrice], b)
  }

  // — Validated type —

  test("Before: ValidatedEmail .value / Ordering / JsonFormat with validation") {
    val Right(e) = domain.before.ValidatedEmail("alice@example.com"): @unchecked
    assertEquals(e.value, "alice@example.com")
    val Right(e1) = domain.before.ValidatedEmail("c@x"): @unchecked
    val Right(e2) = domain.before.ValidatedEmail("a@x"): @unchecked
    val Right(e3) = domain.before.ValidatedEmail("b@x"): @unchecked
    assertEquals(List(e1, e2, e3).sorted.map(_.value), List("a@x", "b@x", "c@x"))
    val json = e.toJson
    assertEquals(json, JsString("alice@example.com"))
    assertEquals(json.convertTo[BeforeValidatedEmail], e)
    assert(domain.before.ValidatedEmail("not-an-email").isLeft)
    intercept[DeserializationException] { JsString("not-an-email").convertTo[BeforeValidatedEmail] }
  }

  // ── "After": just =:= evidence — Ordering, JsonFormat derived automatically ──

  import domain.after.UserId.UserId as AfterUserId
  import domain.after.OrderId.OrderId as AfterOrderId
  import domain.after.Email.Email as AfterEmail
  import domain.after.SKU.SKU as AfterSKU
  import domain.after.CampaignId.CampaignId as AfterCampaignId
  import domain.after.CampaignSlug.CampaignSlug as AfterCampaignSlug
  import domain.after.CreativeId.CreativeId as AfterCreativeId
  import domain.after.Timestamp.Timestamp as AfterTimestamp
  import domain.after.AdId.AdId as AfterAdId
  import domain.after.ImpressionTimestamp.ImpressionTimestamp as AfterImpressionTimestamp
  import domain.after.BudgetAmount.BudgetAmount as AfterBudgetAmount
  import domain.after.BidPrice.BidPrice as AfterBidPrice
  import domain.after.ValidatedEmail.ValidatedEmail as AfterValidatedEmail

  // — String-based types —

  test("After: UserId codec / Ordering / JsonFormat derive automatically") {
    val id = domain.after.UserId("user-42")
    val codec = summon[OpaqueCodec[AfterUserId, String]]
    assertEquals(codec.encode(id), "user-42")
    assertEquals(codec.decode("user-42"), id)
    val ids = List(domain.after.UserId("charlie"), domain.after.UserId("alice"), domain.after.UserId("bob"))
    assertEquals(ids.sorted.map(codec.encode), List("alice", "bob", "charlie"))
    val json = id.toJson
    assertEquals(json, JsString("user-42"))
    assertEquals(json.convertTo[AfterUserId], id)
  }

  test("After: OrderId codec / Ordering / JsonFormat derive automatically") {
    val id = domain.after.OrderId("order-99")
    val codec = summon[OpaqueCodec[AfterOrderId, String]]
    assertEquals(codec.encode(id), "order-99")
    val ids = List(domain.after.OrderId("c"), domain.after.OrderId("a"), domain.after.OrderId("b"))
    assertEquals(ids.sorted.map(codec.encode), List("a", "b", "c"))
    val json = id.toJson
    assertEquals(json, JsString("order-99"))
    assertEquals(json.convertTo[AfterOrderId], id)
  }

  test("After: Email codec / Ordering / JsonFormat derive automatically") {
    val e = domain.after.Email("alice@example.com")
    val codec = summon[OpaqueCodec[AfterEmail, String]]
    assertEquals(codec.encode(e), "alice@example.com")
    val es = List(domain.after.Email("c@x"), domain.after.Email("a@x"), domain.after.Email("b@x"))
    assertEquals(es.sorted.map(codec.encode), List("a@x", "b@x", "c@x"))
    val json = e.toJson
    assertEquals(json, JsString("alice@example.com"))
    assertEquals(json.convertTo[AfterEmail], e)
  }

  test("After: SKU codec / Ordering / JsonFormat derive automatically") {
    val s = domain.after.SKU("SKU-1234")
    val codec = summon[OpaqueCodec[AfterSKU, String]]
    assertEquals(codec.encode(s), "SKU-1234")
    val ss = List(domain.after.SKU("C"), domain.after.SKU("A"), domain.after.SKU("B"))
    assertEquals(ss.sorted.map(codec.encode), List("A", "B", "C"))
    val json = s.toJson
    assertEquals(json, JsString("SKU-1234"))
    assertEquals(json.convertTo[AfterSKU], s)
  }

  test("After: CampaignId codec / Ordering / JsonFormat derive automatically") {
    val id = domain.after.CampaignId("camp-42")
    val codec = summon[OpaqueCodec[AfterCampaignId, String]]
    assertEquals(codec.encode(id), "camp-42")
    val ids = List(domain.after.CampaignId("c"), domain.after.CampaignId("a"), domain.after.CampaignId("b"))
    assertEquals(ids.sorted.map(codec.encode), List("a", "b", "c"))
    val json = id.toJson
    assertEquals(json, JsString("camp-42"))
    assertEquals(json.convertTo[AfterCampaignId], id)
  }

  test("After: CampaignSlug codec / Ordering / JsonFormat derive automatically") {
    val s = domain.after.CampaignSlug("winter-sale")
    val codec = summon[OpaqueCodec[AfterCampaignSlug, String]]
    assertEquals(codec.encode(s), "winter-sale")
    val ss = List(domain.after.CampaignSlug("winter"), domain.after.CampaignSlug("autumn"), domain.after.CampaignSlug("spring"))
    assertEquals(ss.sorted.map(codec.encode), List("autumn", "spring", "winter"))
    val json = s.toJson
    assertEquals(json, JsString("winter-sale"))
    assertEquals(json.convertTo[AfterCampaignSlug], s)
  }

  test("After: CreativeId codec / Ordering / JsonFormat derive automatically") {
    val id = domain.after.CreativeId("cr-7")
    val codec = summon[OpaqueCodec[AfterCreativeId, String]]
    assertEquals(codec.encode(id), "cr-7")
    val ids = List(domain.after.CreativeId("c"), domain.after.CreativeId("a"), domain.after.CreativeId("b"))
    assertEquals(ids.sorted.map(codec.encode), List("a", "b", "c"))
    val json = id.toJson
    assertEquals(json, JsString("cr-7"))
    assertEquals(json.convertTo[AfterCreativeId], id)
  }

  // — Long-based types —

  test("After: Timestamp codec / Ordering / JsonFormat derive automatically") {
    val ts = domain.after.Timestamp(1700000000L)
    val codec = summon[OpaqueCodec[AfterTimestamp, Long]]
    assertEquals(codec.encode(ts), 1700000000L)
    val tss = List(domain.after.Timestamp(300L), domain.after.Timestamp(100L), domain.after.Timestamp(200L))
    assertEquals(tss.sorted.map(codec.encode), List(100L, 200L, 300L))
    val json = ts.toJson
    assertEquals(json, JsNumber(1700000000L))
    assertEquals(json.convertTo[AfterTimestamp], ts)
  }

  test("After: AdId codec / Ordering / JsonFormat derive automatically") {
    val id = domain.after.AdId(42L)
    val codec = summon[OpaqueCodec[AfterAdId, Long]]
    assertEquals(codec.encode(id), 42L)
    val ids = List(domain.after.AdId(3L), domain.after.AdId(1L), domain.after.AdId(2L))
    assertEquals(ids.sorted.map(codec.encode), List(1L, 2L, 3L))
    val json = id.toJson
    assertEquals(json, JsNumber(42L))
    assertEquals(json.convertTo[AfterAdId], id)
  }

  test("After: ImpressionTimestamp codec / Ordering / JsonFormat derive automatically") {
    val ts = domain.after.ImpressionTimestamp(1700000000L)
    val codec = summon[OpaqueCodec[AfterImpressionTimestamp, Long]]
    assertEquals(codec.encode(ts), 1700000000L)
    val tss = List(domain.after.ImpressionTimestamp(300L), domain.after.ImpressionTimestamp(100L), domain.after.ImpressionTimestamp(200L))
    assertEquals(tss.sorted.map(codec.encode), List(100L, 200L, 300L))
    val json = ts.toJson
    assertEquals(json, JsNumber(1700000000L))
    assertEquals(json.convertTo[AfterImpressionTimestamp], ts)
  }

  // — BigDecimal-based types —

  test("After: BudgetAmount codec / Ordering / JsonFormat derive automatically") {
    val b = domain.after.BudgetAmount(BigDecimal("99.95"))
    val codec = summon[OpaqueCodec[AfterBudgetAmount, BigDecimal]]
    assertEquals(codec.encode(b), BigDecimal("99.95"))
    val bs = List(domain.after.BudgetAmount(BigDecimal("3.50")), domain.after.BudgetAmount(BigDecimal("1.25")), domain.after.BudgetAmount(BigDecimal("2.75")))
    assertEquals(bs.sorted.map(codec.encode), List(BigDecimal("1.25"), BigDecimal("2.75"), BigDecimal("3.50")))
    val json = b.toJson
    assertEquals(json, JsNumber(BigDecimal("99.95")))
    assertEquals(json.convertTo[AfterBudgetAmount], b)
  }

  test("After: BidPrice codec / Ordering / JsonFormat derive automatically") {
    val b = domain.after.BidPrice(BigDecimal("2.50"))
    val codec = summon[OpaqueCodec[AfterBidPrice, BigDecimal]]
    assertEquals(codec.encode(b), BigDecimal("2.50"))
    val bs = List(domain.after.BidPrice(BigDecimal("3.50")), domain.after.BidPrice(BigDecimal("1.25")), domain.after.BidPrice(BigDecimal("2.75")))
    assertEquals(bs.sorted.map(codec.encode), List(BigDecimal("1.25"), BigDecimal("2.75"), BigDecimal("3.50")))
    val json = b.toJson
    assertEquals(json, JsNumber(BigDecimal("2.50")))
    assertEquals(json.convertTo[AfterBidPrice], b)
  }

  // — Validated type —

  test("After: ValidatedEmail JsonFormat derives with validation via StringDecoder") {
    val Right(e) = domain.after.ValidatedEmail("alice@example.com"): @unchecked
    val json = e.toJson
    assertEquals(json, JsString("alice@example.com"))
    assertEquals(json.convertTo[AfterValidatedEmail], e)
    assert(domain.after.ValidatedEmail("not-an-email").isLeft)
    intercept[DeserializationException] { JsString("not-an-email").convertTo[AfterValidatedEmail] }
  }
}
