package ui

import DarkColors
import LightColors
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

interface ProvideDefaultColorScheme {
    @Composable
    fun provideColorScheme(
        useDarkTheme: Boolean,
    ): ColorScheme {
        return if (!useDarkTheme) {
            LightColors
        } else {
            DarkColors
        }
    }
}