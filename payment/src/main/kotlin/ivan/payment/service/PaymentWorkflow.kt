package ivan.payment.service

import dev.restate.sdk.annotation.Workflow
import dev.restate.sdk.common.TerminalException
import dev.restate.sdk.kotlin.WorkflowContext
import ivan.account.Config
import ivan.account.service.AccountServiceClient
import ivan.dto.Asset.USD
import ivan.dto.AssetType
import ivan.dto.rest.ChargeRequest
import ivan.dto.rest.ChargeResponse
import ivan.dto.rest.FeeRequest
import ivan.stats.service.StatsServiceClient
import ivan.transaction.service.TransactionServiceClient
import org.slf4j.LoggerFactory

@Workflow
class PaymentWorkflow(private val config: Config) {

    private val logger = LoggerFactory.getLogger(PaymentWorkflow::class.java)

    @Workflow
    suspend fun charge(ctx: WorkflowContext, chargeRequest: ChargeRequest): ChargeResponse {
        try {
            AccountServiceClient.fromContext(ctx).changeBalance(chargeRequest).await()

            val feeRequest = FeeRequest(
                transactionId = chargeRequest.transactionId.toString(),
                amount = chargeRequest.amount,
                asset = USD,
                assetType = AssetType.FIAT
            )

            TransactionServiceClient.fromContext(ctx).fee(feeRequest).await()

            StatsServiceClient.fromContext(ctx).store(feeRequest).await()

            return ChargeResponse(
                accountId = chargeRequest.accountId,
                amount = chargeRequest.amount,
                transactionId = chargeRequest.transactionId,
                result = true
            )
        } catch (e: TerminalException) {
            logger.error("Payment workflow failed: {}", e.message)
            return ChargeResponse(accountId = chargeRequest.accountId, amount = chargeRequest.amount, transactionId = chargeRequest.transactionId, result = false)
        }
    }

}