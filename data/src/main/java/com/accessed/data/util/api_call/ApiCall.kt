package com.accessed.data.util.api_call

import com.accessed.data.model.response.BaseResponse
import com.accessed.data.model.response.ListResponse
import com.accessed.data.model.response.PagingResponse
import com.accessed.data.model.response.SingleResponse

sealed class ApiCall<Domain> {

    data class Single<DTO, Domain>(
        val request: suspend () -> SingleResponse<DTO>,
        val transform: (DTO) -> Domain
    ) : ApiCall<Domain>()

    data class List<DTO, Domain>(
        val request: suspend () -> ListResponse<DTO>,
        val transform: (DTO) -> Domain
    ) : ApiCall<ListResponse<Domain>>()

    data class Paging<DTO, Domain>(
        val request: suspend () -> PagingResponse<DTO>,
        val transform: (DTO) -> Domain
    ) : ApiCall<PagingResponse<Domain>>()

    data class UnitResponse(
        val request: suspend () -> BaseResponse
    ) : ApiCall<Unit>()
}