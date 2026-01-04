package com.vcompass.presentation.viewmodel.feed

import androidx.paging.PagingData
import com.vcompass.domain.usecase.schedule.GetAllSchedulesUseCase
import com.vcompass.presentation.event.global.GlobalConfig
import com.vcompass.presentation.event.global.GlobalEventBus
import com.vcompass.presentation.model.schedule.Schedule
import com.vcompass.presentation.model.schedule.toSchedule
import com.vcompass.presentation.util.collectPagingToState
import com.vcompass.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeFeedViewModel(
    globalEventBus: GlobalEventBus,
    globalConfig: GlobalConfig,
    private val getAllSchedulesUseCase: GetAllSchedulesUseCase
) : BaseViewModel(globalEventBus, globalConfig) {
    private val _schedules = MutableStateFlow<PagingData<Schedule>>(PagingData.empty())
    val schedules = _schedules.asStateFlow()

    init {
        getAllSchedules()
    }

    fun getAllSchedules() {
        collectPagingToState(
            source = getAllSchedulesUseCase(),
            transform = { it.toSchedule() }
        ) {
            _schedules.value = it
        }
    }
}