package com.amoferreira.cryptotracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amoferreira.cryptotracker.core.presentation.util.ObserveAsEvents
import com.amoferreira.cryptotracker.core.presentation.util.toString
import com.amoferreira.cryptotracker.crypto.presentation.coindetail.CoinDetailScreen
import com.amoferreira.cryptotracker.crypto.presentation.coinlist.CoinListEvent
import com.amoferreira.cryptotracker.crypto.presentation.coinlist.CoinListViewModel
import com.amoferreira.cryptotracker.crypto.presentation.coinlist.components.CoinListScreen
import com.amoferreira.cryptotracker.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = koinViewModel<CoinListViewModel>()
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
                    when {
                        uiState.selectedCoin != null -> {
                            CoinDetailScreen(
                                uiState = uiState,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        else -> {
                            CoinListScreen(
                                uiState = uiState,
                                onAction = viewModel::onAction,
                                modifier = Modifier.padding(innerPadding),
                            )
                        }
                    }
                }
            }
        }
    }
}
