package com.amoferreira.cryptotracker.crypto.data.network

import com.amoferreira.cryptotracker.core.data.networking.constructUrl
import com.amoferreira.cryptotracker.core.data.networking.safeCall
import com.amoferreira.cryptotracker.core.domain.util.NetworkError
import com.amoferreira.cryptotracker.core.domain.util.Result
import com.amoferreira.cryptotracker.core.domain.util.map
import com.amoferreira.cryptotracker.crypto.data.network.dto.CoinsResponseDto
import com.amoferreira.cryptotracker.crypto.data.network.mappers.toCoin
import com.amoferreira.cryptotracker.crypto.domain.Coin
import com.amoferreira.cryptotracker.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient,
): CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }
}