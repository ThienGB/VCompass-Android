package com.vcompass.data.model.response

open class AppException(
    private val code: Int?,
    private val data: Any? = null,
    private val errorMsg: String = ""
) : Exception() {
    fun getStatusCode() = code
    fun getErrorMessage() = errorMsg
    fun getData() = data
    fun toException(): Exception {
        return Exception("$code/$errorMsg")
    }
}

sealed class GenericException(
    code: Int?,
    data: Any? = null,
    override val message: String? = null
) : AppException(code, data, message ?: "") {

    data class UnknownException(
        override val message: String? = null
    ) : GenericException(0, null, message)

    data class RemoteException(
        private val code: Int?,
        private val data: Any? = null,
        override val message: String? = null
    ) : GenericException(code, data, message)

    data class ForbiddenException(
        private val code: Int?,
        private val data: Any? = null,
        override val message: String? = null
    ) : GenericException(code, data, message)

    data class UnAuthorizedException(
        private val code: Int?,
        private val data: Any? = null,
        override val message: String? = null
    ) : GenericException(code, data, message)

    data class GsonParseException(
        private val code: Int?,
        private val data: Any? = null,
        override val message: String? = null
    ) : GenericException(code, data, message)

    data class InternalServerErrorException(
        private val code: Int?,
        private val data: Any? = null,
        override val message: String? = null
    ) : GenericException(code, data, message)

    data class BadRequestException(
        private val code: Int?,
        private val data: Any? = null,
        override val message: String? = null
    ) : GenericException(code, data, message)

}
