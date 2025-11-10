package com.vcompass.presentation.di

import com.vcompass.data.di.dataModule
import com.vcompass.domain.di.domainModule
import com.vcompass.presentation.event.eventModule
import com.vcompass.presentation.viewmodel.viewModelModule

val presentationModule = listOf(
    eventModule,
    viewModelModule
) + domainModule + dataModule
