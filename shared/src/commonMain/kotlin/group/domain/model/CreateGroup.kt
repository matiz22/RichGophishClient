package group.domain.model


import kotlinx.serialization.Serializable

@Serializable
data class CreateGroup(
    val name: String,
    val targets: List<Target>
)