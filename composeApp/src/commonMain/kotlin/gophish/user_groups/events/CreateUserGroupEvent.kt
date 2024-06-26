package gophish.user_groups.events

import gophish.user_groups.domain.CreateTargetForm
import group.domain.model.Target

sealed class CreateUserGroupEvent {
    data class UpdateEmail(val email: String) : CreateUserGroupEvent()
    data class UpdateFirstName(val firstName: String) : CreateUserGroupEvent()
    data class UpdateLastName(val lastName: String) : CreateUserGroupEvent()
    data class UpdatePosition(val position: String) : CreateUserGroupEvent()
    data class UpdateName(val name: String) : CreateUserGroupEvent()
    data object AddUserGroup : CreateUserGroupEvent()
    data class DeleteTarget(val target: CreateTargetForm) : CreateUserGroupEvent()
    data object SubmitTarget : CreateUserGroupEvent()
}