package ui.prev

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import config.presentation.screen.ListOfConfigsScreen
import configs.domain.model.ConfigsOrError
import configs.domain.model.GophishConfig

@Preview
@Composable
fun PrevListOfConfigs() {
    val gophishConfig = GophishConfig(
        0, "CorpoXXX", 0, "https://github.com/matiz22", "TOP SECRET KEY"
    )
    MaterialTheme {
        ListOfConfigsScreen(
            ConfigsOrError(
                configs = listOf(gophishConfig, gophishConfig),
            ), {}
        )
    }
}