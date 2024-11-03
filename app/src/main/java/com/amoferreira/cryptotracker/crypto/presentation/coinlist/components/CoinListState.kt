package com.amoferreira.cryptotracker.crypto.presentation.coinlist.components

import androidx.compose.runtime.Immutable
import com.amoferreira.cryptotracker.crypto.presentation.models.CoinUi

@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null,
)
