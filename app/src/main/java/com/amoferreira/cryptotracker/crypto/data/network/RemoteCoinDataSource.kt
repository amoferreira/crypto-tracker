package com.amoferreira.cryptotracker.crypto.data.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.amoferreira.cryptotracker.core.data.networking.constructUrl
import com.amoferreira.cryptotracker.core.data.networking.safeCall
import com.amoferreira.cryptotracker.core.domain.util.NetworkError
import com.amoferreira.cryptotracker.core.domain.util.Result
import com.amoferreira.cryptotracker.core.domain.util.map
import com.amoferreira.cryptotracker.crypto.data.network.dto.CoinHistoryResponseDto
import com.amoferreira.cryptotracker.crypto.data.network.dto.CoinsResponseDto
import com.amoferreira.cryptotracker.crypto.data.network.mappers.toCoin
import com.amoferreira.cryptotracker.crypto.data.network.mappers.toCoinPrice
import com.amoferreira.cryptotracker.crypto.domain.Coin
import com.amoferreira.cryptotracker.crypto.domain.CoinDataSource
import com.amoferreira.cryptotracker.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
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

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start.toMillis()
        val endMillis = end.toMillis()
        return safeCall<CoinHistoryResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }

    private fun ZonedDateTime.toMillis(): Long = this
        .withZoneSameInstant(ZoneId.of("UTC"))
        .toInstant()
        .toEpochMilli()
}