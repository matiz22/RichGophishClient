package group.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Group(
    @SerialName("id")
    val id: Int,
    @SerialName("modified_date")
    val modifiedDate: String,
    @SerialName("name")
    val name: String,
    @SerialName("targets")
    val targets: List<Target>
)