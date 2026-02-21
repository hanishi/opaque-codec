package domain.before

import spray.json.*

object BudgetAmount {
  opaque type BudgetAmount = BigDecimal
  def apply(value: BigDecimal): BudgetAmount = value
  extension (b: BudgetAmount) { def value: BigDecimal = b }
  given Ordering[BudgetAmount] = Ordering[BigDecimal].on(_.value)
  given JsonFormat[BudgetAmount] with {
    def write(b: BudgetAmount) = JsNumber(b.value)
    def read(json: JsValue) = json match {
      case JsNumber(n) => BudgetAmount(n)
      case other => deserializationError(s"Expected number, got $other")
    }
  }
}
