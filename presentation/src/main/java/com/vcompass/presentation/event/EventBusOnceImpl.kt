package com.vcompass.presentation.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class EventBusOnceImpl() : EventBusOnce {
    private val _demoEvent = MutableSharedFlow<HashMap<String, String>>(
        replay = 0,
        extraBufferCapacity = 1
    )

    override val event: SharedFlow<HashMap<String, String>>
        get() = _demoEvent

    override suspend fun emitEvent(event: HashMap<String, String>) {
        _demoEvent.emit(event)
    }
}