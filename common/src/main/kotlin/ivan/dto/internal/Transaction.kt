package ivan.dto.internal

import ivan.dto.AssetType
import ivan.dto.serializer.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class Transaction(

    val transactionId: String,

    @Serializable(with = BigDecimalSerializer::class)
    val amount: BigDecimal,

    val assetType: AssetType,

    val asset: String

)
