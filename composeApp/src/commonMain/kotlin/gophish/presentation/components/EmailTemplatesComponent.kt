package gophish.presentation.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import gophish.presentation.navigation.EmailTemplateConfiguration

class EmailTemplatesComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    val navigation = StackNavigation<EmailTemplateConfiguration>()
    val childStack = childStack(
        source = navigation,
        serializer = EmailTemplateConfiguration.serializer(),
        initialConfiguration = EmailTemplateConfiguration.EmailListTemplateConfiguration,
        handleBackButton = true,
        childFactory = ::creteChild
    )

    private fun creteChild(
        config: EmailTemplateConfiguration,
        context: ComponentContext
    ): Child {
        return when (config) {
            is EmailTemplateConfiguration.EmailListTemplateConfiguration -> Child.EmailListScreen(
                EmailListTemplatesComponent(
                    componentContext = context
                )
            )

            is EmailTemplateConfiguration.CreateEmailTemplateConfiguration -> Child.CreateEmailScreen(
                CreateEmailTemplateComponent(
                    componentContext = context
                )
            )
        }
    }

    sealed class Child {
        data class EmailListScreen(val component: EmailListTemplatesComponent) : Child()
        data class CreateEmailScreen(val component: CreateEmailTemplateComponent) : Child()
    }

}