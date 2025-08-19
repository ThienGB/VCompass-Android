package com.accessed.data.remote.services.party

import com.accessed.data.remote.client.provideService
import com.accessed.data.util.DataConstants
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