package com.amoferreira.cryptotracker.crypto.presentation.coinlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.amoferreira.cryptotracker.crypto.presentation.coinlist.CoinListAction
import com.amoferreira.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinListScreen(
    uiState: CoinListState,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.coins) { coinUi ->
                CoinListItem(
                    coinUi = coinUi,
                    onClick = {
                        onAction(CoinListAction.OnCoinClick(coinUi))
                    },
                    modifier = Modifier.fillParentMaxWidth()
                )
                HorizontalDivider()
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinListScreenPreview() {
    CryptoTrackerTheme {
        CoinListScreen(
            uiState = CoinListState(
                coins = listOf(previewCoin, previewCoin, previewCoin)
            ),
            onAction = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
        )
    }
}