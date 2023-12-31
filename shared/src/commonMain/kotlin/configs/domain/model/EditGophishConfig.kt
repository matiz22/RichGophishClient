package configs.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class EditGophishConfig(
    val id: Long,
    val name: String?,
    val userId: Long,
    val url: String?,
    val apiKey: String?
)
