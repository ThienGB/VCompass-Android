package com.vcompass.data.util.api_call

import com.vcompass.data.util.toListResult
import com.vcompass.data.util.toPagingResult
import com.vcompass.data.util.toResult
import com.vcompass.data.util.toUnitResult

suspend fun processApiCall(call: ApiCall<*>): Result<Any> = when (call) {
    is ApiCall.Single<*, *> -> {
        val typed = call as ApiCall.Single<Any, Any>
        typed.request().toResult(typed.transform)
    }

    is ApiCall.List<*, *> -> {
        val typed = call as ApiCall.List<Any, Any>
        typed.request().toListResult(typed.transform)
    }

    is ApiCall.Paging<*, *> -> {
        val typed = call as ApiCall.Paging<Any, Any>
        typed.request().toPagingResult(typed.transform)
    }

    is ApiCall.UnitResponse -> {
        call.request().toUnitResult()
    }
}