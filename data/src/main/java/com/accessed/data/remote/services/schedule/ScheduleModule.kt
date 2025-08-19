package com.accessed.data.remote.services.schedule

import com.accessed.data.remote.client.provideService
import com.accessed.data.remote.services.message.FcmService
import com.accessed.data.util.DataConstants
import org.koin.core.qualifier.named
import org.koin.dsl.module

val scheduleModule = module {
    single {
        provideService<ScheduleService>(get())
    }
}