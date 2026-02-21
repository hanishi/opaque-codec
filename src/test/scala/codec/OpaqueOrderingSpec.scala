package codec

import codec.OpaqueOrdering.given
import domain.UserId.UserId
import domain.OrderId.OrderId
import domain.Timestamp.Timestamp
import domain.BidPrice.BidPrice
import domain.CampaignSlug.CampaignSlug

class OpaqueOrderingSpec extends munit.FunSuite {

  test("Ordering[UserId] auto-derives and sorts correctly") {
    val ids = List(domain.UserId("charlie"), domain.UserId("alice"), domain.UserId("bob"))
    val sorted = ids.sorted
    assertEquals(sorted.map(summon[OpaqueCodec[UserId, String]].encode), List("alice", "bob", "charlie"))
  }

  test("Ordering[CampaignSlug] auto-derives and sorts correctly") {
    val slugs = List(domain.CampaignSlug("winter"), domain.CampaignSlug("autumn"), domain.CampaignSlug("spring"))
    val sorted = slugs.sorted
    assertEquals(sorted.map(summon[OpaqueCodec[CampaignSlug, String]].encode), List("autumn", "spring", "winter"))
  }

  test("Ordering[Timestamp] auto-derives and sorts correctly") {
    val timestamps = List(domain.Timestamp(300L), domain.Timestamp(100L), domain.Timestamp(200L))
    val sorted = timestamps.sorted
    assertEquals(sorted.map(summon[OpaqueCodec[Timestamp, Long]].encode), List(100L, 200L, 300L))
  }

  test("Ordering[BidPrice] auto-derives and sorts correctly") {
    val prices = List(domain.BidPrice(BigDecimal("3.50")), domain.BidPrice(BigDecimal("1.25")), domain.BidPrice(BigDecimal("2.75")))
    val sorted = prices.sorted
    assertEquals(sorted.map(summon[OpaqueCodec[BidPrice, BigDecimal]].encode), List(BigDecimal("1.25"), BigDecimal("2.75"), BigDecimal("3.50")))
  }

  test("Ordering[UserId] supports min and max") {
    val a = domain.UserId("alice")
    val b = domain.UserId("bob")
    val ord = summon[Ordering[UserId]]
    assertEquals(ord.min(a, b), a)
    assertEquals(ord.max(a, b), b)
  }
}
