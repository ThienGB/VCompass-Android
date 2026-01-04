package com.vcompass.data.model.response.paging

import com.google.gson.annotations.SerializedName

data class PagingResponse<E>(
    @SerializedName("content")
    val content: List<E>? = listOf(),
    @SerializedName("totalPages")
    val totalPages: Long? = 0,
    @SerializedName("last")
    val last: Boolean? = false,
    @SerializedName("totalElements")
    val totalElements: Long? = 0,
    @SerializedName("numberOfElements")
    val numberOfElements: Long? = 0,
    @SerializedName("hasPrevPage")
    val hasPrePage: Boolean? = null,
    @SerializedName("hasNextPage")
    val hasNextPage: Boolean? = null
)
