package ivan.restserver.validation

import ivan.dto.rest.FeeRequest

class AssetTypeValidator(private val nextValidator: Validator<FeeRequest>?): Validator<FeeRequest> {

    private val validAssetsTypes = setOf("FIAT", "CRYPTO")

    override suspend fun validate(toBeValidated: FeeRequest) {
        if (toBeValidated.assetType !in validAssetsTypes) {
            throw ValidationException("Asset type ${toBeValidated.assetType} is not one of the valid assets $validAssetsTypes")
        }
        nextValidator?.validate(toBeValidated)
    }

}