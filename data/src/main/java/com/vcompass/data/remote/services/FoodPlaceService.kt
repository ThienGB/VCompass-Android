package com.vcompass.data.remote.services

import com.vcompass.data.model.dto.business.foodplace.FoodPlaceDTO
import com.vcompass.data.model.response.SingleResponse
import com.vcompass.data.model.response.paging.PagingResponse
import com.vcompass.data.util.DataConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodPlaceService {
    @GET("api/v1/foodPlaces?limit=${DataConstants.DEFAULT_PAGE_SIZE_20}&sortDir=desc")
    suspend fun getFoodPlaces(
        @Query("page") page: Int,
        @Query("sortBy") sortBy: String = "averageRating",
        @Query("search") search: String = "",
        @Query("city") city: String? = null,
        @Query("latitude") latitude: Double? = null,
        @Query("longitude") longitude: Double? = null
    ): SingleResponse<PagingResponse<FoodPlaceDTO>>
}