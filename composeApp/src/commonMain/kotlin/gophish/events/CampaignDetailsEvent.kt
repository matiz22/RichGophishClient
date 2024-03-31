package gophish.events

sealed class CampaignDetailsEvent {
    data class UpdateSearchText(val text: String) : CampaignDetailsEvent()
    data object NextPage : CampaignDetailsEvent()
    data object PreviousPage : CampaignDetailsEvent()
}
