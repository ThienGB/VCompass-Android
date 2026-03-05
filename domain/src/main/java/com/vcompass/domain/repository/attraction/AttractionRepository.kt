package com.vcompass.domain.repository.attraction

import androidx.paging.PagingData
import com.vcompass.domain.model.response.business.accommodation.AccommodationModel
import com.vcompass.domain.model.response.business.attraction.AttractionModel
import com.vcompass.domain.model.response.schedule.ScheduleModel
import kotlinx.coroutines.flow.Flow

interface AttractionRepository {
    fun getAttractions(
        search: String,
        city: String?,
        latitude: Double?,
        longitude: Double?
    ): Flow<PagingData<AttractionModel>>
}