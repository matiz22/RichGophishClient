package auth.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthScreensConfiguration {
    @Serializable
    data object LoginConfiguration : AuthScreensConfiguration()

    @Serializable
    data object RegistrationConfiguration : AuthScreensConfiguration()
}