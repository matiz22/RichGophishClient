package result.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val id: String,
    val status: String,
    val ip: String,
    val latitude: Int,
    val longitude: Int,
    @SerialName("send_date")
    val sendDate: String,
    val reported: Boolean,
    @SerialName("modified_date")
    val modifiedDate: String,
    val email: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    val position: String
)