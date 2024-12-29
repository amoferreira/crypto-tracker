package com.amoferreira.cryptotracker.crypto.presentation.models

import androidx.annotation.DrawableRes
import com.amoferreira.cryptotracker.core.presentation.util.getDrawableIdForCoin
import com.amoferreira.cryptotracker.crypto.domain.Coin
import com.amoferreira.cryptotracker.crypto.presentation.coindetail.DataPoint

data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    val coinPriceHistory: List<DataPoint> = emptyList(),
    @DrawableRes val icon: Int,
)

fun Coin.toCoinUi(): CoinUi =
    CoinUi(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd.toDisplayableNumber(),
        priceUsd = priceUsd.toDisplayableNumber(),
        changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
        icon = getDrawableIdForCoin(symbol),
    )

fun getAbsoluteChangeFormatted(price: Double, changePercent: Double): DisplayableNumber {
    val absoluteValue = price * (changePercent / 100)
    return absoluteValue.toDisplayableNumber()
}
