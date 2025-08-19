package com.accessed.presentation.event

import com.accessed.presentation.event.global.GlobalConfig
import com.accessed.presentation.event.global.GlobalEventBus
import org.koin.dsl.module

val eventModule = module {
    single { GlobalConfig(get()) }
    single<EventBusOnce> { EventBusOnceImpl() }
    single { GlobalEventBus() }
}