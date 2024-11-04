package com.amoferreira.cryptotracker.di

import com.amoferreira.cryptotracker.core.data.networking.HttpClientFactory
import com.amoferreira.cryptotracker.crypto.data.network.RemoteCoinDataSource
import com.amoferreira.cryptotracker.crypto.domain.CoinDataSource
import com.amoferreira.cryptotracker.crypto.presentation.coinlist.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    viewModelOf(::CoinListViewModel)
}