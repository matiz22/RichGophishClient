package gophish.user_groups.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import gophish.user_groups.navigation.UserGroupConfiguration

class UserGroupComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    val navigation = StackNavigation<UserGroupConfiguration>()
    val childStack = childStack(
        source = navigation,
        serializer = UserGroupConfiguration.serializer(),
        initialConfiguration = UserGroupConfiguration.UserGroupListConfiguration,
        handleBackButton = true,
        childFactory = ::creteChild
    )

    private fun creteChild(
        config: UserGroupConfiguration,
        context: ComponentContext
    ): Child {
        return when (config) {
            is UserGroupConfiguration.UserGroupListConfiguration -> Child.UserGroupListChild(
                component = UserGroupListComponent(
                    componentContext = context
                )
            )

            is UserGroupConfiguration.CreateUserGroupConfiguration -> Child.CreateUserGroupChild(
                component = CreateUserGroupComponent(
                    componentContext = context
                )
            )
        }
    }

    sealed class Child {
        data class UserGroupListChild(val component: UserGroupListComponent) : Child()
        data class CreateUserGroupChild(val component: CreateUserGroupComponent) : Child()
    }

}