package com.vcompass.presentation.viewmodel.schedule

import com.vcompass.domain.usecase.schedule.GetScheduleByIdUseCase
import com.vcompass.presentation.enums.BottomSheetType
import com.vcompass.presentation.event.global.GlobalConfig
import com.vcompass.presentation.event.global.GlobalEventBus
import com.vcompass.presentation.model.schedule.Activity
import com.vcompass.presentation.model.schedule.DayActivity
import com.vcompass.presentation.model.schedule.Schedule
import com.vcompass.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScheduleViewModel(
    globalEventBus: GlobalEventBus,
    globalConfig: GlobalConfig,
  //  private val getScheduleByIdUseCase: GetScheduleByIdUseCase
) : BaseViewModel(globalEventBus, globalConfig) {

    private val _sheetType = MutableStateFlow(BottomSheetType.NONE)
    val sheetType = _sheetType.asStateFlow()

    private val _schedule = MutableStateFlow(Schedule())
    val schedule = _schedule.asStateFlow()

    var currentDay = 1
    var currentActivity = Activity()

    fun setSheetType(type: BottomSheetType) {
        _sheetType.value = type
    }

    fun getSchedule() {
        _schedule.value =  mockSchedule
    }
    fun hideSheet() {
        _sheetType.value = BottomSheetType.NONE
    }

    fun addActivity(selectedActivity: Activity) {
        val currentSchedule = _schedule.value
        val updatedDays = currentSchedule.days?.map { dayActivity ->
            if (dayActivity.day == currentDay) {
                val updatedActivities = (dayActivity.activity ?: emptyList()) + selectedActivity
                dayActivity.copy(activity = updatedActivities)
            } else dayActivity
        } ?: listOf(DayActivity(day = currentDay, activity = listOf(selectedActivity)))
        _schedule.value = currentSchedule.copy(days = updatedDays)
    }

    fun addActivityCost(costName: String, cost: Int, costDescription: String) {
        val currentSchedule = _schedule.value

        val updatedDays = currentSchedule.days?.map { dayActivity ->
            if (dayActivity.day == currentDay) {
                val updatedActivities = dayActivity.activity?.map { activity ->
                    if (activity.id == currentActivity.id) {
                        activity.copy(
                            name = costName,
                            cost = cost,
                            costDescription = costDescription
                        )
                    } else activity
                }
                dayActivity.copy(activity = updatedActivities)
            } else dayActivity
        }

        _schedule.value = currentSchedule.copy(days = updatedDays)
    }
}