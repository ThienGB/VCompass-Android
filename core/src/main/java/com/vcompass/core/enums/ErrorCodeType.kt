package com.vcompass.core.enums

enum class ErrorCodeType(val code: Int) {
    ERROR_BAD_REQUEST(400),
    ERROR_CODE_UNAUTHORIZED(401),
    ERROR_CODE_FORBIDDEN(403),
    ERROR_CODE_INTERNAL_SERVER(500);

    companion object {
        fun getErrorTypeByCode(code: Int?): ErrorCodeType? {
            return entries.firstOrNull { it.code == code }
        }
    }
}