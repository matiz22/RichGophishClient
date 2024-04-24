package gophish.presentation.domain

import group.domain.model.CreateGroup
import group.domain.model.Target

data class CreateUserGroupForm(
    val name: String = "",
    val targets: List<Target> = emptyList()
) {
    fun toCreateForm(): CreateGroup {
        return CreateGroup(
            name = name,
            targets = targets
        )
    }
}