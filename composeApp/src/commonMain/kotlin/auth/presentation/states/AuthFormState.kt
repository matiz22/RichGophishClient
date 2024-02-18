package auth.presentation.states

import auth.domain.model.EmailCredentials

data class AuthFormState(
    var isNewUser: Boolean = true,
    var email: String = "",
    var emailError: String? = null,
    var password: String = "",
    var passwordError: String? = null,
    val otherErrors: String? = null
) {
    fun toEmailCredentials(): EmailCredentials {
        return EmailCredentials(
            email = email,
            password = password
        )
    }
}
