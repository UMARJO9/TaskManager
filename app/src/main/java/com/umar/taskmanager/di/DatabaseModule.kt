package com.umar.taskmanager.di

import androidx.room.Room
import com.umar.taskmanager.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "task_manager.db"
        )
            .addMigrations(AppDatabase.MIGRATION_1_2, AppDatabase.MIGRATION_2_3)
            .build()
    }

    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().taskDao() }
    single { get<AppDatabase>().commentDao() }
}
