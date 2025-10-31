package com.example.vcompass

import android.app.Application
import com.vcompass.data.di.dataModule
import com.vcompass.domain.di.domainModule
import com.vcompass.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(
                dataModule + domainModule + presentationModule
            )
        }
    }
}