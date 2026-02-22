package patterns.ulid

import java.security.SecureRandom
import java.util.concurrent.atomic.AtomicReference

/**
  * Minimal ULID (Universally Unique Lexicographically Sortable Identifier) generator.
  *
  * A ULID is a 128-bit value: 48-bit Unix timestamp (ms) + 80-bit randomness,
  * encoded as a 26-character Crockford Base32 string. The string representation
  * sorts lexicographically in chronological order.
  *
  * Within the same millisecond, the random part is atomically incremented
  * to guarantee monotonicity on a single machine.
  */
object ULID {

  private val EncodingChars: Array[Char] = Array(
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
    'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X',
    'Y', 'Z'
  )

  private val random = new SecureRandom
  private val lastValue = new AtomicReference((0L, 0L))
  private val baseSystemTimeMillis = System.currentTimeMillis()
  private val baseNanoTime = System.nanoTime()

  private def currentTimeMillis: Long =
    baseSystemTimeMillis + java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(
      System.nanoTime() - baseNanoTime
    )

  /** Generate a new ULID string (26 characters, Crockford Base32). */
  def newULIDString: String = synchronized {
    val now = currentTimeMillis
    val (prevHi, prevLow) = lastValue.get()
    val prevTimestamp = (prevHi >>> 16) & 0xffffffffffffL

    if (prevTimestamp == now) {
      // Same millisecond: increment the random part
      if (prevLow != ~0L) {
        generate(prevHi, prevLow + 1L)
      } else {
        val nextHi = (prevHi & 0xffffL) + 1
        if ((nextHi & (~0L << 16)) != 0) {
          // Overflow — wait 1ms and retry
          Thread.sleep(1)
          newULIDString
        } else {
          generate(now << 16 | nextHi, 0L)
        }
      }
    } else {
      // New millisecond: fresh random value
      val rand = new Array[Byte](10)
      random.nextBytes(rand)
      val hi = (now << 16) |
        ((rand(0) & 0xffL) << 8) | (rand(1) & 0xffL)
      val low =
        ((rand(2) & 0xffL) << 56) | ((rand(3) & 0xffL) << 48) |
        ((rand(4) & 0xffL) << 40) | ((rand(5) & 0xffL) << 32) |
        ((rand(6) & 0xffL) << 24) | ((rand(7) & 0xffL) << 16) |
        ((rand(8) & 0xffL) << 8)  |  (rand(9) & 0xffL)
      generate(hi, low)
    }
  }

  private def generate(hi: Long, low: Long): String = {
    lastValue.set((hi, low))
    encode128(hi, low)
  }

  private def encode128(hi: Long, low: Long): String = {
    val buf = new Array[Char](26)
    var h = hi
    var l = low
    var i = 25
    while (i >= 0) {
      buf(i) = EncodingChars((l & 0x1fL).toInt)
      val carry = (h & 0x1fL) << (64 - 5)
      l >>>= 5
      l |= carry
      h >>>= 5
      i -= 1
    }
    new String(buf)
  }
}
