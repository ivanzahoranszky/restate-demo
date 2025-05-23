package ivan.transaction.service

import ivan.dto.rest.FeeRequest
import java.math.BigDecimal

class FiatRateCalculationPolicy(private val rate: BigDecimal): RateCalculationPolicy {

    override fun supports(feeRequest: FeeRequest): Boolean = feeRequest.asset == "FIAT"

    override fun rate(feeRequest: FeeRequest): BigDecimal = rate

}