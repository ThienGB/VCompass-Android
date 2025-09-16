package com.vcompass.data.model.dto.schedule

import com.vcompass.data.model.dto.BaseDTO
import com.vcompass.data.model.dto.MapperDTO
import com.vcompass.data.model.dto.withBase
import com.vcompass.domain.model.response.schedule.ScheduleLike
import com.google.gson.annotations.SerializedName

data class ScheduleLikeDTO(
    @SerializedName("idUser")
    val idUser: String?,
) : BaseDTO(), MapperDTO<ScheduleLike> {
    override fun toDomain(): ScheduleLike {
        return ScheduleLike(
            idUser = this.idUser,
        ).withBase(this)
    }
}

