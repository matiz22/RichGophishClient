package gophish.user_groups.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class UserGroupConfiguration {
    @Serializable
    data object UserGroupListConfiguration : UserGroupConfiguration()
    @Serializable
    data object CreateUserGroupConfiguration : UserGroupConfiguration()
}
