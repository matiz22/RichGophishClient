package gophish.campaign.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gophish.campaign.events.CampaignDetailsEvent
import result.domain.model.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultItem(
    modifier: Modifier = Modifier,
    result: Result,
    onClick: (CampaignDetailsEvent) -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        onClick = {
            onClick(CampaignDetailsEvent.PickResult(result))
        }
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
            Box(
                modifier = Modifier.fillMaxSize().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = result.firstName)
            }
            Box(
                modifier = Modifier.fillMaxSize().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = result.lastName)
            }
            Box(
                modifier = Modifier.fillMaxSize().weight(2f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = result.email)
            }
            Box(
                modifier = Modifier.fillMaxSize().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = result.status)
            }
        }
    }
}