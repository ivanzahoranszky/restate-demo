package ivan.dto

import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class FeeRespone(
    val transactionId: String,
    @Serializable(with = BigDecimalSerializer::class)
    val fee: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val rate: BigDecimal
)