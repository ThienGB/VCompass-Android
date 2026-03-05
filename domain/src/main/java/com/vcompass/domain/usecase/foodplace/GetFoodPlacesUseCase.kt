package com.vcompass.domain.usecase.foodplace

import androidx.paging.PagingData
import com.vcompass.domain.model.response.business.foodplace.FoodPlaceModel
import com.vcompass.domain.repository.foodplace.FoodPlaceRepository
import kotlinx.coroutines.flow.Flow

class GetFoodPlacesUseCase(private val repo: FoodPlaceRepository) {
    operator fun invoke(
        search: String,
        city: String? = null,
        latitude: Double? = null,
        longitude: Double? = null
    ): Flow<PagingData<FoodPlaceModel>> {
        return repo.getFoodPlaces(
            search = search,
            city = city,
            latitude = latitude,
            longitude = longitude
        )
    }
}