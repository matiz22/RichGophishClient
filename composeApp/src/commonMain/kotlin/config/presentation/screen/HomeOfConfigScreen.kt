package config.presentation.screen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeOfConfigScreen(previous: () -> Unit) {
    Button(
        onClick = {
            previous()
        },
        content = {
            Text("heeere")
        }
    )
}