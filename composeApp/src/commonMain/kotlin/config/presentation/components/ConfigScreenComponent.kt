package config.presentation.components

import auth.domain.model.User
import com.arkivanov.decompose.ComponentContext

class ConfigScreenComponent(
    componentContext: ComponentContext,
    val user: User
) : ComponentContext by componentContext {

}