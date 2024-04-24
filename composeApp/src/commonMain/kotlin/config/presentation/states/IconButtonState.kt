package config.presentation.states

import androidx.compose.ui.graphics.vector.ImageVector

data class IconButtonState(
    val action: () -> Unit,
    val icon: ImageVector
)
