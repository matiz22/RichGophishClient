package config.presentation.navigation

import auth.domain.model.User
import campaigns.domain.model.Campaign
import configs.domain.model.GophishConfig
import kotlinx.serialization.Serializable

@Serializable
sealed class ConfigScreensConfiguration {
    @Serializable
    data object CreateCampaignConfiguration : ConfigScreensConfiguration()

    @Serializable
    data class ListOfConfigsConfiguration(val user: User) : ConfigScreensConfiguration()

    @Serializable
    data class HomeOfConfigConfiguration(val gophishConfig: GophishConfig) :
        ConfigScreensConfiguration()

    @Serializable
    data class CampaignDetailsConfiguration(val campaign: Campaign) : ConfigScreensConfiguration()

    @Serializable
    data object EmailTemplateConfiguration : ConfigScreensConfiguration()

    @Serializable
    data class HtmlViewerConfiguration(val title: String, val data: String) :
        ConfigScreensConfiguration()

    @Serializable
    data object PagesConfiguration : ConfigScreensConfiguration()

    @Serializable
    data object UserGroupConfiguration : ConfigScreensConfiguration()

    @Serializable
    data object SmtpConfiguration : ConfigScreensConfiguration()
}
