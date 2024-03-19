package home.domain.model


import kotlinx.serialization.Serializable

@Serializable
data class GophishCallResult(
    val message: String,
    val success: Boolean,
    val data: String
)