package ivan.restate.dao

import com.zaxxer.hikari.HikariDataSource
import ivan.restate.Config
import ivan.dto.FeeRequest
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

class FeeRepository(private val config: Config) {

    private val logger = LoggerFactory.getLogger(javaClass)

    init {
        val dataSource = HikariDataSource().apply {
            jdbcUrl = config.jdbcUrl
            username = config.username
            password = config.password
            maximumPoolSize = config.maximumPoolSize
        }

        logger.info("Connecting to database {}. User {} Password {}", config.jdbcUrl, config.username, config.password)

        Database.connect(dataSource)
    }

    fun save(feeRequest: FeeRequest): Boolean {
        // consider using R2DBC instead of Exposed
        transaction {
            TransactionFeeTable.insert {
                it[transactionId] = feeRequest.transactionId
                it[amount] = feeRequest.amount
            }
        }
        return true
    }

}

object TransactionFeeTable : Table("transactionfees") {
    val amount = decimal("amount", precision = 10, scale = 2)
    val transactionId = varchar("transaction_id", 255)
}