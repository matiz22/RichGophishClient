package gophish.presentation.events

sealed class CreateUserGroupEvent {
    data class UpdateEmail(val email: String) : CreateUserGroupEvent()
    data class UpdateFirstName(val firstName: String) : CreateUserGroupEvent()
    data class UpdateLastName(val lastName: String) : CreateUserGroupEvent()
    data class UpdatePosition(val position: String) : CreateUserGroupEvent()
    data class UpdateName(val name: String) : CreateUserGroupEvent()
    data object AddTargetToGroup : CreateUserGroupEvent()
    data object AddUserGroup: CreateUserGroupEvent()
}