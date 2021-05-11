package com.example.capstoneproject

import android.app.Application
import com.example.capstoneproject.di.useCaseModule
import com.example.capstoneproject.di.viewModelModule
import com.example.core.di.databaseModule
import com.example.core.di.networkModule
import com.example.core.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@Suppress("unused")
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApplication)
            modules(useCaseModule, viewModelModule, databaseModule, networkModule, repositoryModule)
        }
    }
}