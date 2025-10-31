package com.vcompass.domain.di

import com.vcompass.data.di.dataModule
import com.vcompass.domain.usecase.useCaseModule

val domainModule = listOf(
    useCaseModule
) + dataModule