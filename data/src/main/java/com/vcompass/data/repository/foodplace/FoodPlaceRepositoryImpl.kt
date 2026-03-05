package com.vcompass.data.repository.foodplace

import androidx.paging.PagingData
import com.vcompass.data.model.dto.business.foodplace.toFoodPlaceModel
import com.vcompass.data.remote.services.FoodPlaceService
import com.vcompass.data.util.createPagingFlow
import com.vcompass.domain.model.response.business.foodplace.FoodPlaceModel
import com.vcompass.domain.repository.foodplace.FoodPlaceRepository
import kotlinx.coroutines.flow.Flow

class FoodPlaceRepositoryImpl(
    private val foodPlaceService: FoodPlaceService
) : FoodPlaceRepository {

    override fun getFoodPlaces(
        search: String,
        city: String?,
        latitude: Double?,
        longitude: Double?
    ): Flow<PagingData<FoodPlaceModel>> {
        return createPagingFlow(
            apiCall = {
                foodPlaceService.getFoodPlaces(
                    page = it,
                    search = search,
                    city = city,
                    latitude = latitude,
                    longitude = longitude
                )
            },
            transform = { dto ->
                dto.toFoodPlaceModel()
            }
        )
    }
}