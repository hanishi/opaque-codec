package domain

object BudgetAmount {
  opaque type BudgetAmount = BigDecimal
  def apply(value: BigDecimal): BudgetAmount = value
  given BudgetAmount =:= BigDecimal = summon
}
