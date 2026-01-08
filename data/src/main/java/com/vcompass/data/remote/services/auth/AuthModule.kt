package com.vcompass.data.remote.services.auth

import com.vcompass.data.remote.client.provideService
import org.koin.core.qualifier.named
import org.koin.dsl.module

val partyModule = module {
    single {
        provideService<UserInfoService>(get())
    }

    single {
        provideService<AuthService>(get(named("auth_retrofit")))
    }
}