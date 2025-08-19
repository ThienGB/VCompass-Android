package com.accessed.data.util

import com.accessed.data.model.response.BaseResponse
import com.accessed.data.model.response.GenericException
import com.accessed.data.model.response.getDetailException
import com.google.gson.JsonParseException
import retrofit2.HttpException

fun Throwable.getDetailError(): GenericException {
    return when (this) {
        is HttpException -> {
            val response = this.response()
            BaseResponse().getDetailException(response)
        }

        is JsonParseException -> {
            val exception = GenericException.GsonParseException(
                code = 500, message = this.localizedMessage
            )
            exception
        }

        else -> {
            val exception = GenericException.RemoteException(
                code = 0, message = this.localizedMessage
            )
            exception
        }
    }
}