package com.vcompass.presentation.viewmodel.profile

import com.vcompass.presentation.event.global.GlobalConfig
import com.vcompass.presentation.event.global.GlobalEventBus
import com.vcompass.presentation.model.user.User
import com.vcompass.presentation.model.user.toUser
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.util.collectToState
import com.vcompass.presentation.viewmodel.BaseViewModel

class UserProfileViewModel(
    globalEventBus: GlobalEventBus,
    globalConfig: GlobalConfig,
) : BaseViewModel(globalEventBus, globalConfig) {
    val currentUser = globalConfig.getSessionData()?.currentUser?.toUser() ?: User()

    fun logout() {
        globalConfig.userLogout()
    }
}