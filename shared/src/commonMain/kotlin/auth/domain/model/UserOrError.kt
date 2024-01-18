package auth.domain.model

data class UserOrError(
    val user: User? = null,
    val error: String? = null
)
