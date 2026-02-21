package codec

import codec.OpaqueOrdering.given
import domain.after.UserId.UserId
import domain.after.OrderId.OrderId
import domain.after.Timestamp.Timestamp
import domain.after.BidPrice.BidPrice
import domain.after.CampaignSlug.CampaignSlug

class OpaqueOrderingSpec extends munit.FunSuite {

  test("Ordering[UserId] auto-derives and sorts correctly") {
    val ids = List(domain.after.UserId("charlie"), domain.after.UserId("alice"), domain.after.UserId("bob"))
    val sorted = ids.sorted
    assertEquals(sorted.map(summon[OpaqueCodec[UserId, String]].encode), List("alice", "bob", "charlie"))
  }

  test("Ordering[CampaignSlug] auto-derives and sorts correctly") {
    val slugs = List(domain.after.CampaignSlug("winter"), domain.after.CampaignSlug("autumn"), domain.after.CampaignSlug("spring"))
    val sorted = slugs.sorted
    assertEquals(sorted.map(summon[OpaqueCodec[CampaignSlug, String]].encode), List("autumn", "spring", "winter"))
  }

  test("Ordering[Timestamp] auto-derives and sorts correctly") {
    val timestamps = List(domain.after.Timestamp(300L), domain.after.Timestamp(100L), domain.after.Timestamp(200L))
    val sorted = timestamps.sorted
    assertEquals(sorted.map(summon[OpaqueCodec[Timestamp, Long]].encode), List(100L, 200L, 300L))
  }

  test("Ordering[BidPrice] auto-derives and sorts correctly") {
    val prices = List(domain.after.BidPrice(BigDecimal("3.50")), domain.after.BidPrice(BigDecimal("1.25")), domain.after.BidPrice(BigDecimal("2.75")))
    val sorted = prices.sorted
    assertEquals(sorted.map(summon[OpaqueCodec[BidPrice, BigDecimal]].encode), List(BigDecimal("1.25"), BigDecimal("2.75"), BigDecimal("3.50")))
  }

  test("Ordering[UserId] supports min and max") {
    val a = domain.after.UserId("alice")
    val b = domain.after.UserId("bob")
    val ord = summon[Ordering[UserId]]
    assertEquals(ord.min(a, b), a)
    assertEquals(ord.max(a, b), b)
  }
}
