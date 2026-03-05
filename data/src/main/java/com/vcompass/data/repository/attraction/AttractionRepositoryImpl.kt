package com.vcompass.data.repository.attraction

import androidx.paging.PagingData
import com.vcompass.data.model.dto.business.attraction.toAttractionModel
import com.vcompass.data.remote.services.AttractionService
import com.vcompass.data.util.createPagingFlow
import com.vcompass.domain.model.response.business.attraction.AttractionModel
import com.vcompass.domain.repository.attraction.AttractionRepository
import kotlinx.coroutines.flow.Flow

class AttractionRepositoryImpl(
    private val attractionService: AttractionService
) : AttractionRepository {

    override fun getAttractions(
        search: String,
        city: String?,
        latitude: Double?,
        longitude: Double?
    ): Flow<PagingData<AttractionModel>> {
        return createPagingFlow(
            apiCall = {
                attractionService.getAttractions(
                    page = it,
                    search = search,
                    city = city,
                    latitude = latitude,
                    longitude = longitude
                )
            },
            transform = { dto ->
                dto.toAttractionModel()
            }
        )
    }
}