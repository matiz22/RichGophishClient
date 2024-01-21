package ui

import DarkColors
import LightColors
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

interface ProvideColorScheme {
    @Composable
    fun provideColorScheme(
        useDarkTheme: Boolean = isSystemInDarkTheme(),
    ): ColorScheme {
        return if (!useDarkTheme) {
            LightColors
        } else {
            DarkColors
        }
    }
}