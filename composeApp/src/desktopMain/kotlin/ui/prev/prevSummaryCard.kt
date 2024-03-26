package ui.prev

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import campaigns.domain.model.CampaignStats
import gophish.presentation.composables.StatsCard

@Preview
@Composable
fun prevSummaryCard() {
    val summary = CampaignStats(
        total = 100,
        sent = 80,
        opened = 60,
        clicked = 40,
        submittedData = 20,
        emailReported = 5,
        error = 2
    )
    StatsCard(stats = summary)
}