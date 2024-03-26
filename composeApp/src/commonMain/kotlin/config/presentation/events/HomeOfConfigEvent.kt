package config.presentation.events

import campaigns.domain.model.Campaign

sealed class HomeOfConfigEvent {
    data class PickCampaign(val campaign: Campaign) : HomeOfConfigEvent()
    data object ShowCampaigns : HomeOfConfigEvent()
    data object HideCampaigns : HomeOfConfigEvent()
    data class DeleteCampaign(val id: Long) : HomeOfConfigEvent()
}