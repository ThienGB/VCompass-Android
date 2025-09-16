package com.vcompass.presentation.util

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

inline fun <reified T> SavedStateHandle.getResultOnce(key: String): StateFlow<T?> {
    val flow = getStateFlow(key, null)
    return flow.map { value ->
        value?.let {
            remove<T>(key)
        }
        value
    } as StateFlow<T?>
}
