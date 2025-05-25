package ivan.dto.rest

import ivan.dto.serializer.BigDecimalSerializer
import ivan.dto.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.UUID

@Serializable
data class ChargeResponse(

    @Serializable(with = UUIDSerializer::class)
    val accountId: UUID,

    @Serializable(with = BigDecimalSerializer::class)
    val amount: BigDecimal,

    val result: Boolean

)