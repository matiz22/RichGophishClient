package timeline.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val payload: Payload,
    val browser: Browser
) {
    @Serializable
    data class Payload(
        val rid: List<String>
    )

    @Serializable
    data class Browser(
        val address: String,
        @SerialName("user-agent")
        val userAgent: String
    )
}