package com.umar.taskmanager

import android.app.Application
import com.umar.taskmanager.data.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TaskManagerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TaskManagerApp)
            modules(databaseModule)
        }
    }
}