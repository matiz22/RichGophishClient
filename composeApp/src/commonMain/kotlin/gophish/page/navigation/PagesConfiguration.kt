package gophish.page.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class PagesConfiguration {
    @Serializable
    data object PagesListConfiguration : PagesConfiguration()

    @Serializable
    data object CreatePageConfiguration : PagesConfiguration()
}
