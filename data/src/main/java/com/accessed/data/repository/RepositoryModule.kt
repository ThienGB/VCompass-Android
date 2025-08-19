package com.accessed.data.repository

import com.accessed.data.repository.common.StatusLoginRepositoryImpl
import com.accessed.data.repository.login.LoginRepositoryImpl
import com.accessed.data.repository.schedule.ScheduleRepositoryImpl
import com.accessed.domain.repository.common.StatusLoginRepository
import com.accessed.domain.repository.login.LoginRepository
import com.accessed.domain.repository.schedule.ScheduleRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<LoginRepository> {
        LoginRepositoryImpl(get(), get(), get())
    }
    single<StatusLoginRepository> { StatusLoginRepositoryImpl(get()) }

    single<ScheduleRepository>{ ScheduleRepositoryImpl(get()) }
}