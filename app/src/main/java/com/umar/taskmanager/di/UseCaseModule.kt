package com.umar.taskmanager.di

import com.umar.taskmanager.domain.usecase.auth.LoginUseCase
import com.umar.taskmanager.domain.usecase.auth.LogoutUseCase
import com.umar.taskmanager.domain.usecase.auth.ObserveCurrentUserIdUseCase
import com.umar.taskmanager.domain.usecase.auth.RegisterUseCase
import com.umar.taskmanager.domain.usecase.comment.AddCommentUseCase
import com.umar.taskmanager.domain.usecase.comment.ObserveCommentsUseCase
import com.umar.taskmanager.domain.usecase.task.AddTaskUseCase
import com.umar.taskmanager.domain.usecase.task.DeleteTaskUseCase
import com.umar.taskmanager.domain.usecase.task.GetTaskUseCase
import com.umar.taskmanager.domain.usecase.task.ObserveUserTasksUseCase
import com.umar.taskmanager.domain.usecase.task.UpdateTaskUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory { RegisterUseCase(get(), get()) }
    factory { LoginUseCase(get(), get()) }
    factory { LogoutUseCase(get()) }
    factory { ObserveCurrentUserIdUseCase(get()) }


    factory { GetTaskUseCase(get()) }
    factory { AddTaskUseCase(get(), get()) }
    factory { UpdateTaskUseCase(get(), get()) }
    factory { DeleteTaskUseCase(get(), get()) }
    factory { ObserveUserTasksUseCase(get()) }

    factory { AddCommentUseCase(get()) }
    factory { ObserveCommentsUseCase(get()) }


}