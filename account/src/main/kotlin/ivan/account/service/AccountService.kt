package ivan.account.service

import dev.restate.sdk.annotation.Handler
import dev.restate.sdk.annotation.Service
import dev.restate.sdk.kotlin.Context
import ivan.dto.rest.ChargeRequest
import ivan.account.Config
import org.slf4j.LoggerFactory

@Service
class AccountService(private val config: Config) {

    private val logger = LoggerFactory.getLogger(AccountService::class.java)

    @Handler
    fun changeBalance(ctx: Context, chargeRequest: ChargeRequest) {
        logger.info("Changing balance of account ${chargeRequest.accountId} by ${chargeRequest.amount}")
        MockDb.change(chargeRequest.accountId, chargeRequest.amount)
    }

}