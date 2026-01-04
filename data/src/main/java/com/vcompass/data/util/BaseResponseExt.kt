package com.vcompass.data.util

import com.vcompass.data.model.response.BaseResponse
import com.vcompass.data.model.response.ListResponse
import com.vcompass.data.model.response.paging.PagingResponse
import com.vcompass.domain.model.response.PagingResponseModel
import com.vcompass.data.model.response.SingleResponse


fun <DTO> toErrorResult(error: String? = null): Result<DTO> {
    return Result.failure(Exception(error ?: "Unknown error"))
}

fun BaseResponse.toUnitResult(): Result<Unit> {
    if (this.isSuccess()) {
        return Result.success(Unit)
    }
    return toErrorResult()
}

fun <DTO, Domain> SingleResponse<DTO>.toResult(transform: (DTO) -> Domain): Result<Domain> {
    if (this.isSuccess() && this.data != null) {
        return Result.success(transform(this.data))
    }
    return toErrorResult()
}

fun <DTO, Domain> ListResponse<DTO>.toListResult(
    transform: (DTO) -> Domain
): Result<List<Domain>> {
    return if (this.isSuccess() && this.data != null) {
        Result.success(this.data.map(transform))
    } else {
        Result.failure(Exception(this.message ?: "Unknown error"))
    }
}

fun <DTO, Domain> PagingResponse<DTO>.toPagingResult(
    transform: (DTO) -> Domain
): Result<PagingResponseModel<Domain>> {
    if (content == null) return toErrorResult()

    val dataTransform = content.map { transform(it) }
    val newPaging = PagingResponseModel(
        content = dataTransform,
        totalPages = this.totalPages,
        last = this.last,
        totalElements = this.totalElements,
        numberOfElements = this.numberOfElements,
        hasPrePage = this.hasPrePage,
        hasNextPage = this.hasNextPage,
    )
    return Result.success(newPaging)
}
