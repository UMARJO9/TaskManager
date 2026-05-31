package com.umar.taskmanager.di

import com.umar.taskmanager.presentation.viewmodel.auth.AuthViewModel
import com.umar.taskmanager.presentation.viewmodel.task.AddTaskViewModel
import com.umar.taskmanager.presentation.viewmodel.task.TaskDetailViewModel
import com.umar.taskmanager.presentation.viewmodel.task.TasksViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get(), get()) }
    viewModel { TasksViewModel(get(), get(), get(), get()) }
    viewModel { (taskId: Long) ->
        AddTaskViewModel(taskId, get(), get(), get(), get())
    }
    viewModel { (taskId: Long) ->
        TaskDetailViewModel(taskId, get(), get(), get(), get())
    }
}