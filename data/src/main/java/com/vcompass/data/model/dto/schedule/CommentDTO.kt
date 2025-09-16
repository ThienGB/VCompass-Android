package com.vcompass.data.model.dto.schedule

import com.vcompass.data.model.dto.BaseDTO
import com.vcompass.data.model.dto.MapperDTO
import com.vcompass.data.model.dto.withBase
import com.vcompass.domain.model.response.schedule.Comment
import com.google.gson.annotations.SerializedName

data class CommentDTO(
    @SerializedName("idUser")
    val idUser: String?,
    @SerializedName("userName")
    val userName: String?,
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("replies")
    val replies: List<CommentDTO>?,
): BaseDTO(), MapperDTO<Comment> {
    override fun toDomain(): Comment {
        return Comment(
            idUser = this.idUser,
            userName = this.userName,
            avatar = this.avatar,
            content = this.content,
            replies = this.replies?.map { it.toDomain() }
        ).withBase(this)
    }
}


