/** Domain types drawn from a programmatic ad platform built with Pekko Cluster.
  *
  * The production system manages campaigns, creatives, budgets, and real-time
  * ad serving across a cluster of nodes. It defines dozens of opaque types —
  * advertiser IDs, campaign IDs, creative IDs, site IDs, CPMs, budgets, spend
  * tracking — all of which need serialization for JSON APIs (Spray JSON) and
  * cluster communication (Pekko CBOR serialization). In the real platform,
  * all identifiers are ULIDs (Universally Unique Lexicographically Sortable
  * Identifiers) represented as strings.
  *
  * Without the `OpaqueCodec` pattern, each opaque type requires a hand-written
  * `JsonFormat` in its companion object — identical logic repeated for every
  * type. With dozens of opaque types this becomes a maintenance burden and a
  * source of copy-paste errors.
  *
  * The types here are a representative sample, split across different underlying
  * representations to demonstrate that the same derivation works uniformly:
  *
  *   - '''String-based''' — `CampaignSlug`, `CreativeId`: identifiers used
  *     in URLs, API responses, and event tracking.
  *
  *   - '''Long-based''' — `AdId`, `ImpressionTimestamp`: numeric identifiers
  *     and epoch-millis timestamps for high-throughput event processing.
  *
  *   - '''BigDecimal-based''' — `BudgetAmount`, `BidPrice`: monetary values
  *     (CPMs, daily budgets) where floating-point rounding is unacceptable.
  *
  * Each type exports `=:=` evidence so that `OpaqueCodec` and `OpaqueJsonSupport`
  * derive codec and `JsonFormat` instances automatically — eliminating the
  * repetitive boilerplate that the production codebase currently carries.
  */
package object domain
