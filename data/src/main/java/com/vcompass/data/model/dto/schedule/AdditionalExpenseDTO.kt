package com.vcompass.data.model.dto.schedule

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.BaseDTO

data class AdditionalExpenseDTO(
    @SerializedName("name")
    val name: String?,
    @SerializedName("cost")
    val cost: Int?,
    @SerializedName("description")
    val description: String?
) : BaseDTO()

