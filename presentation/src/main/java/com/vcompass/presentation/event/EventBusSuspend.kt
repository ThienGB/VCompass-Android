package com.vcompass.presentation.event

import kotlinx.coroutines.flow.StateFlow

interface EventBusSuspend<T> {
    val event: StateFlow<T>
    suspend fun emitEvent(event: T)
}