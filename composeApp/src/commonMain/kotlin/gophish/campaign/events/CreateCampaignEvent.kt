package gophish.campaign.events

sealed class CreateCampaignEvent {
    data object Submit : CreateCampaignEvent()
    data class UpdateName(val name: String) : CreateCampaignEvent()
    data class UpdateTemplate(val name: String) : CreateCampaignEvent()
    data class UpdateUrl(val name: String) : CreateCampaignEvent()
    data class UpdatePage(val name: String) : CreateCampaignEvent()
    data class UpdateSmtp(val name: String) : CreateCampaignEvent()
    data class AddGroup(val name: String) : CreateCampaignEvent()
    data class DeleteGroup(val name: String) : CreateCampaignEvent()
}
