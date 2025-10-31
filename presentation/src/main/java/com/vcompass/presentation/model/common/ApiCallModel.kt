package com.vcompass.presentation.model.common

import kotlinx.coroutines.flow.Flow

data class ApiCallModel<T>(
    val call: suspend () -> Flow<Result<T>>,
    val errorReturnItem: (Throwable) -> T,
)