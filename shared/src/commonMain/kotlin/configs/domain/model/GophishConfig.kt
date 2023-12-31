package configs.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GophishConfig(
    val id: Long,
    val name: String,
    val userId: Long,
    val url: String,
    val apiKey: String
)
