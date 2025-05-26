package ivan.dto

import ivan.dto.Asset.*

enum class AssetType(val assets: List<Asset>) {
    FIAT (listOf(USD, EUR, AED)),
    CRYPTO (listOf(ETH, BTC))
}