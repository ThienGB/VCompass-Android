package com.vcompass.data.local

import ActiveScheduleStore
import org.koin.dsl.module

val localStorage = module {
    single { ActiveScheduleStore(get()) }
    single { SecureStorage(get()) }
    single { SecureStorageHelper(get()) }
}