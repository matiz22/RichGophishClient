package navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenConfiguration {
    @Serializable
    data object AuthScreen : ScreenConfiguration()
    @Serializable
    data object ConfigScreen: ScreenConfiguration()
}