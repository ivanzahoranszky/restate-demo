package ivan.transaction.service

import dev.restate.sdk.annotation.Handler
import dev.restate.sdk.annotation.Service
import dev.restate.sdk.kotlin.Context
import ivan.dto.rest.FeeRequest
import ivan.dto.rest.FeeRespone
import ivan.dto.toDto
import ivan.transaction.dao.FeeRepository
import ivan.stats.service.StatsServiceClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Service
class TransactionService(private val feeRepository: FeeRepository, private val rateCalculator: RateCalculator) {

    val logger: Logger = LoggerFactory.getLogger(TransactionService::class.java)

    @Handler
    suspend fun fee(ctx: Context, feeRequest: FeeRequest): FeeRespone {
        val rate = rateCalculator.calculate(feeRequest)
        val fee = feeRequest.amount * rate
        logger.info("Rate and fee: $rate, $fee calculated for fee request: $feeRequest")

        // it is an idempotent action because we use upsert with conflicting unique constraint on transactionId
        withContext(Dispatchers.IO) {
            feeRepository.save(feeRequest.toDto())
        }

        val statsService = StatsServiceClient.fromContext(ctx)
        statsService.store(feeRequest).await()

        return FeeRespone(transactionId = feeRequest.transactionId, fee = fee, rate = rate)
    }

}