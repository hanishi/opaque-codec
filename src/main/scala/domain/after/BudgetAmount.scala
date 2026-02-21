package domain.after

import codec.OpaqueCodec

object BudgetAmount {
  opaque type BudgetAmount = BigDecimal
  def apply(value: BigDecimal): BudgetAmount = value
  given OpaqueCodec[BudgetAmount, BigDecimal] = OpaqueCodec.fromEvidence
}
