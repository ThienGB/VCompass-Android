package com.vcompass.domain.usecase.accommodation

import androidx.paging.PagingData
import com.vcompass.domain.model.response.business.accommodation.AccommodationModel
import com.vcompass.domain.model.response.schedule.ScheduleModel
import com.vcompass.domain.repository.accommodation.AccommodationRepository
import com.vcompass.domain.repository.schedule.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetAccommodationsUseCase(private val repo: AccommodationRepository) {
    operator fun invoke(
        search: String,
        city: String? = null,
        latitude: Double? = null,
        longitude: Double? = null
    ): Flow<PagingData<AccommodationModel>> {
        return repo.getAccommodations(
            search = search,
            city = city,
            latitude = latitude,
            longitude = longitude
        )
    }
}