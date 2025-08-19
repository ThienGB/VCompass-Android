package com.accessed.domain.usecase

import com.accessed.domain.usecase.common.GetSessionDataUseCase
import com.accessed.domain.usecase.common.SetOpenedAppUseCase
import com.accessed.domain.usecase.login.LoginUseCase
import com.accessed.domain.usecase.login.LogoutUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { LoginUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { GetSessionDataUseCase(get()) }
    factory { SetOpenedAppUseCase(get()) }
}