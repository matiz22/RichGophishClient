package gophish.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import campaigns.domain.model.Campaign
import campaigns.domain.model.CampaignSummary
import campaigns.domain.model.DataOrError
import com.matiz22.richgophishclient.AppRes
import gophish.presentation.events.CampaignDetailsEvent
import gophish.presentation.composables.DetailsCard
import gophish.presentation.composables.PickedResult
import gophish.presentation.composables.ResultTab
import gophish.presentation.composables.StatsCard
import gophish.presentation.domain.PickedUserForDetails
import gophish.presentation.state.PageState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CampaignDetailsScreen(
    campaign: DataOrError<Campaign>,
    campaignSummary: DataOrError<CampaignSummary>,
    pageState: PageState,
    onEvent: (CampaignDetailsEvent) -> Unit,
    searchText: String,
    pickedUserForDetails: List<PickedUserForDetails>
) {
    if (campaign.data != null) {
        LazyColumn(
            contentPadding = PaddingValues(4.dp),
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
                ResultTab(
                    results = campaign.data!!.results,
                    pageState = pageState,
                    onClick = onEvent,
                    searchText = searchText
                )
            }
            item {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    pickedUserForDetails.forEach {
                        PickedResult(
                            modifier = Modifier.width(500.dp),
                            pickedUserForDetails = it,
                            onClick = onEvent
                        )
                    }
                }
            }
        }
    }
}

