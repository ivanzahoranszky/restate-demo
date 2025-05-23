package ivan.restserver.validation

import ivan.dto.rest.FeeRequest
import java.math.BigDecimal

class AmountValidator(private val nextValidator: Validator<FeeRequest>?): Validator<FeeRequest> {

    override suspend fun validate(toBeValidated: FeeRequest) {
        if (toBeValidated.amount <= BigDecimal.ZERO) {
            throw ValidationException("Amount ${toBeValidated.amount} is less or equal 0")
        }
        nextValidator?.validate(toBeValidated)
    }

}