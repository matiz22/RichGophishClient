package smtp.domain.model


import kotlinx.serialization.Serializable

@Serializable
data class Header(
    val key: String,
    val value: String
)