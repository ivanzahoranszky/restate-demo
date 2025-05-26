package ivan.dto.rest

import ivan.dto.serializer.BigDecimalSerializer
import ivan.dto.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.UUID

@Serializable
data class ChargeRequest(

    @Serializable(with = UUIDSerializer::class)
    val transactionId: UUID,

    @Serializable(with = UUIDSerializer::class)
    val accountId: UUID,

    @Serializable(with = BigDecimalSerializer::class)
    val amount: BigDecimal

)