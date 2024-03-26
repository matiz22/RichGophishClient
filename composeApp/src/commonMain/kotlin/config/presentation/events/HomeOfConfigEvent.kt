package config.presentation.events

sealed class HomeOfConfigEvent {
    data object PickCampaign : HomeOfConfigEvent()
    data object ShowCampaigns : HomeOfConfigEvent()
    data object HideCampaigns : HomeOfConfigEvent()
    data class DeleteCampaign(val id: Long) : HomeOfConfigEvent()
}