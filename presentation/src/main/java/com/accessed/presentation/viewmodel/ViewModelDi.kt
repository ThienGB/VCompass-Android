package com.accessed.presentation.viewmodel

import com.accessed.presentation.viewmodel.home.HomeViewModel
import com.accessed.presentation.viewmodel.login.LoginViewModel
import com.accessed.presentation.viewmodel.splash.IntroduceViewModel
import com.accessed.presentation.viewmodel.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SplashViewModel(get(), get(), get())
    }
    viewModel {
        IntroduceViewModel(get(), get(), get())
    }
    viewModel {
        HomeViewModel(get(), get(), get())
    }
    viewModel {
        LoginViewModel(get(), get(), get())
    }
}