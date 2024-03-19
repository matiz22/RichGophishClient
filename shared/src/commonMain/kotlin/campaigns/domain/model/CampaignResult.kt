package campaigns.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import timeline.domain.model.Timeline
import result.domain.model.Result

@Serializable
data class CampaignResult(
    val id: Int,
    val name: String,
    val status: String,
    val results: List<Result>,
    val timeline: List<Timeline>
)