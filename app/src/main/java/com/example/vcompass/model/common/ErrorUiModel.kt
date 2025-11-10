package com.example.vcompass.model.common

import com.example.vcompass.R
import com.vcompass.presentation.util.PresentationConstants

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
        PresentationConstants.ERROR_BAD_REQUEST -> R.string.lb_error_other
        PresentationConstants.ERROR_CODE_UNAUTHORIZED -> R.string.lb_error_unauthorized
        PresentationConstants.ERROR_CODE_FORBIDDEN -> R.string.lb_error_forbidden
        PresentationConstants.ERROR_CODE_INTERNAL_SERVER -> R.string.lb_error_internal_server
        else -> null
    }
}