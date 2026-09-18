package com.neacoding.data.auth

/**
 * Exchanges an expired token pair for a fresh one, or returns `null` when the session is over.
 *
 * Implemented by `:feature:auth:data` — `coreDataModule` deliberately provides no default so a
 * missing binding fails loudly instead of silently signing the player out.
 */
interface TokenRefresher {
    suspend fun refresh(tokens: AuthTokens): AuthTokens?
}
