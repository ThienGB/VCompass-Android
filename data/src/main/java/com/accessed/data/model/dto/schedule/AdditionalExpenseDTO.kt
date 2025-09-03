package com.accessed.data.model.dto.schedule

import com.accessed.data.model.dto.MapperDTO
import com.accessed.domain.model.response.schedule.AdditionalExpense
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

