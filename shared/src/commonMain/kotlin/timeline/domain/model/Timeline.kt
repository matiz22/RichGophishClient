package timeline.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Timeline(
    val email: String,
    val time: String,
    val message: String,
    val details: String
)