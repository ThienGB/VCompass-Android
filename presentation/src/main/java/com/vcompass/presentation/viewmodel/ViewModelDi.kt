package com.vcompass.presentation.viewmodel

import com.vcompass.presentation.viewmodel.home.HomeViewModel
import com.vcompass.presentation.viewmodel.login.LoginViewModel
import com.vcompass.presentation.viewmodel.schedule.ScheduleViewModel
import com.vcompass.presentation.viewmodel.splash.IntroduceViewModel
import com.vcompass.presentation.viewmodel.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(get(), get())
    }
    viewModel {
        SplashViewModel(get(), get(), get())
    }
    viewModel {
        IntroduceViewModel(get(), get(), get())
    }
    viewModel {
        HomeViewModel(get(), get())
    }
    viewModel {
        LoginViewModel(get(), get(), get(),get())
    }
    viewModel { ScheduleViewModel(get(), get()) }
}