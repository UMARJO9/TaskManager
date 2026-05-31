package com.umar.taskmanager.di

import com.umar.taskmanager.presentation.viewmodel.auth.AuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get(), get()) }
}