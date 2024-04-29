package gophish.user_groups.domain

import group.domain.model.Target

data class CreateTargetForm(
    val firstname: String = "",
    val lastName: String = "",
    val email: String = "",
    val emailError: String? = null,
    val position: String = ""
) {
    fun toTarget(): Target {
        return Target(
            firstName = firstname,
            lastName = lastName,
            email = email,
            position = position
        )
    }
}
