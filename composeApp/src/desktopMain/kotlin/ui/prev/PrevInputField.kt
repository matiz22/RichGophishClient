package ui.prev

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import auth.presentation.composables.InputField

@Preview
@Composable
fun PrevInputField() {
    MaterialTheme {
        InputField(Modifier, "test", {}, minLines = 20)
    }
}