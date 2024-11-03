package com.amoferreira.cryptotracker.crypto.presentation.coinlist

import com.amoferreira.cryptotracker.crypto.presentation.models.CoinUi

interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi): CoinListAction
}