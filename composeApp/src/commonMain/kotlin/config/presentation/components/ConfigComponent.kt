package config.presentation.components

import auth.domain.model.User
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import config.presentation.navigation.ConfigScreensConfiguration


class ConfigComponent(
    componentContext: ComponentContext,
    val user: User
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<ConfigScreensConfiguration>()
    val childStack = childStack(
        source = navigation,
        serializer = ConfigScreensConfiguration.serializer(),
        initialConfiguration = ConfigScreensConfiguration.ListOfConfigsScreen(user),
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: ConfigScreensConfiguration,
        context: ComponentContext
    ): Child {
        return when (config) {
            is ConfigScreensConfiguration.ListOfConfigsScreen -> Child.ListOfConfigsScreenChild(
                component = ListOfConfigsComponent(
                    componentContext = context
                )
            )
        }
    }

    sealed class Child {
        data class ListOfConfigsScreenChild(val component: ListOfConfigsComponent) : Child()
        data class HomeOfConfigsScreenChild(val component: HomeOfConfigsComponent) : Child()
    }
}