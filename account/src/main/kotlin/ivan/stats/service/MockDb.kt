package ivan.stats.service

import java.math.BigDecimal
import java.util.UUID

object MockDb {

    var balance: BigDecimal = BigDecimal("1000")

    fun getBalance(accountId: UUID): BigDecimal = balance

    fun change(accountId: UUID, amount: BigDecimal) {
        if (balance + amount < BigDecimal.ZERO)
            throw IllegalArgumentException("Not enough balance")
        balance += amount
    }

}