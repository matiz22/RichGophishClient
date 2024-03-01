package config.presentation.navigation

import auth.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
sealed class ConfigScreensConfiguration {
    @Serializable
    data class ListOfConfigsScreen(val user: User) : ConfigScreensConfiguration()

}
