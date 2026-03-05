package com.vcompass.domain.repository.foodplace

import androidx.paging.PagingData
import com.vcompass.domain.model.response.business.accommodation.AccommodationModel
import com.vcompass.domain.model.response.business.foodplace.FoodPlaceModel
import com.vcompass.domain.model.response.schedule.ScheduleModel
import kotlinx.coroutines.flow.Flow

interface FoodPlaceRepository {
    fun getFoodPlaces(
        search: String,
        city: String?,
        latitude: Double?,
        longitude: Double?
    ): Flow<PagingData<FoodPlaceModel>>
}