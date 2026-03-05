package com.vcompass.domain.usecase.attraction

import androidx.paging.PagingData
import com.vcompass.domain.model.response.business.attraction.AttractionModel
import com.vcompass.domain.repository.attraction.AttractionRepository
import kotlinx.coroutines.flow.Flow

class GetAttractionsUseCase(private val repo: AttractionRepository) {
    operator fun invoke(
        search: String,
        city: String? = null,
        latitude: Double? = null,
        longitude: Double? = null
    ): Flow<PagingData<AttractionModel>> {
        return repo.getAttractions(
            search = search,
            city = city,
            latitude = latitude,
            longitude = longitude
        )
    }
}