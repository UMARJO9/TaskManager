package com.umar.taskmanager.presentation.screen.task.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.umar.taskmanager.R
import com.umar.taskmanager.ui.components.TmColors
import com.umar.taskmanager.ui.components.TmIconButton
import com.umar.taskmanager.ui.components.TmLoading
import com.umar.taskmanager.ui.components.TmTextField

@Composable
fun CommentInputBar(
    value: String,
    isSending: Boolean,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars.union(WindowInsets.ime))
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TmTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            label = stringResource(R.string.comment_placeholder),
            enabled = !isSending,
            maxLines = 4
        )
        if (isSending) {
            TmLoading()
        } else {
            TmIconButton(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = stringResource(R.string.action_send),
                onClick = onSend,
                enabled = !isSending,
                tint = TmColors.Primary
            )
        }
    }
}
