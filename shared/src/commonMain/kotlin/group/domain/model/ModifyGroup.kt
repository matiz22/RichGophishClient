package group.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModifyGroup(
    val id: Int,
    val name: String,
    val targets: List<Target>
)