package config.presentation.screen

import androidx.compose.runtime.Composable
import campaigns.domain.model.CampaignStats
import gophish.presentation.composables.SummaryCard

@Composable
fun HomeOfConfigScreen(summary: CampaignStats) {
    SummaryCard(
        summary = summary
    )
}