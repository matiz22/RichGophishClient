package gophish.campaign.events

import result.domain.model.Result

sealed class CampaignDetailsEvent {
    data class UpdateSearchText(val text: String) : CampaignDetailsEvent()
    data object NextPage : CampaignDetailsEvent()
    data object PreviousPage : CampaignDetailsEvent()
    data class PickResult(val result: Result) : CampaignDetailsEvent()
    data class DeleteResult(val result: Result) : CampaignDetailsEvent()
}
