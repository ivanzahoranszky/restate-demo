package ivan.transaction.service

import ivan.dto.AssetType
import ivan.dto.rest.FeeRequest
import java.math.BigDecimal

class FiatRateCalculationPolicy(private val rate: BigDecimal): RateCalculationPolicy {

    override fun supports(feeRequest: FeeRequest): Boolean = feeRequest.assetType == AssetType.FIAT

    override fun rate(feeRequest: FeeRequest): BigDecimal = rate

}