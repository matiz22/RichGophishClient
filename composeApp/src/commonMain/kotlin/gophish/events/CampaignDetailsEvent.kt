package gophish.events

sealed class CampaignDetailsEvent {
    data object NextPage : CampaignDetailsEvent()
    data object PreviousPage : CampaignDetailsEvent()
}
