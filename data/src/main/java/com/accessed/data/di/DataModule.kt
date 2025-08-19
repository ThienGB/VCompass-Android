package com.accessed.data.di

import com.accessed.data.local.localStorage
import com.accessed.data.remote.client.clientModule
import com.accessed.data.remote.services.calendar.calendarModule
import com.accessed.data.remote.services.job.jobModule
import com.accessed.data.remote.services.message.messageModule
import com.accessed.data.remote.services.party.partyModule
import com.accessed.data.remote.services.schedule.scheduleModule
import com.accessed.data.repository.repositoryModule

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