package auth.presentation.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import auth.domain.model.User
import auth.presentation.navigation.AuthScreensConfiguration
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.items
import com.arkivanov.decompose.router.stack.replaceAll
import config.presentation.events.ScaffoldEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class AuthComponent(
    componentContext: ComponentContext,
    val mainCoroutineContext: CoroutineContext,
    private val navigateToConfig: (User) -> Unit,
) : ComponentContext by componentContext {

    val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val navigation = StackNavigation<AuthScreensConfiguration>()
    val childStack = childStack(
        source = navigation,
        serializer = AuthScreensConfiguration.serializer(),
        initialConfiguration = AuthScreensConfiguration.RegistrationConfiguration,
        handleBackButton = true,
        childFactory = ::createChild
    )

    var snackbarHostState by mutableStateOf(SnackbarHostState())

    fun showAuthResult(message:String) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    private fun createChild(
        config: AuthScreensConfiguration,
        context: ComponentContext
    ): Child {
        return when (config) {
            is AuthScreensConfiguration.RegistrationConfiguration -> Child.RegistrationComponentChild(
                component = RegistrationComponent(componentContext = context,
                    mainCoroutineContext = mainCoroutineContext,
                    navigateToConfig = { user ->
                        navigateToConfig(user)
                    },
                    navigateToLogin = {
                        navigation.replaceAll(AuthScreensConfiguration.LoginConfiguration)
                    }
                )
            )

            is AuthScreensConfiguration.LoginConfiguration -> Child.LoginComponentChild(
                component = LoginComponent(
                    componentContext = context,
                    mainCoroutineContext = mainCoroutineContext,
                    navigateToConfig = { user ->
                        navigateToConfig(user)
                    },
                    navigateToRegister = {
                        navigation.replaceAll(AuthScreensConfiguration.RegistrationConfiguration)
                    }
                )
            )
        }
    }

    sealed class Child {
        data class RegistrationComponentChild(val component: RegistrationComponent) : Child()
        data class LoginComponentChild(val component: LoginComponent) : Child()
    }
}
