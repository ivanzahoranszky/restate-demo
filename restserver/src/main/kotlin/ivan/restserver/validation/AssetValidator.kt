package ivan.restserver.validation

import ivan.dto.rest.FeeRequest

class AssetValidator(private val nextValidator: Validator<FeeRequest>?): Validator<FeeRequest> {

    override suspend fun validate(toBeValidated: FeeRequest) {
        if (toBeValidated.asset !in toBeValidated.assetType.assets) {
            throw ValidationException("Asset type ${toBeValidated.assetType} is " +
                    "not one of the valid assets ${toBeValidated.assetType.assets}")
        }
        nextValidator?.validate(toBeValidated)
    }

}