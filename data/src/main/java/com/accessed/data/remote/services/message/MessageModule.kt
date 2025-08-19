package com.accessed.data.remote.services.message

import com.accessed.data.remote.client.provideService
import com.accessed.data.util.DataConstants
import org.koin.core.qualifier.named
import org.koin.dsl.module

val messageModule = module {
    single {
        provideService<FcmService>(get())
    }
}