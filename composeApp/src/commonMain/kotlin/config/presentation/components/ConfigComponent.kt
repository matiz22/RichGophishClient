package config.presentation.components

import auth.domain.model.User
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import config.presentation.navigation.ConfigScreensConfiguration
import configs.domain.model.GophishConfig


class ConfigComponent(
    componentContext: ComponentContext,
    val user: User
) : ComponentContext by componentContext {

    val navigation = StackNavigation<ConfigScreensConfiguration>()
    val childStack = childStack(
        source = navigation,
        serializer = ConfigScreensConfiguration.serializer(),
        initialConfiguration = ConfigScreensConfiguration.ListOfConfigsConfiguration(user),
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: ConfigScreensConfiguration,
        context: ComponentContext
    ): Child {
        return when (config) {
            is ConfigScreensConfiguration.ListOfConfigsConfiguration -> Child.ListOfConfigsScreenChild(
                component = ListOfConfigsComponent(
                    componentContext = context
                )
            )

            is ConfigScreensConfiguration.HomeOfConfigConfiguration -> Child.HomeOfConfigScreenChild(
                component = HomeOfConfigComponent(
                    componentContext = context,
                    config = config.gophishConfig
                )
            )
        }
    }

    sealed class Child {
        data class ListOfConfigsScreenChild(val component: ListOfConfigsComponent) : Child()
        data class HomeOfConfigScreenChild(val component: HomeOfConfigComponent) : Child()
    }
}