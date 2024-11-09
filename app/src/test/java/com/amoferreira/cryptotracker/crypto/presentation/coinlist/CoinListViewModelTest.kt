package com.amoferreira.cryptotracker.crypto.presentation.coinlist

import app.cash.turbine.test
import com.amoferreira.cryptotracker.core.domain.util.NetworkError
import com.amoferreira.cryptotracker.core.domain.util.Result
import com.amoferreira.cryptotracker.crypto.domain.Coin
import com.amoferreira.cryptotracker.crypto.domain.CoinDataSource
import com.amoferreira.cryptotracker.crypto.presentation.coinlist.components.CoinListState
import com.amoferreira.cryptotracker.crypto.presentation.models.toCoinUi
import com.amoferreira.cryptotracker.util.CoroutineRule
import io.mockk.awaits
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CoinListViewModelTest {
    @get:Rule
    val coroutineRule = CoroutineRule()
    private val testDispatcher = coroutineRule.dispatcher

    @MockK
    private lateinit var coinDataSourceMock: CoinDataSource
    private lateinit var sut: CoinListViewModel

    @Before
    fun setUp() {
        coinDataSourceMock = mockk()
        sut = CoinListViewModel(coinDataSourceMock)
    }

    @Test
    fun `when instantiating the view model then uiState should have default value`() {
        assertEquals(CoinListState(), sut.uiState.value)
    }

    @Test
    fun `when collecting uiState then should isLoading be true and load coins from data source`() = runTest(testDispatcher) {
        // Arrange
        coEvery { coinDataSourceMock.getCoins() } just awaits
        // Act
        sut.uiState.test {
            // Assert
            val expectedFirstLoadingState = CoinListState(isLoading = true)
            assertEquals(expectedFirstLoadingState, awaitItem())

            coVerify(exactly = 1) { coinDataSourceMock.getCoins() }
        }
    }

    @Test
    fun `when load coins return success then should update uiState with data and stop loading`() = runTest(testDispatcher) {
        // Arrange
        val expectedCoin = mockk<Coin>(relaxed = true)
        coEvery { coinDataSourceMock.getCoins() } returns Result.Success(listOf(expectedCoin))
        // Act
        sut.uiState.test {
            // Assert
            val expectedSuccessState = CoinListState(
                isLoading = false,
                coins = listOf(expectedCoin.toCoinUi())
            )
            assertEquals(expectedSuccessState, awaitItem())
        }
    }

    @Test
    fun `when load coins return error then should update uiState to stop loading and send event with returned error`() = runTest(testDispatcher) {
        // Arrange
        val expectedError = mockk<NetworkError>()
        coEvery { coinDataSourceMock.getCoins() } returns Result.Error(expectedError)
        // Act
        sut.uiState.test {
            // Assert
            val expectedErrorState = CoinListState(isLoading = false)
            assertEquals(expectedErrorState, awaitItem())
        }
        sut.events.test {
            assertEquals(CoinListEvent.Error(expectedError), awaitItem())
        }
    }
}