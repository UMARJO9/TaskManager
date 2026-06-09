package com.umar.taskmanager.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.umar.taskmanager.presentation.screen.auth.AuthScreen
import com.umar.taskmanager.presentation.screen.task.AddTaskScreen
import com.umar.taskmanager.presentation.screen.task.TaskDetailScreen
import com.umar.taskmanager.presentation.screen.task.TasksScreen


@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {
    fun navigateBackToTasks() {
        if (!navController.popBackStack()) {
            navController.navigate(Routes.Task.route) {
                popUpTo(0)
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Routes.Auth.route) {
            AuthScreen(onLoggedIn = {
                navController.navigate(Routes.Task.route) {
                    popUpTo(Routes.Auth.route) { inclusive = true }
                }
            })
        }

        composable(Routes.Task.route) {
            TasksScreen(
                onAddTask = { navController.navigate(Routes.AddTask.route) },
                onOpenTask = { task ->
                    navController.navigate(Routes.TaskDetail.createRoute(task.id))
                },
                onLoggedOut = {
                    navController.navigate(Routes.Auth.route) {
                        popUpTo(Routes.Task.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.AddTask.route) {
            AddTaskScreen(
                onSaved = { navigateBackToTasks() },
                onBack = { navigateBackToTasks() }
            )
        }

        composable(
            route = Routes.EditTask.route,
            arguments = listOf(
                navArgument(Routes.EditTask.ARG_TASK_ID) { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong(Routes.EditTask.ARG_TASK_ID) ?: 0L
            AddTaskScreen(
                taskId = taskId,
                onSaved = { navigateBackToTasks() },
                onBack = { navigateBackToTasks() }
            )
        }

        composable(
            route = Routes.TaskDetail.route,
            arguments = listOf(
                navArgument(Routes.TaskDetail.ARG_TASK_ID) { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong(Routes.TaskDetail.ARG_TASK_ID) ?: 0L
            TaskDetailScreen(
                taskId = taskId,
                onEdit = { navController.navigate(Routes.EditTask.createRoute(taskId)) },
                onBack = { navigateBackToTasks() }
            )
        }
    }
}
