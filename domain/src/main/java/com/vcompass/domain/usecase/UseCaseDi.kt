package com.vcompass.domain.usecase

import com.vcompass.domain.usecase.common.GetSessionDataUseCase
import com.vcompass.domain.usecase.common.SetOpenedAppUseCase
import com.vcompass.domain.usecase.login.LoginGoogleUseCase
import com.vcompass.domain.usecase.login.LoginUseCase
import com.vcompass.domain.usecase.login.LogoutUseCase
import org.koin.dsl.module

val useCaseModule = module {
    //======= LOGIN =======//
    factory { LoginUseCase(get()) }
    factory { LoginGoogleUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { GetSessionDataUseCase(get()) }
    factory { SetOpenedAppUseCase(get()) }
}