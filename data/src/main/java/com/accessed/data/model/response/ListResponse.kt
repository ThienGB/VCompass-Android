package com.accessed.data.model.response

import com.google.gson.annotations.SerializedName

class ListResponse<E> : BaseResponse() {

    @SerializedName("data")
    val data: List<E>? = null
}