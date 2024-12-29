package com.amoferreira.cryptotracker.crypto.presentation.coinlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amoferreira.cryptotracker.core.domain.util.onError
import com.amoferreira.cryptotracker.core.domain.util.onSuccess
import com.amoferreira.cryptotracker.crypto.domain.CoinDataSource
import com.amoferreira.cryptotracker.crypto.presentation.coindetail.DataPoint
import com.amoferreira.cryptotracker.crypto.presentation.coinlist.components.CoinListState
import com.amoferreira.cryptotracker.crypto.presentation.models.CoinUi
import com.amoferreira.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class CoinListViewModel(
    private val coinDataSource: CoinDataSource,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CoinListState())
    val uiState = _uiState
        .onStart { loadCoins() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {
                selectCoin(action.coinUi)
            }
        }
    }

    private fun selectCoin(coinUi: CoinUi) {
        _uiState.update { it.copy(selectedCoin = coinUi) }
        loadCoinDetails(coinUi.id)
    }

    private fun loadCoinDetails(coinId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            coinDataSource
                .getCoinHistory(
                    coinId = coinId,
                    start = ZonedDateTime.now().minusDays(5),
                    end = ZonedDateTime.now()
                )
                .onSuccess { history ->
                    val dataPoints = history
                        .sortedBy { it.dateTime }
                        .map {
                            DataPoint(
                                x = it.dateTime.hour.toFloat(),
                                y = it.priceUsd.toFloat(),
                                xLabel = DateTimeFormatter
                                    .ofPattern("ha\nM/d")
                                    .format(it.dateTime),
                            )
                        }
                    _uiState.update {
                        it.copy(
                            selectedCoin = it.selectedCoin?.copy(
                                coinPriceHistory = dataPoints,
                            ),
                            isLoading = false,
                        )
                    }
                }
                .onError { error ->
                    _uiState.update { it.copy(isLoading = false) }
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            coinDataSource
                .getCoins()
                .onSuccess { coins ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            coins = coins.map { it.toCoinUi() },
                        )
                    }
                }
                .onError { error ->
                    _uiState.update { it.copy(isLoading = false) }
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }
}