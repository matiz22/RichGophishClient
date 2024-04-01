package gophish.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matiz22.richgophishclient.AppRes
import gophish.events.CampaignDetailsEvent
import gophish.presentation.domain.PickedUserForDetails

@Composable
fun PickedResult(
    modifier: Modifier = Modifier,
    pickedUserForDetails: PickedUserForDetails,
    onClick: (CampaignDetailsEvent) -> Unit
) {
    OutlinedCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(
                        text = AppRes.string.timeline_header.format(
                            "${pickedUserForDetails.result.firstName} " +
                                    pickedUserForDetails.result.lastName
                        ),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = AppRes.string.email_timeline_header.format(
                            pickedUserForDetails.result.email
                        ),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                IconButton(
                    onClick = { onClick(CampaignDetailsEvent.DeleteResult(pickedUserForDetails.result)) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }
            pickedUserForDetails.timelines.forEach {
                TimelineEvent(it)
            }

        }
    }
}