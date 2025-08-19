package com.accessed.data.util

import com.accessed.data.model.response.BaseResponse
import com.accessed.data.model.response.ListResponse
import com.accessed.data.model.response.PagingResponse
import com.accessed.data.model.response.SingleResponse
import com.accessed.data.util.api_call.ApiCallDsl
import com.accessed.data.util.api_call.ApiCallResult
import com.accessed.data.util.api_call.processApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

//=====.UNIT RESULT =====
fun asUnitResultFlow(
    onCalledAfterApi: () -> Unit = {}
): Flow<Result<Unit>> = flow {
    emit(Result.success(Unit))
    onCalledAfterApi.invoke()
}

fun asUnitResultFlow(
    suspendFunc: suspend () -> BaseResponse,
    onCalledAfterApi: () -> Unit = {}
): Flow<Result<Unit>> = flow {
    try {
        emit(suspendFunc().toUnitResult())
    } catch (throwable: Throwable) {
        emit(Result.failure(throwable.getDetailError().toException()))
    } finally {
        onCalledAfterApi.invoke()
    }
}

fun <DTO, Domain> asSingleResultFlow(
    suspendFunc: suspend () -> SingleResponse<DTO>,
    onCalledAfterApi: () -> Unit = {},
    transform: (DTO) -> Domain
): Flow<Result<Domain>> = flow {
    try {
        emit(suspendFunc().toResult(transform))
    } catch (throwable: Throwable) {
        emit(Result.failure(throwable.getDetailError().toException()))
    } finally {
        onCalledAfterApi.invoke()
    }
}

//=====.LIST RESULT =====
fun <DTO, Domain> asResultListFlow(
    suspendFunc: suspend () -> ListResponse<DTO>,
    onCalledAfterApi: () -> Unit = {},
    transform: (DTO) -> Domain
): Flow<Result<List<Domain>>> = flow {
    try {
        emit(suspendFunc().toListResult(transform))
    } catch (throwable: Throwable) {
        emit(Result.failure(throwable.getDetailError().toException()))
    } finally {
        onCalledAfterApi.invoke()
    }
}

//=====.PAGING RESULT =====
fun <DTO, Domain> asPagingResultFlow(
    suspendFunc: suspend () -> PagingResponse<DTO>,
    onCalledAfterApi: () -> Unit = {},
    transform: (DTO) -> Domain
): Flow<Result<PagingResponse<Domain>>> = flow {
    try {
        emit(suspendFunc().toPagingResult(transform))
    } catch (throwable: Throwable) {
        emit(Result.failure(throwable.getDetailError().toException()))
    } finally {
        onCalledAfterApi.invoke()
    }
}

fun <Domain> PagingResponse<Domain>.asResultFlow(
    onCalledAfterApi: () -> Unit = {},
): Flow<Result<PagingResponse<Domain>>> = flow {
    try {
        emit(Result.success(this@asResultFlow))
    } catch (throwable: Throwable) {
        emit(Result.failure(throwable.getDetailError().toException()))
    } finally {
        onCalledAfterApi.invoke()
    }
}

fun <Domain> Domain.asResultFlow(
    onCalledAfterApi: () -> Unit = {},
): Flow<Result<Domain>> = flow {
    try {
        emit(Result.success(this@asResultFlow))
    } catch (throwable: Throwable) {
        emit(Result.failure(throwable.getDetailError().toException()))
    } finally {
        onCalledAfterApi.invoke()
    }
}

fun <Domain> List<Domain>.asResultFlow(
    onCalledAfterApi: () -> Unit = {},
): Flow<Result<List<Domain>>> = flow {
    try {
        emit(Result.success(this@asResultFlow))
    } catch (throwable: Throwable) {
        emit(Result.failure(throwable.getDetailError().toException()))
    } finally {
        onCalledAfterApi.invoke()
    }
}

fun asMultipleResultFlow(
    allowCancelWhenError: Boolean = false,
    builder: ApiCallDsl.() -> Unit,
): Flow<ApiCallResult> = channelFlow {
    val dsl = ApiCallDsl().apply(builder)
    val cancelRequested = AtomicBoolean(false)

    val parallelJobs = dsl.calls.map { it ->
        launch {
            if (cancelRequested.get()) return@launch

            try {
                val result = processApiCall(it)
                send(ApiCallResult.Success(result))
            } catch (t: Throwable) {
                if (allowCancelWhenError) {
                    if (!cancelRequested.get()) {
                        cancelRequested.set(true)
                        send(ApiCallResult.Failure(t.getDetailError().toException()))
                    }
                }
            }
        }
    }

    parallelJobs.forEach { it.join() }
    if (!allowCancelWhenError)
        send(ApiCallResult.Done)
}