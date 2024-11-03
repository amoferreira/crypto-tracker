package com.amoferreira.cryptotracker.crypto.data.network.mappers

import com.amoferreira.cryptotracker.crypto.data.network.dto.CoinDto
import com.amoferreira.cryptotracker.crypto.domain.Coin

fun CoinDto.toCoin(): Coin = Coin(
    id = id,
    rank = rank,
    name = name,
    symbol = symbol,
    marketCapUsd = marketCapUsd,
    priceUsd = priceUsd,
    changePercent24Hr = changePercent24Hr,
)