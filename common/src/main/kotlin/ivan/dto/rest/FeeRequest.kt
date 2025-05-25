package ivan.dto.rest

import ivan.dto.serializer.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class FeeRequest(

    val transactionId: String,

    @Serializable(with = BigDecimalSerializer::class)
    val amount: BigDecimal,

    val assetType: String,

    val asset: String

)