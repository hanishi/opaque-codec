package codec

import domain.UserId.UserId
import domain.ValidatedEmail.ValidatedEmail

class StringDecoderSpec extends munit.FunSuite {

  test("StringDecoder[String] resolves and decodes") {
    val decoder = summon[StringDecoder[String]]
    assertEquals(decoder.decode("hello"), Right("hello"))
  }

  test("StringDecoder[UserId] auto-derives via =:= and decodes") {
    val decoder = summon[StringDecoder[UserId]]
    val expected = domain.UserId("user-42")
    assertEquals(decoder.decode("user-42"), Right(expected))
  }

  test("StringDecoder[ValidatedEmail] decodes valid input through validation") {
    val decoder = summon[StringDecoder[ValidatedEmail]]
    val Right(ve) = domain.ValidatedEmail("alice@example.com"): @unchecked
    assertEquals(decoder.decode("alice@example.com"), Right(ve))
  }

  test("StringDecoder[ValidatedEmail] rejects invalid input") {
    val decoder = summon[StringDecoder[ValidatedEmail]]
    assert(decoder.decode("not-an-email").isLeft)
  }
}
