package com.amoferreira.cryptotracker.crypto.data.network.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.amoferreira.cryptotracker.crypto.data.network.dto.CoinPriceDto
import com.amoferreira.cryptotracker.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
fun CoinPriceDto.toCoinPrice() = CoinPrice(
    priceUsd = priceUsd,
    dateTime = Instant
        .ofEpochMilli(time)
        .atZone(ZoneId.of("UTC")),
)