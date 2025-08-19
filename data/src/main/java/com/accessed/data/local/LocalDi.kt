package com.accessed.data.local

import org.koin.dsl.module

val localStorage = module {
    single { SecureStorage(get()) }
    single { SecureStorageHelper(get()) }
}