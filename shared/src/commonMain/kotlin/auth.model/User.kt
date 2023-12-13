package auth.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = null,
    val email: String,
    val hashedPassword: String,
)
