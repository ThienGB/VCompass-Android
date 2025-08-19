package com.accessed.presentation.util

import androidx.lifecycle.viewModelScope
import com.accessed.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun <reified T> BaseViewModel.collectToState(
    crossinline block: suspend () -> Flow<Result<T>>,
    crossinline onSuccess: (T) -> Unit = {}
) {
    viewModelScope.launch {
        setLoading()
        block().collect { result ->
            result
                .onSuccess { data ->
                    setSuccess()
                    onSuccess(data)
                }
                .onFailure {
                    setError(it.message ?: "500/Unknown error")
                }
        }
    }
}
