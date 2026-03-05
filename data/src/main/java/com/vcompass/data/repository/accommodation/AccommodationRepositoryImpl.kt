package com.vcompass.data.repository.accommodation

import androidx.paging.PagingData
import com.vcompass.data.model.dto.business.accommodation.toAccommodationModel
import com.vcompass.data.model.dto.schedule.toScheduleModel
import com.vcompass.data.remote.services.AccommodationService
import com.vcompass.data.remote.services.ScheduleService
import com.vcompass.data.util.asSingleResultFlow
import com.vcompass.data.util.createPagingFlow
import com.vcompass.domain.model.response.business.accommodation.AccommodationModel
import com.vcompass.domain.model.response.schedule.ScheduleModel
import com.vcompass.domain.repository.accommodation.AccommodationRepository
import com.vcompass.domain.repository.schedule.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class AccommodationRepositoryImpl(
    private val accommodationService: AccommodationService
) : AccommodationRepository {

    override fun getAccommodations(
        search: String,
        city: String?,
        latitude: Double?,
        longitude: Double?
    ): Flow<PagingData<AccommodationModel>> {
        return createPagingFlow(
            apiCall = {
                accommodationService.getAccommodations(
                    page = it,
                    search = search,
                    city = city,
                    latitude = latitude,
                    longitude = longitude
                )
            },
            transform = { dto ->
                dto.toAccommodationModel()
            }
        )
    }
}