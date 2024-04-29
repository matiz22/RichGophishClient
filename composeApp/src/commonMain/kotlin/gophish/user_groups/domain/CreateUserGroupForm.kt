package gophish.user_groups.domain

import group.domain.model.CreateGroup
import group.domain.model.Target

data class CreateUserGroupForm(
    val name: String = "",
    val targets: List<CreateTargetForm> = emptyList()
) {
    fun toCreateForm(): CreateGroup {
        return CreateGroup(
            name = name,
            targets = targets.map { Target(
                email = it.email,
                firstName = it.firstname,
                lastName = it.lastName,
                position = it.position
            ) }
        )
    }
}