package codec

import codec.OpaqueOrdering.given
import domain.after.TraceId
import domain.after.TraceId.TraceId

class UlidOrderingSpec extends munit.FunSuite {

  test("Ordering[TraceId] sorts ULID-based types chronologically") {
    // Generate TraceIds in order — ULIDs are monotonically increasing strings
    val first = TraceId.newTraceId
    val second = TraceId.newTraceId
    val third = TraceId.newTraceId

    // Deliberately shuffle and sort
    val shuffled = List(third, first, second)
    val sorted = shuffled.sorted

    assertEquals(sorted, List(first, second, third))
  }

  test("Ordering[TraceId] supports min and max") {
    val a = TraceId.newTraceId
    val b = TraceId.newTraceId
    val ord = summon[Ordering[TraceId]]
    assertEquals(ord.min(a, b), a)
    assertEquals(ord.max(a, b), b)
  }

  test("TraceId preserves ULID monotonicity across batch generation") {
    val ids = (1 to 100).map(_ => TraceId.newTraceId).toList
    val sorted = ids.sorted
    assertEquals(sorted, ids)
  }
}
