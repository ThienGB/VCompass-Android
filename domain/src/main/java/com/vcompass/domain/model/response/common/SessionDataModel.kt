package com.vcompass.domain.model.response.common

data class SessionDataModel(
    var isOpenedApp: Boolean? = false,
    var isRememberMe: Boolean? = false,
    var fcmToken: String? = null,
    var accessToken: String? = null,
    var messengerId: String? = null,
    var calendarId: String? = null,
    var calendarUserId: String? = null,
    var userId: String? = null,
    var countError403: Int = 0
)

fun SessionDataModel.isLogged() = accessToken?.isNotEmpty() == true

fun SessionDataModel.add403(limit: Int, onOutOfLimit: () -> Unit) {
    countError403++
    if (countError403 >= limit) {
        onOutOfLimit.invoke()
        return
    }
}