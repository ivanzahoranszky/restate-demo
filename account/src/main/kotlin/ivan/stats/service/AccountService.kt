package ivan.stats.service

import dev.restate.sdk.annotation.Handler
import dev.restate.sdk.annotation.Service
import dev.restate.sdk.kotlin.Context
import ivan.dto.internal.Account
import ivan.dto.rest.FeeRequest
import ivan.stats.Config
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.util.UUID

@Service
class AccountService(private val config: Config) {

    private val logger = LoggerFactory.getLogger(AccountService::class.java)

    // TODO
    private var balance = BigDecimal("1000")

    @Handler
    fun getBalance(ctx: Context, accountId: UUID): Account {
        return Account(accountId, balance)
    }

    @Handler
    fun charge(ctx: Context, accountId: UUID, amount: BigDecimal) {
        if (balance >= amount)
            balance -= amount
        else
            throw RuntimeException("Insufficient funds")
    }

    fun credit(ctx: Context, accountId: UUID, amount: BigDecimal) {
        balance += amount
    }

}