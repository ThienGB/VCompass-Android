package com.vcompass.presentation.viewmodel.home

import com.vcompass.presentation.event.global.GlobalConfig
import com.vcompass.presentation.event.global.GlobalEventBus
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.util.collectToState
import com.vcompass.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class HomeViewModel(
    globalEventBus: GlobalEventBus,
    globalConfig: GlobalConfig,
) : BaseViewModel(globalEventBus, globalConfig) {

    private val _bottomBarVisible = MutableSharedFlow<Boolean>(0, 1)
    var bottomBarVisible = _bottomBarVisible.asSharedFlow()


    fun hideBottomBar() {
        _bottomBarVisible.tryEmit(false)
    }

    fun showBottomBar() {
        _bottomBarVisible.tryEmit(true)
    }
}
