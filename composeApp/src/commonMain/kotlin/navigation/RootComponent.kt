package navigation

import auth.presentation.components.AuthScreenComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import config.presentation.components.ConfigScreenComponent
import kotlinx.coroutines.Dispatchers


class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<ScreenConfiguration>()
    val childStack = childStack(
        source = navigation,
        serializer = ScreenConfiguration.serializer(),
        initialConfiguration = ScreenConfiguration.AuthScreen,
        handleBackButton = true,
        childFactory = ::creteChild
    )


    private fun creteChild(
        config: ScreenConfiguration,
        context: ComponentContext
    ): Child {
        return when (config) {
            is ScreenConfiguration.AuthScreen -> Child.AuthScreen(
                AuthScreenComponent(
                    componentContext = context,
                    mainCoroutineContext = Dispatchers.Main,
                    navigateToConfig = { user ->
                        navigation.replaceAll(
                            ScreenConfiguration.ConfigScreen(user)
                        )
                    }
                )
            )

            is ScreenConfiguration.ConfigScreen -> Child.ConfigScreen(
                ConfigScreenComponent(
                    componentContext = context,
                    user = config.user
                )
            )
        }
    }

    sealed class Child {
        data class AuthScreen(val component: AuthScreenComponent) : Child()
        data class ConfigScreen(val component: ConfigScreenComponent) : Child()
    }

}