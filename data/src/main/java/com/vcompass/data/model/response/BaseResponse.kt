package com.vcompass.data.model.response

import com.vcompass.data.util.tryParseObject
import com.vcompass.data.util.tryParseToInt
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import retrofit2.Response

open class BaseResponse {
    @SerializedName("status")
    var status: Int? = null

    @SerializedName("code")
    var code: Int? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("error")
    var error: String? = null

    @SerializedName("token")
    var token: String? = null

    fun isBadRequest(): Boolean = getResponseStatus().tryParseToInt() == ERROR_BAD_REQUEST

    fun isUnauthorized(): Boolean = getResponseStatus().tryParseToInt() == ERROR_CODE_UNAUTHORIZED

    fun isSuccess(): Boolean = getResponseStatus().tryParseToInt() in STATUS_CODE_SUCCEED..299

    fun isForbidden(): Boolean = getResponseStatus().tryParseToInt() == ERROR_CODE_FORBIDDEN

    fun isErrorInternalServer(): Boolean =
        getResponseStatus().tryParseToInt() == ERROR_CODE_INTERNAL_SERVER

    fun getResponseMessage(): String? = message ?: error

    private fun getResponseStatus() = status ?: code

    companion object {
        const val STATUS_CODE_SUCCEED = 200
        const val ERROR_BAD_REQUEST = 400
        const val ERROR_CODE_UNAUTHORIZED = 401
        const val ERROR_CODE_FORBIDDEN = 403
        const val ERROR_CODE_INTERNAL_SERVER = 500
    }
}

fun BaseResponse.getDetailException(response: Response<*>?): GenericException {
    val bodyAsString = response?.errorBody()?.string() ?: ""
    try {
        val responseModel =
            Gson().fromJson(bodyAsString, SingleResponse::class.java)
        if (responseModel.code == null)
            responseModel.code = response?.code()
        val errorException: GenericException =
            when {
                responseModel.isUnauthorized() -> {
                    GenericException.UnAuthorizedException(
                        code = response?.code(),
                        message = responseModel.getResponseMessage(),
                        data = responseModel?.data
                    )
                }

                responseModel.isForbidden() -> {
                    GenericException.ForbiddenException(
                        code = response?.code(),
                        message = responseModel.getResponseMessage(),
                        data = responseModel?.data
                    )
                }

                responseModel.isErrorInternalServer() -> {
                    GenericException.InternalServerErrorException(
                        code = response?.code(),
                        message = responseModel.getResponseMessage(),
                        data = responseModel?.data
                    )
                }

                responseModel.isBadRequest() -> {
                    val dataObject = responseModel?.data.tryParseObject<ErrorResponse>()
                    GenericException.BadRequestException(
                        code = response?.code(),
                        message = dataObject?.getErrorMessage()
                            ?: responseModel?.getResponseMessage(),
                        data = responseModel?.data
                    )
                }

                else -> GenericException.RemoteException(
                    code = response?.code(),
                    message = responseModel.getResponseMessage(),
                    data = responseModel?.data
                )
            }
        return errorException
    } catch (e: Exception) {
        val exception = when {
            e is JsonParseException -> {
                GenericException.GsonParseException(
                    code = response?.code(),
                    message = bodyAsString.ifEmpty { e.localizedMessage }
                )
            }

            else -> {
                GenericException.RemoteException(
                    code = response?.code(),
                    message = bodyAsString.ifEmpty { e.localizedMessage }
                )
            }
        }
        return exception
    }
}