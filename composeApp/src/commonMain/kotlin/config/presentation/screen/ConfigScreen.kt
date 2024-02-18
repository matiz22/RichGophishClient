package config.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import config.presentation.components.ConfigScreenComponent


@Composable
fun ConfigScreen(component: ConfigScreenComponent) {
    Text(component.user.toString())
}