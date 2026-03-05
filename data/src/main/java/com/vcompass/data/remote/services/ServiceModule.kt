package com.vcompass.data.remote.services

import com.vcompass.data.remote.client.provideService
import org.koin.core.qualifier.named
import org.koin.dsl.module

val serviceModule = module {
    single {
        provideService<UserInfoService>(get())
    }

    single {
        provideService<AuthService>(get(named("auth_retrofit")))
    }

    single {
        provideService<ScheduleService>(get())
    }
    single {
        provideService<AccommodationService>(get())
    }
    single {
        provideService<AttractionService>(get())
    }
    single {
        provideService<FoodPlaceService>(get())
    }
}