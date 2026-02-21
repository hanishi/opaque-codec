package codec

import domain.after.UserId.UserId
import domain.after.Email.Email
import domain.after.ValidatedEmail.ValidatedEmail

class StringEncoderSpec extends munit.FunSuite {

  test("StringEncoder[String] resolves and encodes") {
    val encoder = summon[StringEncoder[String]]
    assertEquals(encoder.encode("hello"), "hello")
  }

  test("StringEncoder[UserId] auto-derives via =:= and encodes") {
    val encoder = summon[StringEncoder[UserId]]
    val uid = domain.after.UserId("user-42")
    assertEquals(encoder.encode(uid), "user-42")
  }

  test("StringEncoder[Email] auto-derives via =:= and encodes") {
    val encoder = summon[StringEncoder[Email]]
    val email = domain.after.Email("alice@example.com")
    assertEquals(encoder.encode(email), "alice@example.com")
  }

  test("StringEncoder[ValidatedEmail] auto-derives via <:< from upper bound") {
    val encoder = summon[StringEncoder[ValidatedEmail]]
    val Right(ve) = domain.after.ValidatedEmail("alice@example.com"): @unchecked
    assertEquals(encoder.encode(ve), "alice@example.com")
  }
}
