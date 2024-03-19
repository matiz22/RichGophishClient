package campaigns.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CampaignStats(
    val total: Int,
    val sent: Int,
    val opened: Int,
    val clicked: Int,
    @SerialName("submitted_data")
    val submittedData: Int,
    @SerialName("email_reported")
    val emailReported: Int,
    val error: Int
)