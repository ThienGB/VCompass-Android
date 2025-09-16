package com.vcompass.presentation.event

import com.vcompass.presentation.event.global.GlobalConfig
import com.vcompass.presentation.event.global.GlobalEventBus
import org.koin.dsl.module

val eventModule = module {
    single { GlobalConfig(get()) }
    single<EventBusOnce> { EventBusOnceImpl() }
    single { GlobalEventBus() }
}