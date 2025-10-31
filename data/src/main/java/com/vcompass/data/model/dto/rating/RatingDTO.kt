package com.vcompass.data.model.dto.rating

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.BaseDTO

data class RatingDTO(
    @SerializedName("idUser")
    val idUser: String?,
    @SerializedName("rate")
    val rate: Int?,
    @SerializedName("content")
    val content: String?,
) : BaseDTO()