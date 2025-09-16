package com.vcompass.presentation.di

import com.vcompass.presentation.event.eventModule
import com.vcompass.presentation.viewmodel.viewModelModule

val presentationModule = listOf(
    eventModule,
    viewModelModule,
)
