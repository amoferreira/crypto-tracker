@file:OptIn(ExperimentalLayoutApi::class)

package com.amoferreira.cryptotracker.crypto.presentation.coindetail

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amoferreira.cryptotracker.R
import com.amoferreira.cryptotracker.crypto.presentation.coindetail.components.InfoCard
import com.amoferreira.cryptotracker.crypto.presentation.coinlist.components.CoinListState
import com.amoferreira.cryptotracker.crypto.presentation.coinlist.components.previewCoin
import com.amoferreira.cryptotracker.crypto.presentation.models.getAbsoluteChangeFormatted
import com.amoferreira.cryptotracker.ui.theme.CryptoTrackerTheme
import com.amoferreira.cryptotracker.ui.theme.greenBackground

@Composable
fun CoinDetailScreen(
    uiState: CoinListState,
    modifier: Modifier = Modifier,
) {
    val contentColor = if(isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    if (uiState.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    } else if (uiState.selectedCoin != null) {
        val coin = uiState.selectedCoin
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = coin.icon),
                contentDescription = coin.name,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = coin.name,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                color = contentColor,
            )
            Text(
                text = coin.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = contentColor,
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
               InfoCard(
                   title = stringResource(id = R.string.market_cap),
                   formattedText = "$ ${coin.marketCapUsd.formattedNumber}",
                   icon = ImageVector.vectorResource(id = R.drawable.stock),
               )
                InfoCard(
                    title = stringResource(id = R.string.price),
                    formattedText = "$ ${coin.priceUsd.formattedNumber}",
                    icon = ImageVector.vectorResource(id = R.drawable.dollar),
                )
                val absoluteChange = getAbsoluteChangeFormatted(
                    coin.priceUsd.value,
                    coin.changePercent24Hr.value,
                )
                val isChangePositive = absoluteChange.value > 0.0
                val changeContentColor = if (isChangePositive) {
                    if (isSystemInDarkTheme()) Color.Green else greenBackground
                } else {
                    MaterialTheme.colorScheme.error
                }
                InfoCard(
                    title = stringResource(id = R.string.change_percent),
                    formattedText = absoluteChange.formattedNumber,
                    icon = if (isChangePositive) {
                        ImageVector.vectorResource(id = R.drawable.trending)
                    } else {
                        ImageVector.vectorResource(id = R.drawable.trending_down)
                    },
                    contentColor = changeContentColor,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinDetailScreenPreview() {
    CryptoTrackerTheme {
        CoinDetailScreen(
            uiState = CoinListState(
                selectedCoin = previewCoin,
            ),
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background,
            ),
        )
    }
}