package com.vcompass.data.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class ErrorResponse {
    @SerializedName("success")
    val success: Boolean = false

    @SerializedName("error")
    val error: String? = null

    @SerializedName("message")
    val message: String? = null

    fun getErrorMessage(): String? {
        error?.let {
            return it
        }

        message?.let {
            return it
        }

        return null
    }
}