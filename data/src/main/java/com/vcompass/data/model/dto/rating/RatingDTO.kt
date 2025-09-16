package com.vcompass.data.model.dto.rating

import com.vcompass.data.model.dto.BaseDTO
import com.vcompass.data.model.dto.MapperDTO
import com.vcompass.data.model.dto.withBase
import com.vcompass.domain.model.rating.Rating
import com.google.gson.annotations.SerializedName

data class RatingDTO(
    @SerializedName("idUser")
    val idUser: String?,
    @SerializedName("rate")
    val rate: Int?,
    @SerializedName("content")
    val content: String?,
) : BaseDTO(), MapperDTO<Rating> {
    override fun toDomain(): Rating {
        return Rating(
            idUser = idUser,
            rate = rate,
            content = content
        ).withBase(this)
    }
}