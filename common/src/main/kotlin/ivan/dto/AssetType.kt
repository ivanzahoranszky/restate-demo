package ivan.dto

enum class AssetType(val assets: List<String>) {
    FIAT (listOf("USD", "EUR", "AED")),
    CRYPTO (listOf("ETH", "BTC"))
}