package com.neacoding.presentation.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

/**
 * A string that is either already resolved or still needs a [Context] to be resolved.
 *
 * Use it for anything localizable (errors, labels). Values that are always dynamic and never
 * come from resources — a player name, a formatted score — stay plain [String].
 */
sealed interface UiText {

    data class DynamicString(val value: String) : UiText

    class StringResource(
        @param:StringRes val id: Int,
        val args: Array<Any> = emptyArray(),
    ) : UiText {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is StringResource) return false
            return id == other.id && args.contentEquals(other.args)
        }

        override fun hashCode(): Int = 31 * id + args.contentHashCode()

        override fun toString(): String = "StringResource(id=$id, args=${args.contentToString()})"
    }

    @Composable
    fun asString(): String = when (this) {
        is DynamicString -> value
        is StringResource -> androidx.compose.ui.res.stringResource(id, *args)
    }

    fun asString(context: Context): String = when (this) {
        is DynamicString -> value
        is StringResource -> context.getString(id, *args)
    }
}
