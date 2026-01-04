package com.vcompass.data.util.api_call

import com.vcompass.data.model.response.BaseResponse
import com.vcompass.data.model.response.ListResponse
import com.vcompass.data.model.response.paging.PagingResponse
import com.vcompass.data.model.response.SingleResponse

class ApiCallDsl {
    internal val calls = mutableListOf<ApiCall<*>>()

    fun <DTO, Domain> single(
        request: suspend () -> SingleResponse<DTO>,
        transform: (DTO) -> Domain
    ) {
        calls.add(ApiCall.Single(request, transform))
    }

    fun <DTO, Domain> list(
        request: suspend () -> ListResponse<DTO>,
        transform: (DTO) -> Domain
    ) {
        calls.add(ApiCall.List(request, transform))
    }

    fun <DTO, Domain> paging(
        request: suspend () -> PagingResponse<DTO>,
        transform: (DTO) -> Domain
    ) {
        calls.add(ApiCall.Paging(request, transform))
    }

    fun unit(
        request: suspend () -> BaseResponse
    ) {
        calls.add(ApiCall.UnitResponse(request))
    }
}
