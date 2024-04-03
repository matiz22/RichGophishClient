package config.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import campaigns.domain.model.Campaign
import campaigns.domain.model.CampaignStats
import campaigns.domain.model.DataOrError
import com.matiz22.richgophishclient.AppRes
import config.presentation.composables.MenuItemPosition
import config.presentation.domain.MenuItem
import config.presentation.events.HomeOfConfigEvent
import config.presentation.navigation.ConfigScreensConfiguration
import gophish.presentation.composables.ChooseCampaignDialog
import gophish.presentation.composables.StatsCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeOfConfigScreen(
    campaigns: DataOrError<List<Campaign>>,
    summary: CampaignStats?,
    onEvent: (HomeOfConfigEvent) -> Unit,
    pickingCampaign: Boolean,
    navigate: (ConfigScreensConfiguration) -> Unit,
) {
    if (campaigns.data != null) {
        if (pickingCampaign) {
            ChooseCampaignDialog(
                campaigns = campaigns.data!!,
                onEvent = onEvent,
            )
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                StatsCard(
                    title = AppRes.string.all_configs_title,
                    stats = summary
                )
            }
            item {
                MenuItemPosition(
                    menuItem = MenuItem(
                        title = AppRes.string.option_campaigns,
                        action = { onEvent(HomeOfConfigEvent.ShowCampaigns) }
                    )
                )
            }
            item {
                MenuItemPosition(
                    menuItem = MenuItem(
                        title = AppRes.string.option_users_and_groups,
                        action = { onEvent(HomeOfConfigEvent.ShowCampaigns) }
                    )
                )
            }
            item {
                MenuItemPosition(
                    menuItem = MenuItem(
                        title = AppRes.string.option_email_templates,
                        action = { navigate(ConfigScreensConfiguration.EmailTemplateConfiguration) }
                    )
                )
            }
            item {
                MenuItemPosition(
                    menuItem = MenuItem(
                        title = AppRes.string.option_landing_pages,
                        action = { onEvent(HomeOfConfigEvent.ShowCampaigns) }
                    )
                )
            }
            item {
                MenuItemPosition(
                    menuItem = MenuItem(
                        title = AppRes.string.option_sending_profiles,
                        action = { onEvent(HomeOfConfigEvent.ShowCampaigns) }
                    )
                )
            }
            item {
                MenuItemPosition(
                    menuItem = MenuItem(
                        title = AppRes.string.option_user_management,
                        action = { onEvent(HomeOfConfigEvent.ShowCampaigns) }
                    )
                )
            }
        }
    }
}