package auth.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import auth.presentation.composables.InputField
import auth.presentation.events.AuthEvent
import com.matiz22.richgophishclient.AppRes


@Composable
fun RegistrationScreen(
    email: String,
    emailError: String?,
    password: String,
    passwordError: String?,
    onEvent: (AuthEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(16.dp)
    ) {

        item {
            InputField(
                text = email,
                onValueChange = {
                    onEvent(AuthEvent.EmailChanged(it))
                },
                hintText = AppRes.string.email_hint,
                errorMessage = emailError,
                minLines = 20,
            )
        }

        item {
            InputField(
                text = password,
                onValueChange = {
                    onEvent(AuthEvent.PasswordChanged(it))
                },
                hintText = AppRes.string.password_hint,
                errorMessage = passwordError,
                isPassword = true,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                minLines = 20
            )
        }

        item {
            OutlinedButton(
                onClick = {
                    onEvent(AuthEvent.IsNewUserChanged)
                },
                content = {
                    Text(
                        text = AppRes.string.nav_to_login,
                    )
                }
            )
        }

        item {
            Button(
                onClick = {
                    onEvent(AuthEvent.Submit)
                },
                content = {
                    Text(
                        text = AppRes.string.submit_button,
                    )
                }
            )
        }
    }
}