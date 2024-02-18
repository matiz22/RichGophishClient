import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import auth.presentation.components.AuthScreenComponent
import auth.presentation.screen.AuthScreen
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import config.presentation.screen.ConfigScreen
import navigation.RootComponent
import ui.SchemeResolver


@Composable
fun App(root: RootComponent) {
    val childStack by root.childStack.subscribeAsState()
    MaterialTheme(
        colorScheme = SchemeResolver.provideColorScheme(isSystemInDarkTheme())
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Children(
                stack = childStack,
                animation = stackAnimation(slide())
            ) { child ->
                when (val instance = child.instance) {

                    is RootComponent.Child.AuthScreen -> {
                        val userState = instance.component.authFormState
                        AuthScreen(
                            email = userState.email,
                            emailError = userState.emailError,
                            password = userState.password,
                            passwordError = userState.passwordError,
                            onEvent = {
                                instance.component.onEvent(it)
                            }
                        )
                    }

                    is RootComponent.Child.ConfigScreen -> {
                        ConfigScreen(instance.component)
                    }
                }
            }
        }
    }
}