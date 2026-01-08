package com.vcompass.domain.usecase

import com.vcompass.domain.usecase.common.GetSessionDataUseCase
import com.vcompass.domain.usecase.common.SetOpenedAppUseCase
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
}