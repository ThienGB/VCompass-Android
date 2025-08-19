package com.accessed.presentation.event.global

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class GlobalEventBus {
    private val _events = MutableSharedFlow<GlobalEvent>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<GlobalEvent> = _events

    suspend fun emit(event: GlobalEvent) = _events.emit(event)

    fun tryEmit(event: GlobalEvent) = _events.tryEmit(event)
}