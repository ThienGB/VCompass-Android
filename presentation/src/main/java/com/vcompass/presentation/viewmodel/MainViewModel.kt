package com.vcompass.presentation.viewmodel

import com.vcompass.presentation.event.global.GlobalConfig
import com.vcompass.presentation.event.global.GlobalEventBus

class MainViewModel(val globalEvent: GlobalEventBus, globalConfig: GlobalConfig) :
    BaseViewModel(globalEvent, globalConfig) {
}