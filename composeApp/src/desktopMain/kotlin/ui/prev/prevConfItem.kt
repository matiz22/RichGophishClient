package ui.prev

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import config.presentation.composables.ConfigItem
import configs.domain.model.GophishConfig
import java.io.ObjectInputFilter.Config

@Preview
@Composable
private fun prevConfItem() {
    MaterialTheme {
        val gophishConfig = GophishConfig(
            0, "CorpoXXX", 0, "https://github.com/matiz22", "TOP SECRET KEY"
        )
        ConfigItem(gophishConfig = gophishConfig)
    }

}