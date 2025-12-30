package com.vcompass.data.model.dto.user

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.BaseDTO

data class UserFavoriteDTO(
    @SerializedName("schedule")
    val schedule: List<String>?,
    @SerializedName("accommodation")
    val accommodation: List<String>?,
    @SerializedName("attraction")
    val attraction:List<String>?,
    @SerializedName("foodPlace")
    val foodPlace: List<String>?,
)