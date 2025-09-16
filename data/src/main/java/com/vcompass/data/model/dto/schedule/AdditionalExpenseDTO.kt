package com.vcompass.data.model.dto.schedule

import com.vcompass.data.model.dto.MapperDTO
import com.vcompass.domain.model.response.schedule.AdditionalExpense
import com.google.gson.annotations.SerializedName

data class AdditionalExpenseDTO(
    @SerializedName("cost")
    val cost: Int?,
    @SerializedName("description")
    val description: String?
): MapperDTO<AdditionalExpense> {
    override fun toDomain(): AdditionalExpense {
        return AdditionalExpense(
            cost = this.cost,
            description = this.description
        )
    }
}

