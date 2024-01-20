package navigation

import auth.presentation.components.AuthScreenComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push


import config.presentation.components.ConfigScreenComponent

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
                    onNavigate = {
                        navigation.push(ScreenConfiguration.ConfigScreen)
                    }
                )
            )

            is ScreenConfiguration.ConfigScreen -> Child.ConfigScreen(
                ConfigScreenComponent(context)
            )
        }
    }

    sealed class Child {
        data class AuthScreen(val component: AuthScreenComponent) : Child()
        data class ConfigScreen(val component: ConfigScreenComponent) : Child()
    }

}