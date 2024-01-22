package ui

import DarkColors
import LightColors
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

actual class SchemeResolver {
    actual companion object : ProvideDefaultColorScheme{
        @Composable
        override fun provideColorScheme(useDarkTheme: Boolean): ColorScheme {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val context = LocalContext.current
                if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            } else {
                if (useDarkTheme) {
                    DarkColors
                } else {
                    LightColors
                }
            }
        }
    }
}