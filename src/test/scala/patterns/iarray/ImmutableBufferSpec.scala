package patterns.iarray

import scala.collection.mutable.ArrayBuffer
import patterns.iarray.ImmutableBuffer.ImmutableBuffer

class ImmutableBufferSpec extends munit.FunSuite {

  test("apply creates from varargs and supports indexing") {
    val buf = ImmutableBuffer(10, 20, 30)
    assertEquals(buf(0), 10)
    assertEquals(buf(1), 20)
    assertEquals(buf(2), 30)
  }

  test("size returns element count") {
    val buf = ImmutableBuffer("a", "b", "c")
    assertEquals(buf.size, 3)
  }

  test("isEmpty and nonEmpty") {
    val empty = ImmutableBuffer[Int]()
    val nonEmpty = ImmutableBuffer(1)
    assert(empty.isEmpty)
    assert(nonEmpty.nonEmpty)
  }

  test("head and last") {
    val buf = ImmutableBuffer(1, 2, 3)
    assertEquals(buf.head, 1)
    assertEquals(buf.last, 3)
  }

  test("toList converts to List") {
    val buf = ImmutableBuffer(1, 2, 3)
    assertEquals(buf.toList, List(1, 2, 3))
  }

  test("map transforms elements") {
    val buf = ImmutableBuffer(1, 2, 3)
    val doubled = buf.map(_ * 2)
    assertEquals(doubled.toList, List(2, 4, 6))
  }

  test("filter selects matching elements") {
    val buf = ImmutableBuffer(1, 2, 3, 4, 5)
    val evens = buf.filter(_ % 2 == 0)
    assertEquals(evens.toList, List(2, 4))
  }

  test("foreach iterates over elements") {
    val buf = ImmutableBuffer(1, 2, 3)
    var sum = 0
    buf.foreach(sum += _)
    assertEquals(sum, 6)
  }

  test("covariance: ImmutableBuffer[String] assignable to ImmutableBuffer[Any]") {
    val strings: ImmutableBuffer[String] = ImmutableBuffer("hello", "world")
    val anys: ImmutableBuffer[Any] = strings
    assertEquals(anys(0), "hello")
    assertEquals(anys.size, 2)
  }

  test("unsafeFromArrayBuffer shares backing storage") {
    val backing = ArrayBuffer(1, 2, 3)
    val buf = ImmutableBuffer.unsafeFromArrayBuffer(backing)
    assertEquals(buf.toList, List(1, 2, 3))
    // Mutation through the original ArrayBuffer is visible — this is why it's "unsafe"
    backing(0) = 99
    assertEquals(buf(0), 99)
  }

  test("unsafeFromArrayBuffer: appending to backing changes size") {
    val backing = ArrayBuffer(1, 2)
    val buf = ImmutableBuffer.unsafeFromArrayBuffer(backing)
    assertEquals(buf.size, 2)
    backing += 3
    assertEquals(buf.size, 3)
  }
}
