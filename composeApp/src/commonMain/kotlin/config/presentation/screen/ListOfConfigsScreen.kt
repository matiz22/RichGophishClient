package config.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import config.presentation.composables.ConfigForm
import config.presentation.composables.ConfigItem
import config.presentation.events.ListOfConfigsEvent
import config.presentation.states.ConfigFormState
import configs.domain.model.ConfigsOrError
import configs.domain.model.GophishConfig


@Composable
fun ListOfConfigsScreen(
    configsOrError: ConfigsOrError,
    onEvent: (ListOfConfigsEvent) -> Unit,
    navigate: (GophishConfig) -> Unit,
    configFormState: ConfigFormState
) {
    if (configFormState.isShown) {
        ConfigForm(
            configFormState,
            onEvent = onEvent
        )
    }
    if (configsOrError.configs != null) {
        LazyVerticalGrid(
            contentPadding = PaddingValues(4.dp),
            columns = GridCells.FixedSize(500.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(
                items = configsOrError.configs!!,
                key = {
                    it.id
                }
            ) { config ->
                ConfigItem(gophishConfig = config, onMenuClick = onEvent, onClick = navigate)
            }
        }
    }
}
