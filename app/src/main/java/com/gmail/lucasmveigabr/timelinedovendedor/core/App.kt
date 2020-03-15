package com.gmail.lucasmveigabr.timelinedovendedor.core

import android.app.Application
import com.gmail.lucasmveigabr.timelinedovendedor.di.firebaseModule
import com.gmail.lucasmveigabr.timelinedovendedor.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(firebaseModule, viewModelModule)
        }
    }

}