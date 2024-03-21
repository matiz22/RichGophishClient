package template.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Template(
    val id: Int,
    val name: String,
    @SerialName("envelope_sender")
    val envelopeSender: String,
    val subject: String,
    val text: String,
    val html: String,
    @SerialName("modified_date")
    val modifiedDate: String? = null,
)