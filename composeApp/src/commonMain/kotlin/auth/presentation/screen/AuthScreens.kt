package auth.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import auth.presentation.components.AuthComponent
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
fun AuthScreens(authComponent: AuthComponent) {
    val childStack by authComponent.childStack.subscribeAsState()
    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {
            is AuthComponent.Child.RegistrationComponentChild -> {
                val userState = instance.component.authFormState
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