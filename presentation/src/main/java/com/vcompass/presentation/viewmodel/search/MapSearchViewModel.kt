package com.vcompass.presentation.viewmodel.search

import com.vcompass.domain.usecase.location.GetCurrentLocationUseCase
import com.vcompass.presentation.event.global.GlobalConfig
import com.vcompass.presentation.event.global.GlobalEventBus
import com.vcompass.presentation.model.location.AppLocation
import com.vcompass.presentation.model.location.toAppLocation
import com.vcompass.presentation.util.collectToState
import com.vcompass.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapSearchViewModel(
    globalEventBus: GlobalEventBus,
    globalConfig: GlobalConfig,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase
) : BaseViewModel(globalEventBus, globalConfig) {
    private val _location = MutableStateFlow<AppLocation?>(null)
    val location = _location.asStateFlow()

    init {
        loadCurrentLocation()
    }

    fun loadCurrentLocation() {
        collectToState(
            block = { getCurrentLocationUseCase() }
        ) {
            _location.value = it.toAppLocation()
        }
    }
}