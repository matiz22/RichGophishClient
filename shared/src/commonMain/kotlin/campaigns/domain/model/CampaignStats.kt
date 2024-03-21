package campaigns.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CampaignStats(
    val total: Int = 0,
    val sent: Int= 0,
    val opened: Int = 0,
    val clicked: Int = 0,
    @SerialName("submitted_data")
    val submittedData: Int = 0,
    @SerialName("email_reported")
    val emailReported: Int = 0,
    val error: Int = 0
)