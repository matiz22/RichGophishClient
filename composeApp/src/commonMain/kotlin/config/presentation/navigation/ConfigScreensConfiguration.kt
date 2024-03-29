package config.presentation.navigation

import auth.domain.model.User
import campaigns.domain.model.Campaign
import configs.domain.model.GophishConfig
import kotlinx.serialization.Serializable

@Serializable
sealed class ConfigScreensConfiguration {
    @Serializable
    data class ListOfConfigsConfiguration(val user: User) : ConfigScreensConfiguration()

    @Serializable
    data class HomeOfConfigConfiguration(val gophishConfig: GophishConfig) : ConfigScreensConfiguration()

    @Serializable
    data class CampaignDetailsConfiguration(val campaign: Campaign) : ConfigScreensConfiguration()
}
