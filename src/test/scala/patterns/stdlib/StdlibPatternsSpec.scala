package patterns.stdlib

class StdlibPatternsSpec extends munit.FunSuite {

  // ─── IArray: opaque type IArray[+T] = Array[? <: T] ───

  test("IArray: creation and indexing") {
    val arr: IArray[Int] = IArray(1, 2, 3)
    assertEquals(arr(0), 1)
    assertEquals(arr(1), 2)
    assertEquals(arr(2), 3)
  }

  test("IArray: length") {
    val arr: IArray[String] = IArray("a", "b", "c")
    assertEquals(arr.length, 3)
  }

  test("IArray: map produces new IArray") {
    val arr: IArray[Int] = IArray(1, 2, 3)
    val doubled: IArray[Int] = arr.map(_ * 2)
    assertEquals(doubled.toList, List(2, 4, 6))
  }

  test("IArray: covariance — IArray[String] assignable to IArray[Any]") {
    // IArray[+T] is covariant because opaque type IArray[+T] = Array[? <: T]
    // This is the same trick as our ImmutableBuffer
    val strings: IArray[String] = IArray("hello", "world")
    val anys: IArray[Any] = strings
    assertEquals(anys(0), "hello")
  }

  test("IArray: read-only — no mutation methods exposed") {
    // IArray does not expose update, append, or any mutating operation.
    // The following would NOT compile:
    //   val arr: IArray[Int] = IArray(1, 2, 3)
    //   arr(0) = 99  // error: value update is not a member of IArray[Int]
    //
    // This is because the opaque type boundary hides Array's mutable API,
    // and only read-only extension methods are provided.
    val arr: IArray[Int] = IArray(1, 2, 3)
    assertEquals(arr.toList, List(1, 2, 3))
  }

  test("IArray: filter and flatMap") {
    val arr: IArray[Int] = IArray(1, 2, 3, 4, 5)
    val evens: IArray[Int] = arr.filter(_ % 2 == 0)
    assertEquals(evens.toList, List(2, 4))
  }

  // ─── NamedTuple: opaque type NamedTuple[N <: Tuple, +V <: Tuple] = V ───

  test("NamedTuple: field access via name") {
    val person = (name = "Alice", age = 30)
    assertEquals(person.name, "Alice")
    assertEquals(person.age, 30)
  }

  test("NamedTuple: toTuple strips names") {
    val person = (name = "Alice", age = 30)
    val tuple: (String, Int) = person.toTuple
    assertEquals(tuple._1, "Alice")
    assertEquals(tuple._2, 30)
  }

  test("NamedTuple: names exist only at compile time") {
    // At runtime, a NamedTuple is just a plain Tuple (the V parameter).
    // The names (N parameter) are erased — they guide only compile-time access.
    val person = (name = "Alice", age = 30)
    val tuple = ("Alice", 30)
    // At runtime they are structurally identical
    assertEquals(person.toTuple, tuple)
  }

  test("NamedTuple: pattern matching") {
    val person = (name = "Bob", age = 25)
    val (n, a) = person.toTuple
    assertEquals(n, "Bob")
    assertEquals(a, 25)
  }

  test("NamedTuple: map over values") {
    val pair = (x = 1, y = 2)
    val tuple = pair.toTuple
    val mapped = tuple.map[[X] =>> String]([T] => (t: T) => t.toString)
    assertEquals(mapped, ("1", "2"))
  }
}
