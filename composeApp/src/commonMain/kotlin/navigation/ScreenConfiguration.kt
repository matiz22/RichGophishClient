package navigation

import auth.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenConfiguration {
    @Serializable
    data object AuthScreen : ScreenConfiguration()
    @Serializable
    data class ConfigScreen(val user: User): ScreenConfiguration()
}