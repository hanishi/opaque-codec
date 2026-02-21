package benchmark

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit
import codec.OpaqueCodec
import domain.UserId.UserId
import domain.OrderId.OrderId

/**
 * Measures whether opaque types add any overhead to codec operations.
 *
 * Strategy: compare two different opaque types (UserId and OrderId),
 * both derived through the exact same =:= + fromConversion mechanism.
 * If they have identical throughput, the opaque type itself adds
 * zero cost — any absolute overhead comes from the codec pattern
 * (Function1 dispatch), not from the opaque type layer.
 *
 * Also includes a raw String baseline (no codec, no type wrapping)
 * to show the absolute cost of the codec dispatch.
 */
@BenchmarkMode(Array(Mode.Throughput))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
class CodecBenchmark {

  private val rawString: String = "user-42"
  private val userId: UserId = domain.UserId("user-42")
  private val orderId: OrderId = domain.OrderId("order-99")

  private val userIdCodec = summon[OpaqueCodec[UserId, String]]
  private val orderIdCodec = summon[OpaqueCodec[OrderId, String]]

  // Raw string return — no codec, no virtual dispatch.
  // Shows the absolute lower bound.
  @Benchmark
  def raw_identity(): String = rawString

  // Two opaque types, same derivation mechanism.
  // Any difference = opaque type overhead (should be none).
  @Benchmark
  def opaque_encodeUserId(): String =
    userIdCodec.encode(userId)

  @Benchmark
  def opaque_encodeOrderId(): String =
    orderIdCodec.encode(orderId)

  @Benchmark
  def opaque_decodeUserId(): UserId =
    userIdCodec.decode(rawString)

  @Benchmark
  def opaque_decodeOrderId(): OrderId =
    orderIdCodec.decode(rawString)

  @Benchmark
  def opaque_roundtripUserId(): UserId =
    userIdCodec.decode(userIdCodec.encode(userId))

  @Benchmark
  def opaque_roundtripOrderId(): OrderId =
    orderIdCodec.decode(orderIdCodec.encode(orderId))
}
