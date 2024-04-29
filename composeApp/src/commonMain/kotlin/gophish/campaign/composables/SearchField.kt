package gophish.campaign.composables

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gophish.campaign.events.CampaignDetailsEvent

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    text: String,
    onChange: (CampaignDetailsEvent) -> Unit,
    textHint: String,
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            onChange(CampaignDetailsEvent.UpdateSearchText(it))
        },
        singleLine = true,
        label = {
            Text(text = textHint)
        }
    )
}