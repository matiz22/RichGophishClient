package ui.prev

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import config.presentation.screen.ListOfConfigsScreen

@Preview
@Composable
fun PrevListOfConfigs(){
    MaterialTheme {
        ListOfConfigsScreen()
    }
}