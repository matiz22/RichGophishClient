package auth.model.config_gophish

import kotlinx.serialization.Serializable

@Serializable
data class CreateGophishConfig(
    val userId: Long,
    val url: String,
    val apiKey: String
)
