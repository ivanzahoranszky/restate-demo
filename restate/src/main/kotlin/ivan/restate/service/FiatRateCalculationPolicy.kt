package ivan.restate.service

import ivan.dto.FeeRequest
import java.math.BigDecimal

class FiatRateCalculationPolicy(private val rate: BigDecimal): RateCalculationPolicy {

    override fun supports(feeRequest: FeeRequest): Boolean = feeRequest.asset == "FIAT"

    override fun rate(feeRequest: FeeRequest): BigDecimal = rate

}