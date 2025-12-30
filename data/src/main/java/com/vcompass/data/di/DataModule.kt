package com.vcompass.data.di

import com.vcompass.data.local.localStorage
import com.vcompass.data.remote.client.clientModule
import com.vcompass.data.remote.services.calendar.calendarModule
import com.vcompass.data.remote.services.job.jobModule
import com.vcompass.data.remote.services.message.messageModule
import com.vcompass.data.remote.services.auth.partyModule
import com.vcompass.data.remote.services.schedule.scheduleModule
import com.vcompass.data.repository.repositoryModule

val dataModule = listOf(
    localStorage,
    clientModule,
    repositoryModule,
    partyModule,
    messageModule,
    calendarModule,
    jobModule,
    scheduleModule
)