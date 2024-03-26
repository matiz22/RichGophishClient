package gophish.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import campaigns.domain.model.Campaign
import config.presentation.events.HomeOfConfigEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseCampaignDialog(
    onEvent: (HomeOfConfigEvent) -> Unit,
    campaigns: List<Campaign>
) {
    AlertDialog(
        onDismissRequest = {
            onEvent(HomeOfConfigEvent.HideCampaigns)
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(0.7f),
            shape = RoundedCornerShape(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(0.7f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(campaigns) { campaign ->
                    CampaignItem(
                        campaign = campaign,
                        onClick = {
                            onEvent(HomeOfConfigEvent.DeleteCampaign(campaign.id))
                        }
                    )
                }
            }
        }
    }
}