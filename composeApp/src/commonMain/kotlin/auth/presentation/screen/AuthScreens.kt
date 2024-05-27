package auth.presentation.screen

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import auth.presentation.components.AuthComponent
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.matiz22.richgophishclient.AppRes
import config.presentation.events.ScaffoldEvents

@Composable
fun AuthScreens(authComponent: AuthComponent) {
    val childStack by authComponent.childStack.subscribeAsState()
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = authComponent.snackbarHostState)
        }
    ){
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { child ->
            when (val instance = child.instance) {
                is AuthComponent.Child.RegistrationComponentChild -> {
                    val userState = instance.component.authFormState
                    val apiCallResultChannel = instance.component.apiCallResult
                    LaunchedEffect(apiCallResultChannel) {
                        apiCallResultChannel.collect { result ->
                            if (result.errorMessage != null) {
                                authComponent.showAuthResult(result.errorMessage!!)
                            }
                        }
                    }
                    RegistrationScreen(
                        email = userState.email,
                        emailError = userState.emailError,
                        password = userState.password,
                        passwordError = userState.passwordError,
                        onEvent = { authEvent ->
                            instance.component.onEvent(authEvent)
                        }
                    )
                }

                is AuthComponent.Child.LoginComponentChild -> {
                    val userState = instance.component.authFormState
                    val apiCallResultChannel = instance.component.apiCallResult
                    LaunchedEffect(apiCallResultChannel) {
                        apiCallResultChannel.collect { result ->
                            if (result.errorMessage != null) {
                                authComponent.showAuthResult(result.errorMessage!!)
                            }
                        }
                    }
                    LoginScreen(
                        email = userState.email,
                        emailError = userState.emailError,
                        password = userState.password,
                        passwordError = userState.passwordError,
                        onEvent = { authEvent ->
                            instance.component.onEvent(authEvent)
                        }
                    )
                }
            }
        }
    }
}