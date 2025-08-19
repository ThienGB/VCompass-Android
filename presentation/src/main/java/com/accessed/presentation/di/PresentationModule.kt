package com.accessed.presentation.di

import com.accessed.presentation.event.eventModule
import com.accessed.presentation.viewmodel.viewModelModule

val presentationModule = listOf(
    eventModule,
    viewModelModule,
)
