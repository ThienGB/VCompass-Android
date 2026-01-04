package com.vcompass.data.util

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vcompass.data.model.response.BaseResponse
import com.vcompass.data.model.response.ListResponse
import com.vcompass.data.model.response.paging.PagingResponse
import com.vcompass.domain.model.response.PagingResponseModel
import com.vcompass.data.model.response.SingleResponse
import com.vcompass.data.model.response.paging.GenericPagingSource
import com.vcompass.data.util.api_call.ApiCallDsl
import com.vcompass.data.util.api_call.ApiCallResult
import com.vcompass.data.util.api_call.processApiCall
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.joinAll
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
): Flow<Result<Unit>> {
    return flow {
        emit(suspendFunc())
    }.map { response ->
        response.toUnitResult()
    }.catch { e ->
        if (e is CancellationException) throw e
        emit(Result.failure(e.getDetailError()))
    }.onCompletion {
        onCalledAfterApi.invoke()
    }
}

fun <DTO, Domain> asSingleResultFlow(
    suspendFunc: suspend () -> SingleResponse<DTO>,
    onCalledAfterApi: () -> Unit = {},
    transform: (DTO) -> Domain
): Flow<Result<Domain>> {
    return flow {
        emit(suspendFunc())
    }.map { response ->
        response.toResult(transform)
    }.catch { e ->
        if (e is CancellationException) throw e
        emit(Result.failure(e.getDetailError()))
    }.onCompletion {
        onCalledAfterApi.invoke()
    }
}

//=====.LIST RESULT =====
fun <DTO, Domain> asResultListFlow(
    suspendFunc: suspend () -> ListResponse<DTO>,
    onCalledAfterApi: () -> Unit = {},
    transform: (DTO) -> Domain
): Flow<Result<List<Domain>>> {
    return flow {
        emit(suspendFunc())
    }.map { response ->
        response.toListResult(transform)
    }.catch { e ->
        if (e is CancellationException) throw e
        emit(Result.failure(e.getDetailError()))
    }.onCompletion {
        onCalledAfterApi.invoke()
    }
}

//=====.PAGING RESULT =====
fun <DTO, Domain> asPagingResultFlow(
    suspendFunc: suspend () -> SingleResponse<PagingResponse<DTO>>,
    onCalledAfterApi: () -> Unit = {},
    transform: (DTO) -> Domain
): Flow<Result<PagingResponseModel<Domain>>> {
    return flow {
        emit(suspendFunc())
    }.map { response ->
        response.data?.toPagingResult(transform) ?: toErrorResult()
    }.catch { e ->
        if (e is CancellationException) throw e
        emit(Result.failure(e.getDetailError()))
    }.onCompletion {
        onCalledAfterApi.invoke()
    }
}

suspend fun <DTO, Domain> fetchPageOnce(
    suspendFunc: suspend () -> SingleResponse<PagingResponse<DTO>>,
    transform: (DTO) -> Domain,
    onSuccess: () -> Unit = {}
): Result<PagingResponseModel<Domain>> {
    return try {
        val response = suspendFunc()
        val pagingResponse = response.data
        pagingResponse?.toPagingResult(transform)?.let {
            onSuccess.invoke()
            it
        } ?: toErrorResult()
    } catch (e: Throwable) {
        if (e is CancellationException) throw e
        Result.failure(e.getDetailError())
    }
}

fun <DTO, Domain : Any> createPagingFlow(
    pageSize: Int = DataConstants.DEFAULT_PAGE_SIZE_20,
    prefetchDistance: Int = DataConstants.LIMIT_SIZE_ITEM_FOR_LOAD_MORE,
    onCalledAfterApi: () -> Unit = {},
    onSuccess: () -> Unit = {},
    onTotalItems: (Int) -> Unit = {},
    transform: (DTO) -> Domain,
    apiCall: suspend (page: Int) -> SingleResponse<PagingResponse<DTO>>
): Flow<PagingData<Domain>> {
    return Pager(
        config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = prefetchDistance,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            GenericPagingSource(
                initialPage = 0,
                onCalledAfterApi = onCalledAfterApi,
                onTotalItems = { total ->
                    onTotalItems.invoke(total)
                },
                apiCaller = { page ->
                    fetchPageOnce(
                        suspendFunc = { apiCall(page) },
                        transform = transform,
                        onSuccess = onSuccess
                    )
                }
            )
        }
    ).flow
}

fun <Domain> PagingResponse<Domain>.asResultFlow(
    onCalledAfterApi: () -> Unit = {},
): Flow<Result<PagingResponse<Domain>>> {
    return flow {
        emit(Result.success(this@asResultFlow))
    }.catch { e ->
        if (e is CancellationException) throw e
        emit(Result.failure(e.getDetailError()))
    }.onCompletion {
        onCalledAfterApi.invoke()
    }
}

fun <Domain> Domain.asResultFlow(
    onCalledAfterApi: () -> Unit = {},
): Flow<Result<Domain>> {
    return flow {
        emit(Result.success(this@asResultFlow))
    }.catch { e ->
        if (e is CancellationException) throw e
        emit(Result.failure(e.getDetailError()))
    }.onCompletion {
        onCalledAfterApi.invoke()
    }
}

fun <Domain> List<Domain>.asResultFlow(
    onCalledAfterApi: () -> Unit = {},
): Flow<Result<List<Domain>>> {
    return flow {
        emit(Result.success(this@asResultFlow))
    }.catch { e ->
        if (e is CancellationException) throw e
        emit(Result.failure(e.getDetailError()))
    }.onCompletion {
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
                        send(ApiCallResult.Failure(t.getDetailError()))
                    }
                }
            }
        }
    }

    parallelJobs.joinAll()
    if (!allowCancelWhenError)
        send(ApiCallResult.Done)
}