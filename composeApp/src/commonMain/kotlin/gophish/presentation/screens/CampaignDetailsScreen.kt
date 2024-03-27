package gophish.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import campaigns.domain.model.Campaign
import campaigns.domain.model.CampaignSummary
import campaigns.domain.model.DataOrError
import com.matiz22.richgophishclient.AppRes
import gophish.presentation.composables.DetailsCard
import gophish.presentation.composables.ResultItem
import gophish.presentation.composables.StatsCard

@Composable
fun CampaignDetailsScreen(
    campaign: DataOrError<Campaign>,
    campaignSummary: DataOrError<CampaignSummary>,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            DetailsCard(campaign = campaign.data)
        }
        item {
            StatsCard(
                title = AppRes.string.campaign_stats,
                stats = campaignSummary.data?.stats
            )
        }
        item {
            campaign.data?.results?.forEach {
                ResultItem(result = it)
            }
        }
    }
}

