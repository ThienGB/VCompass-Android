package com.vcompass.core.extensions

import androidx.compose.ui.Modifier

inline fun Modifier.conditional(
    condition: Boolean,
    crossinline block: Modifier.() -> Modifier
): Modifier = if (condition) then(block(Modifier)) else this

inline fun <T> Modifier.optional(
    value: T?,
    crossinline block: Modifier.(T) -> Modifier
): Modifier = value?.let { block(it) } ?: this