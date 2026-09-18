package com.neacoding.data.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

data class AuthTokens(val accessToken: String, val refreshToken: String)

/** Read/write access to the signed-in player's tokens. Consumed by the Ktor `Auth` plugin. */
interface TokenStorage {
    suspend fun get(): AuthTokens?
    suspend fun save(tokens: AuthTokens)
    suspend fun clear()
}

private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

class DataStoreTokenStorage(private val context: Context) : TokenStorage {

    override suspend fun get(): AuthTokens? = context.authDataStore.data
        .map { prefs ->
            val access = prefs[ACCESS_TOKEN] ?: return@map null
            val refresh = prefs[REFRESH_TOKEN] ?: return@map null
            AuthTokens(access, refresh)
        }
        .first()

    override suspend fun save(tokens: AuthTokens) {
        context.authDataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = tokens.accessToken
            prefs[REFRESH_TOKEN] = tokens.refreshToken
        }
    }

    override suspend fun clear() {
        context.authDataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN)
            prefs.remove(REFRESH_TOKEN)
        }
    }

    private companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }
}
