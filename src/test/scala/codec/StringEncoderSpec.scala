package codec

import domain.UserId.UserId
import domain.Email.Email
import domain.ValidatedEmail.ValidatedEmail

class StringEncoderSpec extends munit.FunSuite:

  test("StringEncoder[String] resolves and encodes") {
    val encoder = summon[StringEncoder[String]]
    assertEquals(encoder.encode("hello"), "hello")
  }

  test("StringEncoder[UserId] auto-derives via =:= and encodes") {
    val encoder = summon[StringEncoder[UserId]]
    val uid = domain.UserId("user-42")
    assertEquals(encoder.encode(uid), "user-42")
  }

  test("StringEncoder[Email] auto-derives via =:= and encodes") {
    val encoder = summon[StringEncoder[Email]]
    val email = domain.Email("alice@example.com")
    assertEquals(encoder.encode(email), "alice@example.com")
  }

  test("StringEncoder[ValidatedEmail] auto-derives via <:< from upper bound") {
    val encoder = summon[StringEncoder[ValidatedEmail]]
    val Right(ve) = domain.ValidatedEmail("alice@example.com"): @unchecked
    assertEquals(encoder.encode(ve), "alice@example.com")
  }
