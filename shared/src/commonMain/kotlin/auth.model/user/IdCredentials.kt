package auth.model.user

import kotlinx.serialization.Serializable

@Serializable
data class IdCredentials(
    val id: Long,
    val password: String
)