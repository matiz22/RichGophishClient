package configs.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ConfigUserIdRequest(
    val userId: Long
)