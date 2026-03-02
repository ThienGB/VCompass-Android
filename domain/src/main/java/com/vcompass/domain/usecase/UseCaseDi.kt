package com.vcompass.domain.usecase

import com.vcompass.domain.usecase.common.GetSessionDataUseCase
import com.vcompass.domain.usecase.common.SetOpenedAppUseCase
import com.vcompass.domain.usecase.location.GetCurrentLocationUseCase
import com.vcompass.domain.usecase.location.GetLocationFromLatLngUseCase
import com.vcompass.domain.usecase.location.ObserveSelectedLocationUseCase
import com.vcompass.domain.usecase.location.SetCurrentLocationUseCase
import com.vcompass.domain.usecase.location.SetSearchLocationUseCase
import com.vcompass.domain.usecase.login.LoginGoogleUseCase
import com.vcompass.domain.usecase.login.LoginUseCase
import com.vcompass.domain.usecase.schedule.GetAllSchedulesUseCase
import com.vcompass.domain.usecase.schedule.GetScheduleDetailUseCase
import org.koin.dsl.module

val useCaseModule = module {
    //======= LOGIN =======//
    factory { LoginUseCase(get()) }
    factory { LoginGoogleUseCase(get()) }
    factory { GetSessionDataUseCase(get()) }
    factory { SetOpenedAppUseCase(get()) }

    //======= SCHEDULE =======//
    factory { GetScheduleDetailUseCase(get()) }
    factory { GetAllSchedulesUseCase(get()) }

    //======= LOCATION =======//
    factory { ObserveSelectedLocationUseCase(get()) }
    factory { SetCurrentLocationUseCase(get()) }
    factory { SetSearchLocationUseCase(get()) }
    factory { GetLocationFromLatLngUseCase(get()) }
    factory { GetCurrentLocationUseCase(get()) }
}