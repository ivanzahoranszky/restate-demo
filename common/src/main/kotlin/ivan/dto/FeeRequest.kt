package ivan.dto

import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class FeeRequest(
    val transactionId: String,
    @Serializable(with = BigDecimalSerializer::class)
    val amount: BigDecimal,
    val asset: String
)
