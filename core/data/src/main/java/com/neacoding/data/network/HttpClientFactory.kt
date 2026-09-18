package com.neacoding.data.network

import co.touchlab.kermit.Logger as KermitLogger
import com.neacoding.data.auth.AuthTokens
import com.neacoding.data.auth.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Builds the single [HttpClient] used by every remote data source.
 *
 * The engine is injected rather than hardcoded so tests can pass a `MockEngine`.
 */
object HttpClientFactory {

    fun create(
        engine: HttpClientEngine,
        tokenStorage: TokenStorage,
        refreshTokens: suspend (AuthTokens) -> AuthTokens?,
        enableLogging: Boolean,
    ): HttpClient = HttpClient(engine) {
        expectSuccess = false

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                    explicitNulls = false
                },
            )
        }

        if (enableLogging) {
            install(Logging) {
                level = LogLevel.HEADERS
                logger = object : Logger {
                    override fun log(message: String) = KermitLogger.d(tag = "HttpClient") { message }
                }
            }
        }

        install(Auth) {
            bearer {
                loadTokens {
                    tokenStorage.get()?.let { BearerTokens(it.accessToken, it.refreshToken) }
                }
                refreshTokens {
                    val current = tokenStorage.get() ?: return@refreshTokens null
                    val renewed = refreshTokens(current)
                    if (renewed == null) {
                        tokenStorage.clear()
                        null
                    } else {
                        tokenStorage.save(renewed)
                        BearerTokens(renewed.accessToken, renewed.refreshToken)
                    }
                }
            }
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }
}
