package com.vcompass.data.remote.services

import com.vcompass.data.model.dto.business.accommodation.AccommodationDTO
import com.vcompass.data.model.response.SingleResponse
import com.vcompass.data.model.response.paging.PagingResponse
import com.vcompass.data.util.DataConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface AccommodationService {
    @GET("api/v1/accommodations?limit=${DataConstants.DEFAULT_PAGE_SIZE_20}&sortDir=desc")
    suspend fun getAccommodations(
        @Query("page") page: Int,
        @Query("sortBy") sortBy: String = "price",
        @Query("search") search: String = "",
        @Query("city") city: String? = null,
        @Query("latitude") latitude: Double? = null,
        @Query("longitude") longitude: Double? = null
    ): SingleResponse<PagingResponse<AccommodationDTO>>
}