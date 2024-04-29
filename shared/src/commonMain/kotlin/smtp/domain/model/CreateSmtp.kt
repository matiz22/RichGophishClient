package smtp.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSmtp(
    @SerialName("from_address")
    val fromAddress: String,
    val headers: List<Header>,
    val host: String,
    @SerialName("ignore_cert_errors")
    val ignoreCertErrors: Boolean,
    @SerialName("interface_type")
    val interfaceType: String = "SMTP",
    val name: String,
    val password: String,
    val username: String
)