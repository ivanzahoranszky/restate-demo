package ivan.transaction.service

import ivan.dto.rest.FeeRequest
import java.math.BigDecimal

class DefaultRateCalculationPolicy(private val rate: BigDecimal): RateCalculationPolicy {

    override fun supports(feeRequest: FeeRequest): Boolean = true

    override fun rate(feeRequest: FeeRequest): BigDecimal = rate

}