package com.vcompass.data.model.dto.schedule

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.BaseDTO

data class LikeDTO(
    @SerializedName("userId")
    val userId: String?,
) : BaseDTO()

