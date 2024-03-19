package smtp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ChosenSmtp(
    val name: String
)