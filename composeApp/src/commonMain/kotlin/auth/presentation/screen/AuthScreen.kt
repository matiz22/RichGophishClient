package auth.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import auth.presentation.components.AuthScreenComponent
import auth.presentation.events.AuthEvent


@Composable
fun AuthScreen(component: AuthScreenComponent) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField("", onValueChange = {})

        Button(onClick = {
            component.onEvent(AuthEvent.Submit)
        }) {
            Text("Test")
        }
    }
}