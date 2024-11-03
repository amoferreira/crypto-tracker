package com.amoferreira.cryptotracker.crypto.presentation.models

import androidx.annotation.DrawableRes
import com.amoferreira.cryptotracker.crypto.domain.Coin
import com.amoferreira.cryptotracker.util.getDrawableIdForCoin

data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
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
