package com.umar.taskmanager.presentation.navigation

sealed class Routes(val routes: String) {
    data object Auth : Routes("auth")
    data object Task : Routes("task")
    data object TaskDetail : Routes("task_detail/{taskId}") {
        fun createRoute(taskId: Long) = "task_detail/{taskId}"
    }
}