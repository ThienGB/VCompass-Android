package com.vcompass.data.remote.services.auth

import com.vcompass.data.remote.client.provideService
import com.vcompass.data.util.DataConstants
import org.koin.core.qualifier.named
import org.koin.dsl.module

val partyModule = module {
    single {
        provideService<AuthService>(get())
    }
    single {
        provideService<UserInfoService>(get())
    }
}