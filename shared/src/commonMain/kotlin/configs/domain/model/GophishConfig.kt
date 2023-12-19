package configs.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GophishConfig(
    val id: Long,
    val userId: Long,
    val url: String,
    val apiKey: String
)
