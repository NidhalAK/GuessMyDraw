package com.neacoding.data.di

import com.neacoding.data.BuildConfig
import com.neacoding.data.auth.DataStoreTokenStorage
import com.neacoding.data.auth.TokenRefresher
import com.neacoding.data.auth.TokenStorage
import com.neacoding.data.network.HttpClientFactory
import com.neacoding.domain.util.DispatcherProvider
import com.neacoding.domain.util.StandardDispatchers
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Shared infrastructure every feature data module builds on.
 *
 * Requires `androidContext(...)` in `startKoin` (for `DataStoreTokenStorage`) and a
 * [TokenRefresher] binding contributed by `:feature:auth:data`.
 */
val coreDataModule = module {
    single<DispatcherProvider> { StandardDispatchers }

    singleOf(::DataStoreTokenStorage) { bind<TokenStorage>() }

    single<HttpClientEngine> { OkHttp.create() }

    single {
        val refresher = get<TokenRefresher>()
        HttpClientFactory.create(
            engine = get(),
            tokenStorage = get(),
            refreshTokens = refresher::refresh,
            enableLogging = BuildConfig.DEBUG,
        )
    }
}
