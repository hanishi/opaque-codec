package benchmark

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit
import codec.OpaqueNumeric
import domain.after.BidPrice
import domain.after.BidPrice.BidPrice

/**
 * Measures whether OpaqueNumeric adds overhead compared to using
 * Numeric[BigDecimal] directly.
 *
 * Since opaque types erase to their underlying type, OpaqueNumeric
 * reuses the same Numeric instance via asInstanceOf — zero overhead.
 */
@BenchmarkMode(Array(Mode.Throughput))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
class NumericBenchmark {

  private val rawA: BigDecimal = BigDecimal("100.50")
  private val rawB: BigDecimal = BigDecimal("42.25")
  private val bidA: BidPrice = BidPrice(rawA)
  private val bidB: BidPrice = BidPrice(rawB)

  private val rawNumeric: Numeric[BigDecimal] = summon[Numeric[BigDecimal]]

  import OpaqueNumeric.given
  private val opaqueNumeric: Numeric[BidPrice] = summon[Numeric[BidPrice]]

  // --- plus ---

  @Benchmark
  def raw_plus(): BigDecimal =
    rawNumeric.plus(rawA, rawB)

  @Benchmark
  def opaque_plus(): BidPrice =
    opaqueNumeric.plus(bidA, bidB)

  // --- minus ---

  @Benchmark
  def raw_minus(): BigDecimal =
    rawNumeric.minus(rawA, rawB)

  @Benchmark
  def opaque_minus(): BidPrice =
    opaqueNumeric.minus(bidA, bidB)

  // --- times ---

  @Benchmark
  def raw_times(): BigDecimal =
    rawNumeric.times(rawA, rawB)

  @Benchmark
  def opaque_times(): BidPrice =
    opaqueNumeric.times(bidA, bidB)

  // --- compare ---

  @Benchmark
  def raw_compare(): Int =
    rawNumeric.compare(rawA, rawB)

  @Benchmark
  def opaque_compare(): Int =
    opaqueNumeric.compare(bidA, bidB)

  // --- sum (aggregate) ---

  private val rawList: List[BigDecimal] =
    List.tabulate(100)(i => BigDecimal(i))
  private val opaqueList: List[BidPrice] =
    rawList.map(BidPrice(_))

  @Benchmark
  def raw_sum(): BigDecimal =
    rawList.sum(using rawNumeric)

  @Benchmark
  def opaque_sum(): BidPrice =
    opaqueList.sum(using opaqueNumeric)
}
