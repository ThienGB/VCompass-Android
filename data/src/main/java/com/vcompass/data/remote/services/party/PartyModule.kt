package com.vcompass.data.remote.services.party

import com.vcompass.data.remote.client.provideService
import com.vcompass.data.util.DataConstants
import org.koin.core.qualifier.named
import org.koin.dsl.module

val partyModule = module {
    single {
        provideService<AuthService>(get(named(DataConstants.AUTH_SERVICE)))
    }
    single {
        provideService<UserInfoService>(get(named(DataConstants.AUTH_SERVICE)))
    }
}