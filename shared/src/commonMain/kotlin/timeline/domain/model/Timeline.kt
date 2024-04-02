package timeline.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Timeline(
    val email: String,
    val time: String,
    val message: String,
    val details: String
) {
    /**
     * Converts details string to obj
     * TODO move it to custom serializer,
     * It is badly nested json from GoPhish
     */
    fun provideDetailsList(): Event? {
        return if (details.isNotBlank()) {
            Json.decodeFromString<Event>(details)
        } else {
            null
        }
    }
}