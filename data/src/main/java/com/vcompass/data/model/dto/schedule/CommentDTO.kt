package com.vcompass.data.model.dto.schedule

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.BaseDTO

data class CommentDTO(
    @SerializedName("userId")
    val userId: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("replies")
    val replies: List<CommentDTO>?,
): BaseDTO()


