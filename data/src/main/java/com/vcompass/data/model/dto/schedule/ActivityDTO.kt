package com.vcompass.data.model.dto.schedule

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.BaseDTO
import com.vcompass.data.model.dto.business.BusinessDTO
import com.vcompass.data.model.dto.business.LocationDTO

data class ActivityDTO(
    @SerializedName("activityType")
    val activityType: String,
    @SerializedName("business")
    val business: BusinessDTO?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("imgSrc")
    val images: List<String>?,
    @SerializedName("cost")
    val cost: Int?,
    @SerializedName("costDescription")
    val costDescription: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("timeStart")
    val timeStart: String?,
    @SerializedName("timeEnd")
    val timeEnd: String?,
    @SerializedName("bookingId")
    val bookingId: String?
): BaseDTO()

