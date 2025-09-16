package com.vcompass.data.remote.services.message

import com.vcompass.data.remote.client.provideService
import com.vcompass.data.util.DataConstants
import org.koin.core.qualifier.named
import org.koin.dsl.module

val messageModule = module {
    single {
        provideService<FcmService>(get())
    }
}