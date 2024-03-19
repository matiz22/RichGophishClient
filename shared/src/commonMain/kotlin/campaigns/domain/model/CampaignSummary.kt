package campaigns.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CampaignSummary(
    val id: Int,
    @SerialName("created_date")
    val createdDate: String,
    @SerialName("launch_date")
    val launchDate: String,
    @SerialName("send_by_date")
    val sendByDate: String,
    @SerialName("completed_date")
    val completedDate: String,
    val status: String,
    val name: String,
    val stats: CampaignStats
)