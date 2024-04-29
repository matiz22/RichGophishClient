package gophish.campaign.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import campaigns.domain.model.Campaign
import com.matiz22.richgophishclient.AppRes
import helper.provideReadableDateTime

@Composable
fun DetailsCard(
    modifier: Modifier = Modifier,
    campaign: Campaign?
) {
    OutlinedCard(modifier = modifier) {
        if (campaign != null) {
            Column(modifier = Modifier.fillMaxWidth().wrapContentSize().padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = AppRes.string.campaign_name,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = campaign.name,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = AppRes.string.campaign_created_date,
                    )
                    Text(
                        text = provideReadableDateTime(campaign.createdDate)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = AppRes.string.campaign_launch_date
                    )
                    Text(
                        text = provideReadableDateTime(campaign.launchDate)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = AppRes.string.campaign_send_by_date
                    )
                    Text(
                        text = provideReadableDateTime(campaign.sendByDate)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = AppRes.string.campaign_completed_date
                    )
                    Text(
                        text = provideReadableDateTime(campaign.completedDate)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = AppRes.string.campaign_template
                    )
                    Text(
                        text = campaign.template.name
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = AppRes.string.campaign_page
                    )
                    Text(
                        text = campaign.page.name
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = AppRes.string.campaign_status
                    )
                    Text(
                        text = campaign.status
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = AppRes.string.campaign_url
                    )
                    Text(
                        text = campaign.url
                    )
                }
            }
        }
    }
}