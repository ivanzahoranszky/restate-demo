package ivan.restserver.validation

import ivan.dto.FeeRequest

class AssetValidator(private val nextValidator: Validator<FeeRequest>?): Validator<FeeRequest> {

    private val validAssets = setOf("FIAT", "CRYPTO")

    override suspend fun validate(feeRequest: FeeRequest) {
        if (! (feeRequest.asset in validAssets)) {
            throw ValidationException("Asset ${feeRequest.asset} is not one of the valid assets ${validAssets}")
        }
        nextValidator?.validate(feeRequest)
    }

}