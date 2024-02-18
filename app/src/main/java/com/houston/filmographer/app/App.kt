package com.houston.filmographer.app

import android.app.Application
import com.houston.filmographer.di.dataModule
import com.houston.filmographer.di.interactorModule
import com.houston.filmographer.di.repositoryModule
import com.houston.filmographer.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(viewModelModule, interactorModule, repositoryModule, dataModule)
        }
    }
}