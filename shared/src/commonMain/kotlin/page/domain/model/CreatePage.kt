package page.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePage(
    val name: String,
    val html: String,
    @SerialName("capture_credentials")
    val captureCredentials: Boolean,
    @SerialName("capture_passwords")
    val capturePasswords: Boolean,
    @SerialName("redirect_url")
    val redirectUrl: String
)