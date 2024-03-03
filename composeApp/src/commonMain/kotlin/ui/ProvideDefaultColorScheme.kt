package ui

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import theme.DarkColors
import theme.LightColors

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