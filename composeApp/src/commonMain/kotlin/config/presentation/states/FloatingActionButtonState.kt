package config.presentation.states

import androidx.compose.ui.graphics.vector.ImageVector

data class FloatingActionButtonState(
    val action: () -> Unit,
    val icon: ImageVector
)
