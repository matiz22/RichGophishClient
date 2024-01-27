package auth.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import auth.presentation.components.AuthScreenComponent
import auth.presentation.composables.InputField
import auth.presentation.events.AuthEvent
import com.matiz22.richgophishclient.AppRes


@Composable
fun AuthScreen(component: AuthScreenComponent) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(AppRes.string.action_skip)
//        InputField(
//            text =
//        )
    }
}