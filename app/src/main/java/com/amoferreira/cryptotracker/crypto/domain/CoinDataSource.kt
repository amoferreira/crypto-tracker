package com.amoferreira.cryptotracker.crypto.domain

import com.amoferreira.cryptotracker.core.domain.util.NetworkError
import com.amoferreira.cryptotracker.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}