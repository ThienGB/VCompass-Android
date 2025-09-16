package com.example.vcompass.model.common

import com.vcompass.data.model.response.BaseResponse
import com.example.vcompass.R

class ErrorUiModel(data: String) {
    var code: Int? = null
    var message: String? = null

    init {
        val dataList = data.split("/")
        code = dataList.firstOrNull()?.trim()?.toIntOrNull()
        message = dataList.lastOrNull()?.trim()
    }
}

fun ErrorUiModel.getErrorResId(): Int? {
    return when (code) {
        BaseResponse.ERROR_BAD_REQUEST -> R.string.app_name
        BaseResponse.ERROR_CODE_UNAUTHORIZED -> R.string.app_name
        BaseResponse.ERROR_CODE_FORBIDDEN -> R.string.app_name
        BaseResponse.ERROR_CODE_INTERNAL_SERVER -> R.string.app_name
        else -> null
    }
}