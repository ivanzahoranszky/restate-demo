package ivan.dto

import ivan.dto.internal.Transaction
import ivan.dto.rest.FeeRequest

fun FeeRequest.toDto() = Transaction(
    transactionId = transactionId,
    amount = amount,
    asset = asset,
    assetType = assetType)
