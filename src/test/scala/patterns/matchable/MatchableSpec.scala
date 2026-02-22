package patterns.matchable

import patterns.matchable.Street.Street
import patterns.matchable.City.City

class MatchableSpec extends munit.FunSuite {

  test("opaque types are erased at runtime: isInstanceOf[String] returns true") {
    // Pitfall: opaque types are erased, so runtime checks see the underlying type
    val street: Street = Street("123 Main St")
    val isString = street.isInstanceOf[String]
    assert(isString, "Street is erased to String at runtime")
  }

  test("Street and City are both String at runtime") {
    val street: Street = Street("123 Main St")
    val city: City = City("Springfield")
    // Both are String at runtime — the type distinction exists only at compile time
    assert(street.isInstanceOf[String])
    assert(city.isInstanceOf[String])
  }

  test("pattern matching pierces opaque types") {
    // Pitfall: match on the underlying type succeeds because opaque types are erased
    val street: Street = Street("123 Main St")
    val result = (street: Any) match {
      case s: String => s"matched as String: $s"
      case _         => "not matched"
    }
    assertEquals(result, "matched as String: 123 Main St")
  }

  test("type-based dispatch cannot distinguish Street from City at runtime") {
    // Pitfall: since both are String at runtime, runtime dispatch treats them identically
    val street: Street = Street("123 Main St")
    val city: City = City("Springfield")

    def describe(x: Any): String = x match {
      case _: String => "it's a String"
      case _         => "unknown"
    }

    // Both match as String — no way to distinguish at runtime
    assertEquals(describe(street), "it's a String")
    assertEquals(describe(city), "it's a String")
  }

  test("ClassTag / TypeTest cannot distinguish opaque types from their underlying type") {
    // Opaque types do not carry runtime tags
    import scala.reflect.ClassTag
    val streetTag = summon[ClassTag[Street]]
    val stringTag = summon[ClassTag[String]]
    // Both have the same runtime class
    assertEquals(streetTag.runtimeClass, stringTag.runtimeClass)
  }

  test("asInstanceOf can cast between opaque types sharing the same underlying type") {
    // Pitfall: unsafe cast succeeds because both are String at runtime
    val street: Street = Street("123 Main St")
    val city: City = street.asInstanceOf[City]
    assertEquals(city.value, "123 Main St")
  }

  test("compile-time safety: == between Street and City requires explicit conversion") {
    // At compile time with strictEquality (import scala.language.strictEquality),
    // Street == City would not compile without a given CanEqual.
    // Without strictEquality, Scala's universal equality allows it,
    // but the comparison is meaningless because the types are conceptually different.
    val street: Street = Street("same")
    val city: City = City("same")
    // This compiles under universal equality and returns true (both are "same" at runtime)
    // With strictEquality enabled, this would require: given CanEqual[Street, City] = CanEqual.derived
    assert(street == city) // true at runtime — a pitfall of universal equality
  }
}
