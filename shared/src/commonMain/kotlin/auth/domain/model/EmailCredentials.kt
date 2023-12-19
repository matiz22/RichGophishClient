package auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class EmailCredentials(
    val email: String,
    val password: String
)