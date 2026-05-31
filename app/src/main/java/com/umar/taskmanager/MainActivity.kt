package com.umar.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umar.taskmanager.domain.usecase.auth.ObserveCurrentUserIdUseCase
import com.umar.taskmanager.presentation.navigation.AppNavHost
import com.umar.taskmanager.presentation.navigation.Routes
import com.umar.taskmanager.ui.theme.TaskManagerTheme
import kotlinx.coroutines.flow.map
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppRoot()
                }
            }
        }
    }
}

private sealed interface SessionState {
    data object Loading : SessionState
    data class Resolved(val userId: Long?) : SessionState
}

@Composable
private fun AppRoot(
    observeCurrentUserIdUseCase: ObserveCurrentUserIdUseCase = koinInject()
) {
    val sessionFlow = remember {
        observeCurrentUserIdUseCase().map<Long?, SessionState> { SessionState.Resolved(it) }
    }
    val sessionState by sessionFlow.collectAsStateWithLifecycle(initialValue = SessionState.Loading)

    when (val state = sessionState) {
        SessionState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is SessionState.Resolved -> {
            val start = if (state.userId == null) Routes.Auth.routes else Routes.Task.routes
            AppNavHost(startDestination = start)
        }
    }
}