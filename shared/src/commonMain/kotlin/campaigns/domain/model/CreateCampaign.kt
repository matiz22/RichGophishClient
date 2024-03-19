package campaigns.domain.model


import group.domain.model.ChosenGroup
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import page.domain.model.ChosenPage
import smtp.domain.model.ChosenSmtp
import template.domain.model.ChosenTemplate

@Serializable
data class CreateCampaign(
    val name: String,
    val template: ChosenTemplate,
    val url: String,
    val page: ChosenPage,
    val smtp: ChosenSmtp,
    @SerialName("launch_date")
    val launchDate: String,
    @SerialName("send_by_date")
    val sendByDate: String,
    val groups: List<ChosenGroup>
)