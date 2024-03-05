package config.presentation.screen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.matiz22.richgophishclient.AppRes
import root.presentation.composables.AppScaffold


@Composable
fun ListOfConfigsScreen(next: () ->Unit) {
    Button(
        onClick = {
            next()
        },
        content = {
            Text("heeere")
        }
    )
}
