package gophish.presentation.composables

import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import campaigns.domain.model.CampaignStats


@Composable
fun SummaryCard(
    modifier: Modifier = Modifier,
    summary: CampaignStats
) {

    OutlinedCard(
        modifier = modifier
    ) {
        
    }
}