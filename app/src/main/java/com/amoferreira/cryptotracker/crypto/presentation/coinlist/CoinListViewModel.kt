package com.amoferreira.cryptotracker.crypto.presentation.coinlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amoferreira.cryptotracker.core.domain.util.onError
import com.amoferreira.cryptotracker.core.domain.util.onSuccess
import com.amoferreira.cryptotracker.crypto.domain.CoinDataSource
import com.amoferreira.cryptotracker.crypto.presentation.coinlist.components.CoinListState
import com.amoferreira.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinDataSource: CoinDataSource,
): ViewModel() {
    private val _uiState = MutableStateFlow(CoinListState())
    val uiState = _uiState
        .onStart { loadCoins() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    fun onAction(action: CoinListAction) {
        when(action) {
            is CoinListAction.OnCoinClick -> {

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
                }
        }
    }
}