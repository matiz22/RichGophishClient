package gophish.campaign.state

import campaigns.domain.model.CreateCampaign
import group.domain.model.ChosenGroup
import page.domain.model.ChosenPage
import smtp.domain.model.ChosenSmtp
import template.domain.model.ChosenTemplate

data class CampaignFormState(
    val name: String = "",
    val template: String = "",
    val url: String = "",
    val page: String = "",
    val smtp: String = "",
    val pickedGroups: List<String> = emptyList(),
    val launchedByDate: String = "",


    ) {
    fun toCreateCampaignRequest(): CreateCampaign {
        return CreateCampaign(
            name = name,
            template = ChosenTemplate(template),
            url = url,
            page = ChosenPage(page),
            smtp = ChosenSmtp(smtp),
            groups = pickedGroups.map { ChosenGroup(it) }
        )
    }
}
