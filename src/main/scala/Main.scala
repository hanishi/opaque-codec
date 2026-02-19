import codec.{StringCodec, LongCodec, StringEncoder, StringDecoder, LongEncoder, LongDecoder}
import codec.SprayJsonSupport.given
import spray.json.*
import domain.UserId.UserId
import domain.OrderId.OrderId
import domain.Email.Email
import domain.SKU.SKU
import domain.Timestamp.Timestamp
import domain.ValidatedEmail.ValidatedEmail

@main
def main(): Unit =
  println("=== Zero-Cost Type Class Derivation Demo ===")
  println()

  // --- StringCodec[String]: the trivial identity codec ---
  val stringCodec = summon[StringCodec[String]]
  println("StringCodec[String]")
  println(s"  encode(\"hello\") = ${stringCodec.encode("hello")}")
  println(s"  decode(\"hello\") = ${stringCodec.decode("hello")}")
  println()

  // --- StringCodec[UserId]: auto-derived via =:= evidence ---
  val userIdCodec = summon[StringCodec[UserId]]
  val uid = domain.UserId("user-42")
  println("StringCodec[UserId]")
  println(s"  encode(UserId(\"user-42\")) = ${userIdCodec.encode(uid)}")
  println(s"  decode(\"user-42\")         = ${userIdCodec.decode("user-42")}")
  println()

  // --- StringCodec[OrderId] ---
  val orderIdCodec = summon[StringCodec[OrderId]]
  val oid = domain.OrderId("order-99")
  println("StringCodec[OrderId]")
  println(s"  encode(OrderId(\"order-99\")) = ${orderIdCodec.encode(oid)}")
  println(s"  decode(\"order-99\")          = ${orderIdCodec.decode("order-99")}")
  println()

  // --- StringCodec[Email] ---
  val emailCodec = summon[StringCodec[Email]]
  val email = domain.Email("alice@example.com")
  println("StringCodec[Email]")
  println(s"  encode(Email(\"alice@example.com\")) = ${emailCodec.encode(email)}")
  println(s"  decode(\"alice@example.com\")         = ${emailCodec.decode("alice@example.com")}")
  println()

  // --- StringCodec[SKU] ---
  val skuCodec = summon[StringCodec[SKU]]
  val sku = domain.SKU("SKU-1234")
  println("StringCodec[SKU]")
  println(s"  encode(SKU(\"SKU-1234\")) = ${skuCodec.encode(sku)}")
  println(s"  decode(\"SKU-1234\")      = ${skuCodec.decode("SKU-1234")}")
  println()

  // --- LongCodec[Long]: the trivial identity codec ---
  val longCodec = summon[LongCodec[Long]]
  println("LongCodec[Long]")
  println(s"  encode(42L) = ${longCodec.encode(42L)}")
  println(s"  decode(42L) = ${longCodec.decode(42L)}")
  println()

  // --- LongCodec[Timestamp]: auto-derived via =:= evidence ---
  val tsCodec = summon[LongCodec[Timestamp]]
  val ts = domain.Timestamp(1700000000L)
  println("LongCodec[Timestamp]")
  println(s"  encode(Timestamp(1700000000L)) = ${tsCodec.encode(ts)}")
  println(s"  decode(1700000000L)            = ${tsCodec.decode(1700000000L)}")
  println()

  // --- Spray JSON integration: JsonFormat auto-derived from codecs ---
  println("=== Spray JSON Integration ===")
  println()

  println("UserId")
  val uidJson = uid.toJson
  println(s"  uid.toJson                = ${uidJson.compactPrint}")
  println(s"  .convertTo[UserId]        = ${uidJson.convertTo[UserId]}")
  println()

  println("OrderId")
  val oidJson = oid.toJson
  println(s"  oid.toJson                = ${oidJson.compactPrint}")
  println(s"  .convertTo[OrderId]       = ${oidJson.convertTo[OrderId]}")
  println()

  println("Email")
  val emailJson = email.toJson
  println(s"  email.toJson              = ${emailJson.compactPrint}")
  println(s"  .convertTo[Email]         = ${emailJson.convertTo[Email]}")
  println()

  println("SKU")
  val skuJson = sku.toJson
  println(s"  sku.toJson                = ${skuJson.compactPrint}")
  println(s"  .convertTo[SKU]           = ${skuJson.convertTo[SKU]}")
  println()

  println("Timestamp")
  val tsJson = ts.toJson
  println(s"  ts.toJson                 = ${tsJson.compactPrint}")
  println(s"  .convertTo[Timestamp]     = ${tsJson.convertTo[Timestamp]}")
  println()

  val parsed = """"user-99"""".parseJson.convertTo[UserId]
  println(s"""  "user-99".parseJson.convertTo[UserId] = $parsed""")
  println()

  println("All codecs and JSON formats resolved and round-tripped successfully!")
  println()

  // --- The <:< approach: ValidatedEmail with upper bound ---
  println("=== Validated Types via <:< (Upper Bound Approach) ===")
  println()

  // ValidatedEmail.apply enforces that the value contains "@"
  println("ValidatedEmail.apply with validation:")
  println(s"  apply(\"alice@example.com\") = ${domain.ValidatedEmail("alice@example.com")}")
  println(s"  apply(\"not-an-email\")      = ${domain.ValidatedEmail("not-an-email")}")
  println()

  // StringEncoder resolves automatically via <:< — no =:= export needed
  val veEncoder = summon[StringEncoder[ValidatedEmail]]
  val Right(ve) = domain.ValidatedEmail("alice@example.com"): @unchecked
  println("StringEncoder[ValidatedEmail] (auto-derived via <:<):")
  println(s"  encode(ve) = ${veEncoder.encode(ve)}")
  println()

  // StringDecoder uses the hand-written instance that enforces validation
  val veDecoder = summon[StringDecoder[ValidatedEmail]]
  println("StringDecoder[ValidatedEmail] (hand-written, validates):")
  println(s"  decode(\"alice@example.com\") = ${veDecoder.decode("alice@example.com")}")
  println(s"  decode(\"not-an-email\")      = ${veDecoder.decode("not-an-email")}")
  println()

  // Spray JSON integration: encoding works, decoding validates
  println("Spray JSON for ValidatedEmail:")
  val veJson = ve.toJson
  println(s"  ve.toJson                        = ${veJson.compactPrint}")
  println(s"  .convertTo[ValidatedEmail]       = ${veJson.convertTo[ValidatedEmail]}")
  print(s"  invalid.convertTo[ValidatedEmail] = ")
  try
    JsString("not-an-email").convertTo[ValidatedEmail]
  catch
    case e: DeserializationException =>
      println(s"DeserializationException: ${e.getMessage}")
  println()

  // The key point: no =:= evidence is exported, so there's no backdoor
  println("No =:= evidence exported — this won't compile:")
  println("  summon[ValidatedEmail =:= String]  // ✗ compile error")
  println("  ev.flip(\"not-an-email\")             // ✗ impossible")
