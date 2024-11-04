package com.amoferreira.cryptotracker.crypto.presentation.coinlist

import com.amoferreira.cryptotracker.core.domain.util.NetworkError

interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}