package campaigns.domain.model

import template.domain.model.Template
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import page.domain.model.Page
import smtp.domain.model.Smtp
import result.domain.model.Result
import timeline.domain.model.Timeline

@Serializable
data class Campaign(
    val id: Long,
    val name: String,
    @SerialName("created_date")
    val createdDate: String,
    @SerialName("launch_date")
    val launchDate: String,
    @SerialName("send_by_date")
    val sendByDate: String,
    @SerialName("completed_date")
    val completedDate: String,
    val template: Template,
    val page: Page,
    val status: String,
    val results: List<Result>,
    val timeline: List<Timeline>?,
    val smtp: Smtp,
    val url: String
)