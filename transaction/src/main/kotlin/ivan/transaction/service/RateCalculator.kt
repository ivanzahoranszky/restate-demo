package ivan.transaction.service

import ivan.dto.rest.FeeRequest
import java.math.BigDecimal

class RateCalculator(private val rateCalculationPolicies: List<RateCalculationPolicy>) {

    fun calculate(feeRequest: FeeRequest): BigDecimal {
        val rateCalculationPolicy: RateCalculationPolicy = rateCalculationPolicies.firstOrNull { it.supports(feeRequest) }
            ?: DefaultRateCalculationPolicy(BigDecimal("0.1"))
        return rateCalculationPolicy.rate(feeRequest)
    }

}