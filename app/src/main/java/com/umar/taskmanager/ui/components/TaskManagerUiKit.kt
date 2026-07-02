package com.umar.taskmanager.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.umar.taskmanager.ui.theme.ActionDisabled
import com.umar.taskmanager.ui.theme.ActionPressed
import com.umar.taskmanager.ui.theme.ActionPrimary
import com.umar.taskmanager.ui.theme.LocalTmColors
import com.umar.taskmanager.ui.theme.OnAction
import com.umar.taskmanager.ui.theme.SystemError
import com.umar.taskmanager.ui.theme.SystemInfo
import com.umar.taskmanager.ui.theme.SystemSuccess
import com.umar.taskmanager.ui.theme.SystemWarning

object TmColors {
    val Background: Color @Composable @ReadOnlyComposable get() = LocalTmColors.current.background
    val Surface: Color @Composable @ReadOnlyComposable get() = LocalTmColors.current.surface
    val SurfaceSoft: Color @Composable @ReadOnlyComposable get() = LocalTmColors.current.surfaceSoft
    val Card: Color @Composable @ReadOnlyComposable get() = LocalTmColors.current.card
    val Line: Color @Composable @ReadOnlyComposable get() = LocalTmColors.current.line
    val Ink: Color @Composable @ReadOnlyComposable get() = LocalTmColors.current.ink
    val InkMuted: Color @Composable @ReadOnlyComposable get() = LocalTmColors.current.inkMuted
    val Hint: Color @Composable @ReadOnlyComposable get() = LocalTmColors.current.hint

    val Primary: Color = ActionPrimary
    val PrimaryPressed: Color = ActionPressed
    val PrimaryDisabled: Color = ActionDisabled
    val OnPrimary: Color = OnAction

    val Success: Color = SystemSuccess
    val Warning: Color = SystemWarning
    val Error: Color = SystemError
    val Info: Color = SystemInfo

    val Danger: Color = SystemError

    val PriorityHigh: Color = com.umar.taskmanager.ui.theme.PriorityHigh
    val PriorityMedium: Color = com.umar.taskmanager.ui.theme.PriorityMedium
    val PriorityLow: Color = com.umar.taskmanager.ui.theme.PriorityLow
    val PriorityCompleted: Color = com.umar.taskmanager.ui.theme.PriorityCompleted
}

object TmShapes {
    val Small = RoundedCornerShape(12.dp)
    val Medium = RoundedCornerShape(16.dp)
    val Large = RoundedCornerShape(20.dp)
    val Pill = RoundedCornerShape(999.dp)
}

@Composable
fun TmScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(TmColors.Background)
    ) {
        content()
    }
}

@Composable
fun TmTopBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        color = TmColors.Surface,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (navigationIcon != null) {
                navigationIcon()
                Spacer(Modifier.width(10.dp))
            }
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TmColors.Ink,
                maxLines = 1
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), content = actions)
        }
    }
}

@Composable
fun TmButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    variant: TmButtonVariant = TmButtonVariant.Primary
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val background = when (variant) {
        TmButtonVariant.Primary -> when {
            !enabled -> TmColors.PrimaryDisabled
            pressed -> TmColors.PrimaryPressed
            else -> TmColors.Primary
        }
        TmButtonVariant.Secondary -> TmColors.Surface
        TmButtonVariant.Danger -> TmColors.Danger
    }
    val contentColor = when (variant) {
        TmButtonVariant.Secondary -> TmColors.Ink
        else -> TmColors.OnPrimary
    }
    val disabledAlpha = if (enabled || variant == TmButtonVariant.Primary) 1f else 0.55f

    Row(
        modifier = modifier
            .height(52.dp)
            .alpha(disabledAlpha)
            .background(background, TmShapes.Medium)
            .then(
                if (variant == TmButtonVariant.Secondary) {
                    Modifier.border(1.dp, TmColors.Line, TmShapes.Medium)
                } else {
                    Modifier
                }
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled && !isLoading,
                onClick = onClick
            )
            .padding(horizontal = 18.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(18.dp),
                strokeWidth = 2.dp,
                color = contentColor
            )
        }
        Text(
            text = text,
            color = contentColor,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

enum class TmButtonVariant {
    Primary, Secondary, Danger
}

@Composable
fun TmTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeholder: String? = null,
    singleLine: Boolean = false,
    minLines: Int = 1,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(
        modifier = modifier.alpha(if (enabled) 1f else 0.55f),
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = TmColors.Ink
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(TmColors.Surface, TmShapes.Medium)
                .border(1.dp, TmColors.Line, TmShapes.Medium)
                .padding(horizontal = 14.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            leadingIcon?.invoke()
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                enabled = enabled,
                textStyle = MaterialTheme.typography.bodyLarge.merge(
                    TextStyle(color = TmColors.Ink)
                ),
                cursorBrush = SolidColor(TmColors.Primary),
                singleLine = singleLine,
                minLines = minLines,
                maxLines = maxLines,
                visualTransformation = visualTransformation,
                decorationBox = { innerTextField ->
                    if (value.isEmpty() && placeholder != null) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyLarge,
                            color = TmColors.Hint
                        )
                    }
                    innerTextField()
                }
            )
            trailingIcon?.invoke()
        }
    }
}

@Composable
fun TmIconButton(
    imageVector: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    tint: Color = TmColors.InkMuted,
    containerColor: Color = TmColors.SurfaceSoft
) {
    Box(
        modifier = modifier
            .size(42.dp)
            .alpha(if (enabled) 1f else 0.5f)
            .background(containerColor, TmShapes.Pill)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Composable
fun TmChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val background = if (selected) TmColors.Primary else TmColors.Surface
    val contentColor = if (selected) TmColors.OnPrimary else TmColors.InkMuted
    Surface(
        modifier = modifier
            .alpha(if (enabled) 1f else 0.5f)
            .clickable(enabled = enabled, onClick = onClick),
        shape = TmShapes.Pill,
        color = background,
        border = BorderStroke(1.dp, if (selected) TmColors.Primary else TmColors.Line)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
            color = contentColor,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun TmBottomBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        color = TmColors.Surface,
        shadowElevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Composable
fun RowScope.TmBottomBarItem(
    selected: Boolean,
    label: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .background(if (selected) TmColors.SurfaceSoft else Color.Transparent, TmShapes.Pill)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) TmColors.Primary else TmColors.InkMuted
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) TmColors.Primary else TmColors.InkMuted,
            maxLines = 1
        )
    }
}

@Composable
fun TmFab(
    imageVector: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = modifier
            .size(58.dp)
            .background(
                if (pressed) TmColors.PrimaryPressed else TmColors.Primary,
                TmShapes.Large
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = TmColors.OnPrimary
        )
    }
}

@Composable
fun TmPanel(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = TmShapes.Medium,
        color = TmColors.Card,
        shadowElevation = 3.dp,
        content = content
    )
}

@Composable
fun TmConfirmDialog(
    title: String,
    message: String,
    confirmText: String,
    dismissText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        TmPanel(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TmColors.Ink
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TmColors.InkMuted
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TmButton(
                        text = dismissText,
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        variant = TmButtonVariant.Secondary
                    )
                    TmButton(
                        text = confirmText,
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f),
                        variant = TmButtonVariant.Danger
                    )
                }
            }
        }
    }
}

@Composable
fun TmLoading(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier,
        color = TmColors.Primary,
        strokeWidth = 3.dp
    )
}

@Composable
fun string(@StringRes resId: Int): String = stringResource(resId)
