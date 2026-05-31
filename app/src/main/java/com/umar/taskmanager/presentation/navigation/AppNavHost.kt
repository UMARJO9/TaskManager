package com.umar.taskmanager.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.umar.taskmanager.presentation.screen.auth.AuthScreen


@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Routes.Auth.routes) {
            AuthScreen(onLoggedIn = {
                navController.navigate(Routes.Task.routes) {
                    popUpTo(Routes.Auth.routes) { inclusive = true }
                }
            })
        }

    }
}