package gophish.presentation.composables

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gophish.events.CampaignDetailsEvent

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