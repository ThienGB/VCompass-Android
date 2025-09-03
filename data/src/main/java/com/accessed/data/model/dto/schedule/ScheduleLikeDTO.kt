package com.accessed.data.model.dto.schedule

import com.accessed.data.model.dto.BaseDTO
import com.accessed.data.model.dto.MapperDTO
import com.accessed.data.model.dto.withBase
import com.accessed.domain.model.response.schedule.ScheduleLike
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

