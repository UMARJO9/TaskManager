package com.umar.taskmanager.presentation.navigation

sealed class Routes(val routes: String) {
    data object Auth : Routes("auth")
    data object Task : Routes("task")
    data object AddTask : Routes("add_task")
    data object EditTask : Routes("edit_task/{taskId}") {
        const val ARG_TASK_ID = "taskId"
        fun createRoute(taskId: Long) = "edit_task/$taskId"
    }
    data object TaskDetail : Routes("task_detail/{taskId}") {
        const val ARG_TASK_ID = "taskId"
        fun createRoute(taskId: Long) = "task_detail/$taskId"
    }
}