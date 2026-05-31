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

        composable(Routes.Task.routes) {
            TasksScreen(
                onAddTask = { navController.navigate(Routes.AddTask.routes) },
                onOpenTask = { task ->
                    navController.navigate(Routes.TaskDetail.createRoute(task.id))
                },
                onLoggedOut = {
                    navController.navigate(Routes.Auth.routes) {
                        popUpTo(Routes.Task.routes) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.AddTask.routes) {
            AddTaskScreen(
                onSaved = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.EditTask.routes,
            arguments = listOf(
                navArgument(Routes.EditTask.ARG_TASK_ID) { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong(Routes.EditTask.ARG_TASK_ID) ?: 0L
            AddTaskScreen(
                taskId = taskId,
                onSaved = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.TaskDetail.routes,
            arguments = listOf(
                navArgument(Routes.TaskDetail.ARG_TASK_ID) { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong(Routes.TaskDetail.ARG_TASK_ID) ?: 0L
            TaskDetailScreen(
                taskId = taskId,
                onEdit = { navController.navigate(Routes.EditTask.createRoute(taskId)) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}