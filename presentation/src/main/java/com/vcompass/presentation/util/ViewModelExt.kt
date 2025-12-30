package com.vcompass.presentation.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vcompass.presentation.model.common.ApiCallModel
import com.vcompass.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlin.coroutines.cancellation.CancellationException

inline fun <reified T> BaseViewModel.collectToState(
    crossinline block: suspend () -> Flow<Result<T>>,
    showLoading: Boolean = true,
    noinline defaultValue: ((Throwable) -> T)? = null,
    noinline onError: ((Throwable) -> Unit)? = null,
    crossinline onSuccess: (T) -> Unit = {}
) {
    baseCollectToState(
        block = block,
        showLoading = showLoading,
        onLoading = {
            setLoading()
        },
        defaultValue = defaultValue,
        onError = { e ->
            onError?.invoke(e) ?: showError(e.message ?: "500/Unknown error")
        },
        onSuccess = {
            setSuccess()
            onSuccess(it)
        }
    )
}

//===== Call multiple API =====
private suspend fun <T> firstSuccessOrLastFailure(flow: Flow<Result<T>>): Result<T> {
    var lastFailure: Throwable? = null
    return try {
        flow
            .onEach { it.onFailure { lastFailure = it } }
            .firstOrNull { it.isSuccess }
            ?: Result.failure(lastFailure ?: NoSuchElementException("No emissions"))
    } catch (e: Throwable) {
        if (e is CancellationException) throw e
        Result.failure(e)
    }
}

suspend fun <T> runAllOrDefault(
    calls: List<ApiCallModel<T>>
): List<T> = supervisorScope {
    calls.map { api ->
        async {
            val res: Result<T> = firstSuccessOrLastFailure(api.call())
            res.getOrElse { e -> api.errorReturnItem(e) }
        }
    }.awaitAll()
}

inline fun <reified T> BaseViewModel.collectToStateMany(
    crossinline provider: suspend () -> List<ApiCallModel<T>>,
    showLoading: Boolean = true,
    noinline onError: ((Throwable) -> Unit)? = null,
    crossinline onSuccess: (List<T>) -> Unit = {},
) {
    baseCollectToStateMany(
        provider = provider,
        showLoading = showLoading,
        onLoading = {
            setLoading()
        },
        onError = { e ->
            onError?.invoke(e) ?: showError(e.message ?: "500/Unknown error")
        },
        onSuccess = {
            setSuccess()
            onSuccess(it)
        }
    )
}

fun <Domain : Any, Ui : Any> BaseViewModel.collectPagingToState(
    source: Flow<PagingData<Domain>>,
    showLoading: Boolean = true,
    enableChangeState: Boolean = true,
    onError: ((Throwable) -> Unit)? = null,
    transform: (Domain) -> Ui,
    onSuccess: (PagingData<Ui>) -> Unit = {}
) {
    baseCollectPagingToState(
        source = source,
        showLoading = showLoading,
        onLoading = {
            if (enableChangeState)
                setLoading()
        },
        onError = { e ->
            onError?.invoke(e) ?: run {
                if (enableChangeState)
                    showError(e.message ?: "500/Unknown error")
            }
        },
        transform = transform,
        onSuccess = {
            onSuccess.invoke(it)
            if (enableChangeState)
                setSuccess()
        },
    )
}

internal fun <Domain : Any, Ui : Any> ViewModel.baseCollectPagingToState(
    source: Flow<PagingData<Domain>>,
    showLoading: Boolean = true,
    onError: ((Throwable) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    transform: (Domain) -> Ui,
    onSuccess: (PagingData<Ui>) -> Unit = {},
) {
    val uiStateFlow = MutableStateFlow<PagingData<Ui>>(PagingData.empty())
    viewModelScope.launch {
        if (showLoading) onLoading?.invoke()
        val uiPagingFlow: Flow<PagingData<Ui>> =
            source
                .map { pagingData -> pagingData.map(transform) }
                .cachedIn(viewModelScope)
        uiPagingFlow
            .catch { e ->
                if (e is CancellationException && e !is TimeoutCancellationException) throw e
                onError?.invoke(e)
            }
            .collectLatest { pagingDataUi ->
                uiStateFlow.value = pagingDataUi
                onSuccess.invoke(pagingDataUi)
            }
    }
}


inline fun <reified T> ViewModel.baseCollectToState(
    crossinline block: suspend () -> Flow<Result<T>>,
    showLoading: Boolean = true,
    crossinline onLoading: () -> Unit = {},
    noinline defaultValue: ((Throwable) -> T)? = null,
    noinline onError: ((Throwable) -> Unit)? = null,
    crossinline onSuccess: (T) -> Unit = {}
) {
    viewModelScope.launch {
        if (showLoading) onLoading.invoke()

        block().collectLatest { result ->
            result.fold(
                onSuccess = { data ->
                    onSuccess(data)
                },
                onFailure = { e ->
                    defaultValue?.let {
                        val fallback = runCatching { defaultValue(e) }.getOrNull()
                        fallback?.let {
                            onSuccess(it)
                        } ?: onError?.invoke(e)
                    } ?: onError?.invoke(e)
                }
            )
        }
    }
}

inline fun <reified T> BaseViewModel.baseCollectToStateMany(
    crossinline provider: suspend () -> List<ApiCallModel<T>>,
    showLoading: Boolean = true,
    crossinline onLoading: () -> Unit = {},
    noinline onError: ((Throwable) -> Unit)? = null,
    crossinline onSuccess: (List<T>) -> Unit = {},
) {
    viewModelScope.launch {
        if (showLoading) onLoading.invoke()

        val result = runCatching {
            runAllOrDefault(provider())
        }.onFailure { e ->
            if (e is CancellationException && e !is TimeoutCancellationException) throw e
        }

        result.fold(
            onSuccess = { list ->
                onSuccess.invoke(list)
            },
            onFailure = { e ->
                onError?.invoke(e)
            }
        )
    }
}