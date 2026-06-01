package com.umar.taskmanager.di

import com.umar.taskmanager.data.notification.WorkManagerTaskReminderScheduler
import com.umar.taskmanager.data.repository.AuthRepositoryImpl
import com.umar.taskmanager.data.repository.CommentRepositoryImpl
import com.umar.taskmanager.data.repository.TaskRepositoryImpl
import com.umar.taskmanager.data.session.SessionManager
import com.umar.taskmanager.data.session.dataStore
import com.umar.taskmanager.domain.repository.AuthRepository
import com.umar.taskmanager.domain.repository.CommentRepository
import com.umar.taskmanager.domain.repository.TaskRepository
import com.umar.taskmanager.domain.scheduler.TaskReminderScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { androidContext().dataStore }
    single { SessionManager(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<TaskRepository> { TaskRepositoryImpl(get()) }
    single<CommentRepository> { CommentRepositoryImpl(get()) }
    single<TaskReminderScheduler> { WorkManagerTaskReminderScheduler(androidContext()) }
}