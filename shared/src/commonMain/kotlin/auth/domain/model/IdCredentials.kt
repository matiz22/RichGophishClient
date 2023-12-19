package auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class IdCredentials(
    val id: Long,
    val password: String
)