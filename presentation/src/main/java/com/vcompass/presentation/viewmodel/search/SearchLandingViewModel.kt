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

class SearchLandingViewModel(
    globalEventBus: GlobalEventBus,
    globalConfig: GlobalConfig,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase
) : BaseViewModel(globalEventBus, globalConfig) {
    private val _address = MutableStateFlow<AppLocation?>(null)
    val address = _address.asStateFlow()

    fun loadCurrentLocation() {
        collectToState(
            block = { getCurrentLocationUseCase() }
        ) {
            _address.value = it.toAppLocation()
        }
    }
}