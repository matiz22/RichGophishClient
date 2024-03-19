package smtp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Smtp(
    val id: Int,
    @SerialName("interface_type")
    val interfaceType: String,
    val name: String,
    val host: String,
    @SerialName("from_address")
    val fromAddress: String,
    @SerialName("ignore_cert_errors")
    val ignoreCertErrors: Boolean,
    val headers: List<Map<String, String>>,
    @SerialName("modified_date")
    val modifiedDate: String
)