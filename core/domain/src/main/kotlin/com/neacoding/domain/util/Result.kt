@file:Suppress("MatchingDeclarationName")

package com.neacoding.domain.util

/**
 * Typed result used across every layer. Expected failures are values, never exceptions.
 *
 * Shadows `kotlin.Result` on purpose: importing this type is what makes the typed-error
 * discipline visible at call sites.
 */
sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.neacoding.domain.util.Error>(val error: E) : Result<Nothing, E>
}

/** A [Result] that carries no payload, only the fact that it succeeded or failed. */
typealias EmptyResult<E> = Result<Unit, E>

inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> = when (this) {
    is Result.Error -> Result.Error(error)
    is Result.Success -> Result.Success(map(data))
}

inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> = when (this) {
    is Result.Error -> this
    is Result.Success -> {
        action(data)
        this
    }
}

inline fun <T, E : Error> Result<T, E>.onFailure(action: (E) -> Unit): Result<T, E> = when (this) {
    is Result.Error -> {
        action(error)
        this
    }

    is Result.Success -> this
}

fun <T, E : Error> Result<T, E>.asEmptyResult(): EmptyResult<E> = map { }

/** Returns the payload on success, or [default] on failure. */
fun <T, E : Error> Result<T, E>.getOrElse(default: T): T = when (this) {
    is Result.Error -> default
    is Result.Success -> data
}

/** Returns the payload on success, or `null` on failure. */
fun <T, E : Error> Result<T, E>.getOrNull(): T? = when (this) {
    is Result.Error -> null
    is Result.Success -> data
}
