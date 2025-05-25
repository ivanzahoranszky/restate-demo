package ivan.stats.service

import dev.restate.sdk.annotation.Workflow
import dev.restate.sdk.kotlin.WorkflowContext
import ivan.dto.rest.ChargeRequest
import ivan.dto.rest.ChargeResponse
import ivan.stats.Config
import org.slf4j.LoggerFactory

@Workflow
class PaymentWorkflow(private val config: Config) {

    private val logger = LoggerFactory.getLogger(PaymentWorkflow::class.java)

    @Workflow
    fun charge(ctx: WorkflowContext, chargeRequest: ChargeRequest): ChargeResponse {
        return ChargeResponse(accountId = chargeRequest.accountId, amount = chargeRequest.amount, result = true)
    }

}