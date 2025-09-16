package com.vcompass.presentation.event

import kotlinx.coroutines.flow.SharedFlow

interface EventBusOnce {
    val event: SharedFlow<HashMap<String, String>>
    suspend fun emitEvent(event: HashMap<String, String>)
}