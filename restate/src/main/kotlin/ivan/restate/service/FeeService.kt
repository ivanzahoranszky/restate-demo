package ivan.restate.service

import dev.restate.sdk.annotation.Handler
import dev.restate.sdk.annotation.Service
import dev.restate.sdk.kotlin.Context
import ivan.dto.FeeRequest
import ivan.dto.FeeRespone
import ivan.restate.dao.FeeRepository

@Service
class FeeService(private val feeRepository: FeeRepository, private val rateCalculator: RateCalculator) {

    @Handler
    fun fee(ctx: Context, feeRequest: FeeRequest): FeeRespone {
        val rate = rateCalculator.calculate(feeRequest)
        val fee = feeRequest.amount * rate

        // we don't need idempotency key since it runs as a transaction
        feeRepository.save(feeRequest)

        return FeeRespone(transactionId = feeRequest.transactionId, fee = fee, rate = rate)
    }

}