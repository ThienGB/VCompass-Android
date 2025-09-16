package com.vcompass.data.remote.services.schedule

import com.vcompass.data.remote.client.provideService
import com.vcompass.data.remote.services.message.FcmService
import com.vcompass.data.util.DataConstants
import org.koin.core.qualifier.named
import org.koin.dsl.module

val scheduleModule = module {
    single {
        provideService<ScheduleService>(get())
    }
}