package com.example.vcompass.util

import com.vcompass.data.model.response.BaseResponse

fun Int.isExpiredToken(): Boolean {
    return this == BaseResponse.ERROR_CODE_UNAUTHORIZED
}

fun Int.isForbidden(): Boolean {
    return this == BaseResponse.ERROR_CODE_FORBIDDEN
}