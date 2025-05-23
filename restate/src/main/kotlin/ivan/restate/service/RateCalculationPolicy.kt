package ivan.restate.service

import ivan.dto.FeeRequest
import java.math.BigDecimal

interface RateCalculationPolicy {

    fun supports(feeRequest: FeeRequest): Boolean

    fun rate(feeRequest: FeeRequest): BigDecimal

}