package gophish.presentation.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import gophish.presentation.navigation.PagesConfiguration

class PagesComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    val navigation = StackNavigation<PagesConfiguration>()
    val childStack = childStack(
        source = navigation,
        serializer = PagesConfiguration.serializer(),
        initialConfiguration = PagesConfiguration.PagesListConfiguration,
        handleBackButton = true,
        childFactory = ::creteChild
    )

    private fun creteChild(
        config: PagesConfiguration,
        context: ComponentContext
    ): Child {
        return when (config) {
            is PagesConfiguration.PagesListConfiguration -> Child.PagesListChild(
                PagesListComponent(
                    componentContext = context
                )
            )

            is PagesConfiguration.CreatePageConfiguration -> Child.CreatePageChild(
                CreatePageComponent(
                    componentContext = context
                )
            )
        }
    }

    sealed class Child {
        data class PagesListChild(val component: PagesListComponent) : Child()
        data class CreatePageChild(val component: CreatePageComponent) : Child()
    }

}