package config.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.matiz22.richgophishclient.AppRes
import config.presentation.composables.ConfigItem
import config.presentation.events.ListOfConfigsEvent
import configs.domain.model.ConfigsOrError
import root.presentation.composables.AppScaffold


@Composable
fun ListOfConfigsScreen(
    configsOrError: ConfigsOrError,
    onEvent: (ListOfConfigsEvent) -> Unit
) {

    if (configsOrError.configs != null) {
        LazyVerticalGrid(
            contentPadding = PaddingValues(4.dp),
            columns = GridCells.FixedSize(500.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
                Button(onClick = {onEvent(ListOfConfigsEvent.AddConfig)}){

                }
            }
            items(configsOrError.configs!!) { config ->
                ConfigItem(gophishConfig = config)
            }
        }
    }
}
