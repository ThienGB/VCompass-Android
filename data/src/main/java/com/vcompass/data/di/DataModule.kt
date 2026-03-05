package com.vcompass.data.di

import com.vcompass.data.local.localStorage
import com.vcompass.data.remote.client.clientModule
import com.vcompass.data.remote.services.serviceModule
import com.vcompass.data.repository.repositoryModule

val dataModule = listOf(
    localStorage,
    clientModule,
    repositoryModule,
    serviceModule
)