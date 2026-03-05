package com.vcompass.domain.repository.accommodation

import androidx.paging.PagingData
import com.vcompass.domain.model.response.business.accommodation.AccommodationModel
import com.vcompass.domain.model.response.schedule.ScheduleModel
import kotlinx.coroutines.flow.Flow

interface AccommodationRepository {
    fun getAccommodations(
        search: String,
        city: String?,
        latitude: Double?,
        longitude: Double?
    ): Flow<PagingData<AccommodationModel>>
}