package gophish.presentation.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import gophish.presentation.navigation.SmtpConfiguration
import gophish.presentation.navigation.UserGroupConfiguration

class SmtpComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    val navigation = StackNavigation<SmtpConfiguration>()
    val childStack = childStack(
        source = navigation,
        serializer = SmtpConfiguration.serializer(),
        initialConfiguration = SmtpConfiguration.SmtpListConfiguration,
        handleBackButton = true,
        childFactory = ::creteChild
    )

    private fun creteChild(
        config: SmtpConfiguration,
        context: ComponentContext
    ): Child {
        return when (config) {
            is SmtpConfiguration.CreateSmtpConfiguration -> Child.CreateSmtpChild(
                component = CreateSmtpComponent(componentContext = context)
            )

            is SmtpConfiguration.SmtpListConfiguration -> Child.SmtpListChild(
                component = SmtpListComponent(componentContext = context)
            )
        }
    }

    sealed class Child {
        data class SmtpListChild(val component: SmtpListComponent) : Child()
        data class CreateSmtpChild(val component: CreateSmtpComponent) : Child()
    }

}