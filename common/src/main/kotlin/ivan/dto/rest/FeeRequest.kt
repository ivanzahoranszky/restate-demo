package ivan.dto.rest

import ivan.dto.AssetType
import ivan.dto.serializer.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class FeeRequest(

    val transactionId: String,

    @Serializable(with = BigDecimalSerializer::class)
    val amount: BigDecimal,

    val assetType: AssetType,

    val asset: String

)