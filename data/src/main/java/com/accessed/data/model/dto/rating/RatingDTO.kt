package com.accessed.data.model.dto.rating

import com.accessed.data.model.dto.BaseDTO
import com.accessed.data.model.dto.MapperDTO
import com.accessed.data.model.dto.withBase
import com.accessed.domain.model.rating.Rating
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