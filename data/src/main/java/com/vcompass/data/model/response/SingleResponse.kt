package com.vcompass.data.model.response

import com.google.gson.annotations.SerializedName

class SingleResponse<E> : BaseResponse(){

    @SerializedName("data")
    val data: E? = null
}