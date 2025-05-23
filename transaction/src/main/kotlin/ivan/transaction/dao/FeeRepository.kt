package ivan.transaction.dao

import com.zaxxer.hikari.HikariDataSource
import ivan.dto.internal.Transaction
import ivan.transaction.Config
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.upsert
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

        logger.info("Connecting to database {}", config.jdbcUrl)

        Database.connect(dataSource)
    }

    fun save(transaction: Transaction) {
        logger.info("Storing fee request: {}", transaction)
        transaction {
            TransactionFeeTable.upsert {
                it[TransactionFeeTable.transactionId] = transaction.transactionId
                it[TransactionFeeTable.amount] = transaction.amount
                it[TransactionFeeTable.assetType] = transaction.assetType
                it[TransactionFeeTable.asset] = transaction.asset
            }
        }
    }

}

object TransactionFeeTable : IdTable<Int>("transactionfees") {
    val amount = decimal("amount", precision = 10, scale = 2)
    val transactionId = varchar("transaction_id", 255)
        .uniqueIndex("transactionfees_transaction_id")
    override val id: Column<EntityID<Int>> = integer("id").autoIncrement().entityId()
    val assetType = varchar("asset_type", 255)
    val asset = varchar("asset", 255)
}