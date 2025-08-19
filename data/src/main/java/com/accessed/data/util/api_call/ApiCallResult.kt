package com.accessed.data.util.api_call

sealed class ApiCallResult {
    data class Success(val data: Any) : ApiCallResult()
    data class Failure(val error: Throwable) : ApiCallResult()
    object Done : ApiCallResult()
}
