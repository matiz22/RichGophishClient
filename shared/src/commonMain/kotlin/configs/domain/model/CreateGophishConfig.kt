package configs.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateGophishConfig(
    val userId: Long,
    val url: String,
    val apiKey: String
)
