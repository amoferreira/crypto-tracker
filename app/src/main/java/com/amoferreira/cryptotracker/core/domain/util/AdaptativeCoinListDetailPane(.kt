package com.amoferreira.cryptotracker.core.domain.util

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amoferreira.cryptotracker.core.presentation.util.ObserveAsEvents
import com.amoferreira.cryptotracker.core.presentation.util.toString
import com.amoferreira.cryptotracker.crypto.presentation.coindetail.CoinDetailScreen
import com.amoferreira.cryptotracker.crypto.presentation.coinlist.CoinListAction
import com.amoferreira.cryptotracker.crypto.presentation.coinlist.CoinListEvent
import com.amoferreira.cryptotracker.crypto.presentation.coinlist.CoinListScreen
import com.amoferreira.cryptotracker.crypto.presentation.coinlist.CoinListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdaptativeCoinListDetailPane(
    viewModel: CoinListViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.events) { event ->
        when (event) {
            is CoinListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG,
                ).show()
            }
        }
    }

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoinListScreen(
                    uiState = uiState,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when(action) {
                            is CoinListAction.OnCoinClick -> {
                                viewModel.onAction(action)
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail,
                                )
                            }
                        }
                    }
                )
            }
        },
        detailPane = {
            CoinDetailScreen(uiState = uiState)
        },
        modifier = modifier,
    )
}