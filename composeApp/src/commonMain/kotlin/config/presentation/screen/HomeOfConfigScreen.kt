package config.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import campaigns.domain.model.CampaignStats
import gophish.presentation.composables.SummaryCard

@Composable
fun HomeOfConfigScreen(summary: CampaignStats?) {
    Column {
        SummaryCard(
            stats = summary
        )
        LazyColumn {
            item {
                Button(
                    onClick = {}
                ){}
            }
        }
    }


}