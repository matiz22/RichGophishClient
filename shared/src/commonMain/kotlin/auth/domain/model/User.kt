package auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val email: String,
    val password: String,
)
