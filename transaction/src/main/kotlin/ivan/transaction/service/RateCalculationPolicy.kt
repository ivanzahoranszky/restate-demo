package ivan.transaction.service

import ivan.dto.rest.FeeRequest
import java.math.BigDecimal

interface RateCalculationPolicy {

    fun supports(feeRequest: FeeRequest): Boolean

    fun rate(feeRequest: FeeRequest): BigDecimal

}