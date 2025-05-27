package ivan.account.service

import dev.restate.sdk.common.TerminalException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.util.*

object MockDb {

    val logger: Logger = LoggerFactory.getLogger(MockDb::class.java)

    val db: HashMap<UUID, BigDecimal> = hashMapOf()

    init {
        db[UUID.fromString("ed6de3c7-95e2-4df0-b786-15f1b954da1c")] = BigDecimal("1000")
        db[UUID.fromString("b026ee11-e4be-40bf-9cee-4a2227534547")] = BigDecimal("2000")
        db[UUID.fromString("ddfe1edc-f7a7-464a-a362-4da98dc711ce")] = BigDecimal("3000")
    }

    @Synchronized
    fun getBalance(accountId: UUID): BigDecimal = db[accountId] ?: throw RuntimeException("Account not found")

    @Synchronized
    fun change(accountId: UUID, amount: BigDecimal) {
        logger.info("Changing balance of account $accountId by $amount")
        val account = db[accountId] ?: throw RuntimeException("Account not found")
        if (account + amount < BigDecimal.ZERO) {
            logger.error("Not enough balance")
            throw TerminalException("Not enough balance")
        }
        db[accountId]?.add(amount)
    }

}