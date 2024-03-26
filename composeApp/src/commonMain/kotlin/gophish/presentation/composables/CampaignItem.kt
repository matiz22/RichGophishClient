package gophish.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import campaigns.domain.model.Campaign
import helper.provideReadableDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignItem(
    modifier: Modifier = Modifier,
    campaign: Campaign,
    onDeleteClick: () -> Unit,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(
                    text = campaign.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = provideReadableDateTime(campaign.createdDate),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = campaign.status,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column {
                IconButton(
                    onClick = {
                        onDeleteClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }
        }
    }
}